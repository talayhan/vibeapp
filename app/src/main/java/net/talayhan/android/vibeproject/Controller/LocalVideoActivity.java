package net.talayhan.android.vibeproject.Controller;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import net.talayhan.android.vibeproject.R;
import net.talayhan.android.vibeproject.Util.Constants;

/**
 * Created by root on 2/19/15.
 */
public class LocalVideoActivity extends Activity {

    /* private members */
    public static VideoView mVideoView_vw;
    public static MediaMetadataRetriever metaRetriver;
    private static String vidAddress;
    private Uri vidUri;
    private Boolean firstLooking = true;
    private String allOtherInformation;

    
    /* Test variables */
    ImageView album_art; 
    TextView album, artist, genre, informations;
    
    byte[] art;

    // Fetch Id's form xml 
    public void getInit() 
    {
        informations = (TextView) findViewById(R.id.info);
    }
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibe);
        
        /* Video settings */
        mVideoView_vw = (VideoView) findViewById(R.id.videoView_vw);
        vidAddress = getIntent().getStringExtra(Constants.EXTRA_ANSWER_IS_TRUE);
        /* Show video file url's to as Toast message. */
        Toast.makeText(LocalVideoActivity.this,vidAddress, Toast.LENGTH_LONG).show();
        vidUri = Uri.parse(vidAddress);

        getInit();
        /* Custom video settings for internet */
        mVideoView_vw.setVideoURI(vidUri);
        mVideoView_vw.setMediaController(new MediaController(LocalVideoActivity.this));
        mVideoView_vw.start();
        
        Intent transparentLayoutInt = new Intent(LocalVideoActivity.this, DrawingActivity.class);
        startActivityForResult(transparentLayoutInt,Constants.REQUEST_FINISH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case Constants.REQUEST_FINISH:
                    finish();
                break;
        }
    }
}
