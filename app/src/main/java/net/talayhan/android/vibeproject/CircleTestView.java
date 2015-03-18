package net.talayhan.android.vibeproject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Environment;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by root on 2/20/15.
 */
public class CircleTestView extends View {
    
    public static int i = 0;
    
    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;

    private static final String TAG = "CirclesDrawingView";
    private boolean isFirst = true;

    /** Main bitmap */
    private Bitmap mBitmap = null;
    private Rect mMeasuredRect;

    /** Paint to draw circles */
    private Paint mCirclePaint;

    private final Random mRadiusGenerator = new Random();
    // Radius limit in pixels
    private final static int RADIUS_LIMIT = 50;

    private static final int CIRCLES_LIMIT = 1;

    /** All available circles */
    private HashSet<CircleArea> mCircles = new HashSet<CircleArea>(CIRCLES_LIMIT);
    private SparseArray<CircleArea> mCirclePointer = new SparseArray<CircleArea>(CIRCLES_LIMIT);

    /**
     * Default constructor
     *
     * @param ct {@link android.content.Context}
     */
    public CircleTestView(final Context ct) {
        super(ct);
        init(ct);
    }

    public CircleTestView(final Context ct, final AttributeSet attrs) {
        super(ct, attrs);
        init(ct);
    }

    public CircleTestView(final Context ct, final AttributeSet attrs, final int defStyle) {
        super(ct, attrs, defStyle);
        init(ct);
    }
    /* --------------------------------------------------- */
    /* init
     *
    /* --------------------------------------------------- */
    private void init(final Context ct) {
        // Generate bitmap used for background
        mBitmap = BitmapFactory.decodeResource(ct.getResources(), R.drawable.abc_btn_check_to_on_mtrl_000);

        mCirclePaint = new Paint();

        mCirclePaint.setColor(Color.rgb(63, 239, 140));
        mCirclePaint.setStrokeWidth(10);
        mCirclePaint.setStyle(Paint.Style.STROKE);
    }

    /** Stores data about single circle */
    private static class CircleArea {
        int radius;
        int centerX;
        int centerY;

        CircleArea(int centerX, int centerY, int radius) {
            this.radius = radius;
            this.centerX = centerX;
            this.centerY = centerY;
        }

        @Override
        public String toString() {
            return "Circle[" + centerX + ", " + centerY + ", " + radius + "]";
        }
    }
    /* --------------------------------------------------- */
    /* onDraw
     *
    /* --------------------------------------------------- */
    @Override
    public void onDraw(final Canvas canv) {
        // background bitmap to cover all area
        //canv.drawBitmap(mBitmap, null, mMeasuredRect, null);

        for (CircleArea circle : mCircles) {
            canv.drawCircle(circle.centerX, circle.centerY, circle.radius, mCirclePaint);
        }
    }

    /* --------------------------------------------------- */
    /* onTouchEvent
     *
    /* --------------------------------------------------- */
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        boolean handled = false;

        CircleArea touchedCircle;
        int xTouch;
        int yTouch;
        int pointerId;
        int actionIndex = event.getActionIndex();

