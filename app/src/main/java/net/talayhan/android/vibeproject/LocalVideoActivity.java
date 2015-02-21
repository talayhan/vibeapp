package net.talayhan.android.vibeproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * Created by root on 2/19/15.
 */
public class LocalVideoActivity extends Activity {

    /* private members */
    public static VideoView mVideoView_vw;

    /* define string to test VideoView component */
    String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
    Uri vidUri = Uri.parse(vidAddress);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibe);
        
        /* Video settings */
        mVideoView_vw = (VideoView) findViewById(R.id.videoView_vw);
        
        /* Put here AlertDialog to make a decision between  */
        new AlertDialog.Builder(this)
                .setTitle("Internet or Local?")
                .setMessage("Would you like to watch the video on the internet or local storage?")
                .setPositiveButton("Internet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Internet
                        /* Custom video settings for internet */
                        mVideoView_vw.setVideoURI(vidUri);
                        mVideoView_vw.setMediaController(new MediaController(LocalVideoActivity.this));
                        mVideoView_vw.start();
                        
                        /* Start transparent activity */
                        Intent intent = new Intent(LocalVideoActivity.this, DrawingActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Local", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Local
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}
