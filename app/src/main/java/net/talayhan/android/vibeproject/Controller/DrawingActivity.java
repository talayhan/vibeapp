package net.talayhan.android.vibeproject.Controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import net.talayhan.android.vibeproject.R;
import net.talayhan.android.vibeproject.Util.Constants;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

/**
 * Created by root on 2/20/15.
 */
public class DrawingActivity extends Activity {

    /* Toolbar buttons */
    private ImageButton mPlayPause_bt;
    private ImageButton mForward_bt;
    private ImageButton mBackward_bt;
    private ImageButton mCapture_bt;
    
    /* Footer seekbar. */
    private DiscreteSeekBar videoSeekbar_sb;

    /* Utility members */
    private Boolean isPause = false;
    private int seekForwardTime = 5000;
    private int seekBackwardTime = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        /* initialize the button */
        mPlayPause_bt = (ImageButton) findViewById(R.id.play_bt);
        mPlayPause_bt.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN) // fix later to using image vector
            @Override
            public void onClick(View v) {
                if (!isPause){
                    LocalVideoActivity.mVideoView_vw.pause();
                    mPlayPause_bt.setImageResource(R.drawable.btn_play);
                    Toast.makeText(DrawingActivity.this, "Clicked pause :" + isPause, Toast.LENGTH_LONG).show();
                    isPause = true;
                }
                else {
                    LocalVideoActivity.mVideoView_vw.start();
                    mPlayPause_bt.setImageResource(R.drawable.btn_pause);
                    Toast.makeText(DrawingActivity.this,"Clicked start :" + isPause,Toast.LENGTH_LONG).show();
                    isPause = false;
                }
            }
        });
        
        /* Initialize forward button */
        mForward_bt = (ImageButton) findViewById(R.id.forward_bt);
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
        
        mBackward_bt = (ImageButton) findViewById(R.id.backward_bt);
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

        /* Initialize capture button */
        mCapture_bt = (ImageButton) findViewById(R.id.capture_bt);
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
        
        /* Initialize seekbar to using forward and backward */
        videoSeekbar_sb = (DiscreteSeekBar) findViewById(R.id.discrete_seek_bar);
        /* set progress bar values */
        videoSeekbar_sb.setProgress(0);
        videoSeekbar_sb.setMax(100);
        videoSeekbar_sb.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
    }
    

    
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
