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

import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;

/**
 * Created by root on 2/19/15.
 */
public class LocalVideoActivity extends Activity {

    /* private members */
    public static VideoView mVideoView_vw;
    private static String vidAddress;
    private Uri vidUri;
    private Boolean firstLooking = true;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibe);
        
        /* Video settings */
        mVideoView_vw = (VideoView) findViewById(R.id.videoView_vw);
        vidAddress = getIntent().getStringExtra(Constants.EXTRA_ANSWER_IS_TRUE);
        Toast.makeText(LocalVideoActivity.this,vidAddress, Toast.LENGTH_LONG).show();

        vidUri = Uri.parse(vidAddress);

        /* Custom video settings for internet */
        mVideoView_vw.setVideoURI(vidUri);
        mVideoView_vw.setMediaController(new MediaController(LocalVideoActivity.this));
        mVideoView_vw.start();
        
        Intent transparentLayoutInt = new Intent(LocalVideoActivity.this, DrawingActivity.class);
        startActivityForResult(transparentLayoutInt,Constants.REQUEST_CHOOSER);
        
        

    }
}
