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
        
        if(firstLooking == true) {
            firstLooking = false;   // Now anymore, first time
            /* Put here AlertDialog to make a decision between  */
            new AlertDialog.Builder(this)
                    .setTitle("Internet or Local?")
                    .setMessage("Would you like to watch the video on the internet or local storage?")
                    .setPositiveButton("Internet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Internet
                            /* define string to test VideoView component */
                            vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
                            vidUri = Uri.parse(vidAddress);
                            
                            /* Custom video settings for internet */
                            mVideoView_vw.setVideoURI(vidUri);
                            mVideoView_vw.setMediaController(new MediaController(LocalVideoActivity.this));
                            mVideoView_vw.start();
                            
                            /* Start transparent activity */
                            Intent intent = new Intent(LocalVideoActivity.this, DrawingActivity.class);
                            startActivityForResult(intent, Constants.REQUEST_CHOOSER);
                        }
                    })
                    .setNegativeButton("Local", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Local
                            // Create the ACTION_GET_CONTENT Intent
                            Intent getContentIntent = FileUtils.createGetContentIntent();

                            Intent intent = Intent.createChooser(getContentIntent, "Select a file");
                            startActivityForResult(intent, Constants.REQUEST_CHOOSER);

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_CHOOSER:
                if (resultCode == RESULT_OK) {

                    final Uri uri = data.getData();

                    // Get the File path from the Uri
                    vidAddress = FileUtils.getPath(this, uri);
                    /* define string to test VideoView component */
                    vidUri = Uri.parse(vidAddress);
                    Toast.makeText(this, "Choosen file: " + vidAddress,Toast.LENGTH_LONG).show();
                    
                    /* Custom video settings for internet */
                    mVideoView_vw.setVideoURI(vidUri);
                    mVideoView_vw.setMediaController(new MediaController(LocalVideoActivity.this));
                    mVideoView_vw.start();

                    /* Start transparent activity */
                    Intent intent2 = new Intent(LocalVideoActivity.this, DrawingActivity.class);
                    startActivityForResult(intent2, Constants.REQUEST_CHOOSER);

                    // Alternatively, use FileUtils.getFile(Context, Uri)
                    if (vidAddress != null && FileUtils.isLocal(vidAddress)) {
                        File file = new File(vidAddress);
                    }
                }
                break;
        }
    }
}
