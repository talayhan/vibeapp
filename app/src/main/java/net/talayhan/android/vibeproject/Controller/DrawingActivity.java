package net.talayhan.android.vibeproject.Controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import net.talayhan.android.vibeproject.Model.Coordinate;
import net.talayhan.android.vibeproject.R;
import net.talayhan.android.vibeproject.Util.Constants;
import net.talayhan.android.vibeproject.Util.Utilities;
import net.talayhan.android.vibeproject.View.CircleView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by root on 2/20/15.
 */
public class DrawingActivity extends Activity {

    /* Toolbar buttons */
    @InjectView(R.id.forward_bt) ImageButton mForward_bt;
    @InjectView(R.id.play_bt) ImageButton mPlayPause_bt;
    @InjectView(R.id.backward_bt) ImageButton mBackward_bt;
    @InjectView(R.id.capture_bt) ImageButton mCapture_bt;
    @InjectView(R.id.list_btn) ImageButton mList_bt;
    @InjectView(R.id.circle_view) CircleView mCircleView;
    
    @InjectView(R.id.currentDuration_tv) TextView mCurrentDuration_tv;
    @InjectView(R.id.totalDuration_tv) TextView mTotalDuration_tv;
    
    /* Footer seekbar. */
    @InjectView(R.id.discrete_seek_bar) DiscreteSeekBar videoSeekbar_sb;

    /* Utility members */
    private Boolean isPause = false;
    private int seekForwardTime = Constants.FORWARD_TIME;
    private int seekBackwardTime = Constants.FORWARD_TIME;
    private Utilities utils = new Utilities();

    public int xTouch;
    public int yTouch;

    public static String FIREBASE_URL = "https://scorching-heat-6569.firebaseio.com/";
    private Firebase mFirebaseRef;
    
    /* Thread handler */
    private Handler mHandler = new Handler();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_drawing);
        ButterKnife.inject(this);

        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL).child("Location:" +LocalVideoActivity.metaRetriver.METADATA_KEY_LOCATION);


        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                sendThread();
            }

            private void sendThread() {
                // Create our 'model', a Chat object
                if( !(xTouch == 0 && yTouch == 0) ) {
                    long currentDuration = LocalVideoActivity.mVideoView_vw.getCurrentPosition();
                    Coordinate coord = new Coordinate(xTouch, yTouch, utils.milliSecondsToTimer(currentDuration));
                    // Create a new, auto-generated child of that chat location, and save our chat data there
                    mFirebaseRef.push().setValue(coord);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

        mPlayPause_bt.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN) // fix later to using image vector
            @Override
            public void onClick(View v) {
                if (!isPause){
                    LocalVideoActivity.mVideoView_vw.pause();
                    mPlayPause_bt.setImageResource(R.drawable.btn_play);
                    Toast.makeText(DrawingActivity.this, "Clicked pause :" + isPause,
                            Toast.LENGTH_LONG).show();
                    isPause = true;
                }
                else {
                    LocalVideoActivity.mVideoView_vw.start();
                    mPlayPause_bt.setImageResource(R.drawable.btn_pause);
                    Toast.makeText(DrawingActivity.this,"Clicked start :" + isPause,
                            Toast.LENGTH_LONG).show();
                    isPause = false;
                }
            }
        });
        
        mForward_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = LocalVideoActivity.mVideoView_vw.getCurrentPosition();
                // check if seekBackward time is greater than 0 sec
                if(currentPosition + seekForwardTime <= LocalVideoActivity.mVideoView_vw.getDuration()){
                    // forward to video
                    LocalVideoActivity.mVideoView_vw.seekTo(currentPosition + seekForwardTime);
                }else{
                    // forward to end position
                    LocalVideoActivity.mVideoView_vw.seekTo(LocalVideoActivity.mVideoView_vw.getDuration());
                }

            }
        });
        
        mBackward_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = LocalVideoActivity.mVideoView_vw.getCurrentPosition();
                // check if seekBackward time is greater than 0 sec
                if(currentPosition - seekBackwardTime >= 0){
                    // backward to video
                    LocalVideoActivity.mVideoView_vw.seekTo(currentPosition - seekBackwardTime);
                }else{
                    // backward to starting position
                    LocalVideoActivity.mVideoView_vw.seekTo(0);
                }
            }
        });

        mCapture_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* currentPosition in milliseconds */
                int currentPosition = LocalVideoActivity.mVideoView_vw.getCurrentPosition();
                /* show millisec on the screen as toast message. */
                Toast.makeText(DrawingActivity.this,
                        "Current Position: " + currentPosition + " (ms)",
                        Toast.LENGTH_LONG).show();
                
                /* Unit in microsecond */
                Bitmap bmFrame = LocalVideoActivity.metaRetriver.getFrameAtTime(currentPosition * 1000);
                if(bmFrame == null){
                    Toast.makeText(DrawingActivity.this,
                            "bmFrame == null!",
                            Toast.LENGTH_LONG).show();
                }else{
                    AlertDialog.Builder myCaptureDialog =
                            new AlertDialog.Builder(DrawingActivity.this);
                    /* imageView processing */
                    ImageView capturedImageView = new ImageView(DrawingActivity.this);
                    capturedImageView.setImageBitmap(bmFrame);
                    LayoutParams capturedImageViewLayoutParams =
                            new LayoutParams(LayoutParams.WRAP_CONTENT,
                                    LayoutParams.WRAP_CONTENT);
                    capturedImageView.setLayoutParams(capturedImageViewLayoutParams);
                    
                    /* AlertDialog process. */
                    myCaptureDialog.setView(capturedImageView);
                    myCaptureDialog.show();
                }

            }
        });
        
        mList_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mCircleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                xTouch = (int) event.getX();
                yTouch = (int) event.getY();

                return false;
            }
        });
        
        /* set progress bar values */
        videoSeekbar_sb.setProgress(0);
        videoSeekbar_sb.setMax(100);
        
        videoSeekbar_sb.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
                // remove message Handler from updating progress bar
                mHandler.removeCallbacks(mUpdateTimeTask);
            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
                int totalDuration = LocalVideoActivity.mVideoView_vw.getDuration();
                int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

                // forward or backward to certain seconds
                LocalVideoActivity.mVideoView_vw.seekTo(currentPosition);

                // update timer progress again
                updateProgressBar();
            }
        });
        
        updateProgressBar();
    }

    /**
     * Update timer on seekbar
     * */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /*
    * Background Runnable Thread* */
    private Runnable mUpdateTimeTask = new Runnable() {
        @Override
        public void run() {
            long totalDuration = LocalVideoActivity.mVideoView_vw.getDuration();
            long currentDuration = LocalVideoActivity.mVideoView_vw.getCurrentPosition();

            // Displaying Total Duration time
            mTotalDuration_tv.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            mCurrentDuration_tv.setText(""+utils.milliSecondsToTimer(currentDuration));
            // Updating progress bar
            int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            videoSeekbar_sb.setProgress(progress);
            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Toast.makeText(this,"Pressed back Button to back Main Menu.", Toast.LENGTH_SHORT).show();
            Intent in = new Intent();
            setResult(Constants.REQUEST_FINISH,in);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