        // get touch event coordinates and make transparent circle from it
        switch (event.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN:
                // it's the first pointer, so clear all existing pointers data
                clearCirclePointer();

                if (isFirst) {
                    isFirst = false;
                    LocalVideoActivity.mVideoView_vw.pause();   // pause the video and show alert
                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Good job!")
                            .setContentText("You labeled the person!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    LocalVideoActivity.mVideoView_vw.start();   // start the video
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
                    
                xTouch = (int) event.getX(0);
                yTouch = (int) event.getY(0);

                // check if we've touched inside some circle
                touchedCircle = obtainTouchedCircle(xTouch, yTouch);
                touchedCircle.centerX = xTouch;
                touchedCircle.centerY = yTouch;
                mCirclePointer.put(event.getPointerId(0), touchedCircle);

                invalidate();
                handled = true;

                Log.d("CirclesDrawing", "ACTION_DOWN");

                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                Log.w(TAG, "Pointer down");
                // It secondary pointers, so obtain their ids and check circles
                pointerId = event.getPointerId(actionIndex);

                xTouch = (int) event.getX(actionIndex);
                yTouch = (int) event.getY(actionIndex);

                // check if we've touched inside some circle
                touchedCircle = obtainTouchedCircle(xTouch, yTouch);

                mCirclePointer.put(pointerId, touchedCircle);
                touchedCircle.centerX = xTouch;
                touchedCircle.centerY = yTouch;
                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_MOVE:
                final int pointerCount = event.getPointerCount();

                Log.w(TAG, "ACTION_MOVE");

                for (actionIndex = 0; actionIndex < pointerCount; actionIndex++) {
                    // Some pointer has moved, search it by pointer id
                    pointerId = event.getPointerId(actionIndex);

                    xTouch = (int) event.getX(actionIndex);
                    yTouch = (int) event.getY(actionIndex);

                    touchedCircle = mCirclePointer.get(pointerId);

                    if (null != touchedCircle) {
                        touchedCircle.centerX = xTouch;
                        touchedCircle.centerY = yTouch;
                    }
                }
                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_UP:
                clearCirclePointer();
                invalidate();
                handled = true;

                //MyVideoPlayer.actualVideoView.start();
                Toast.makeText(getContext(), "Start", Toast.LENGTH_SHORT).show();
                Log.d("CirclesDrawing", "ACTION_UP");

                break;

            case MotionEvent.ACTION_POINTER_UP:
                // not general pointer was up
                pointerId = event.getPointerId(actionIndex);

                mCirclePointer.remove(pointerId);
                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_CANCEL:
                handled = true;
                break;

            default:
                // do nothing
                break;
        }

        return super.onTouchEvent(event) || handled;
    }
    
/*
    private void saveScreenshotToSDCard(VideoView vv){
        Bitmap bitmap;
        
        
        //v1.setDrawingCacheEnabled(true);
        //bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        bitmap = vv.

        //v1.setDrawingCacheEnabled(false);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

        //you can create a new file name "test.jpg" in sdcard folder.
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + "test" + i++ + ".jpg");
        try {
            //write the bytes in file
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());

            // remember close de FileOutput
            fo.close();
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("[+] SDCARD DEBUG","Screenshot");
    }
*/
    
    /**
     * Clears all CircleArea - pointer id relations
     */
    private void clearCirclePointer() {
        Log.w(TAG, "clearCirclePointer");

        mCirclePointer.clear();
    }

    public String inputString;
    public ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);
    public ArrayList<String> totalLabel = new ArrayList<String>(50);

    /**
     * Search and creates new (if needed) circle based on touch area
     *
     * @param xTouch int x of touch
     * @param yTouch int y of touch
     * Note: Random radius, mRadiusGenerator.nextInt(RADIUS_LIMIT)
     */
    private CircleArea obtainTouchedCircle(final int xTouch, final int yTouch) {
        CircleArea touchedCircle = getTouchedCircle(xTouch, yTouch);

        if (null == touchedCircle) {
            touchedCircle = new CircleArea(xTouch, yTouch, RADIUS_LIMIT);

            if (mCircles.size() == CIRCLES_LIMIT) {
                Log.w(TAG, "Clear all circles, size is " + mCircles.size());
                // remove first circle
                mCircles.clear();
            }
            // Added Circle
            Log.w(TAG, "Added circle " + touchedCircle);
            mCircles.add(touchedCircle);
        }

        return touchedCircle;
    }

    /**
     * Determines touched circle
     *
     * @param xTouch int x touch coordinate
     * @param yTouch int y touch coordinate
     *
     */
    private CircleArea getTouchedCircle(final int xTouch, final int yTouch) {
        CircleArea touched = null;

        for (CircleArea circle : mCircles) {
            if ((circle.centerX - xTouch) * (circle.centerX - xTouch) + (circle.centerY - yTouch) * (circle.centerY - yTouch) <= circle.radius * circle.radius) {
                touched = circle;
                Toast.makeText(getContext(),"X:"+ xTouch+"Y:"+ yTouch,Toast.LENGTH_SHORT).show();
                break;
            }
        }

        return touched;
    }

    /* --------------------------------------------------- */
    /* onMeasure
     *
    /* --------------------------------------------------- */
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mMeasuredRect = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

}
