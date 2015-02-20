package net.talayhan.android.vibeproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by root on 2/20/15.
 */
public class TranslucentDrawingView extends View {
    public TranslucentDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // detect user touch

        //retrieve the x and y positions of the user touch
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(getContext(),"ActionDown",Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_MOVE:
                Toast.makeText(getContext(),"ActionMove",Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_UP:
                Toast.makeText(getContext(),"ActionUp",Toast.LENGTH_SHORT).show();
                break;
            default:
                return false;
        }

        // Calling invalidate, will cause the onDraw method to execute.
        invalidate();
        return true;
    }
}
