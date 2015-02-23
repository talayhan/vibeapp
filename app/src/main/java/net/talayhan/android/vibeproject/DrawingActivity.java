package net.talayhan.android.vibeproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by root on 2/20/15.
 */
public class DrawingActivity extends Activity {

    private Button mPlayPause_bt;
    private Button mForward_bt;
    private Boolean isPause = false;
    private SeekBar videoProgressbar_sb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        /* initialize the button */
        mPlayPause_bt = (Button) findViewById(R.id.play_bt);
        mPlayPause_bt.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN) // fix later to using image vector
            @Override
            public void onClick(View v) {
                if (!isPause){
                    LocalVideoActivity.mVideoView_vw.pause();
                    Toast.makeText(DrawingActivity.this, "Clicked pause :" + isPause, Toast.LENGTH_LONG).show();
                    mPlayPause_bt.setBackground(getResources().getDrawable(R.drawable.abc_btn_check_to_on_mtrl_015));
                    isPause = true;
                }
                else {
                    LocalVideoActivity.mVideoView_vw.start();
                    Toast.makeText(DrawingActivity.this,"Clicked start :" + isPause,Toast.LENGTH_LONG).show();
                    mPlayPause_bt.setBackground(getResources().getDrawable(R.drawable.abc_btn_check_to_on_mtrl_000));
                    isPause = false;
                }
            }
        });
        
        mForward_bt = (Button) findViewById(R.id.forward_bt);
        mForward_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalVideoActivity.mVideoView_vw.seekTo(540);
            }
        });

    }
}
