package net.talayhan.android.vibeproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
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

    /* Test variables */
    ImageView album_art; 
    TextView album, artist, genre;
    MediaMetadataRetriever metaRetriver;
    byte[] art;

    // Fetch Id's form xml 
    public void getInit() 
    { 
        album_art = (ImageView) findViewById(R.id.album_art); 
        album = (TextView) findViewById(R.id.Album); 
        artist = (TextView) findViewById(R.id.artist_name); 
        genre = (TextView) findViewById(R.id.genre); 
    }

    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibe);
        
        /* Video settings */
        mVideoView_vw = (VideoView) findViewById(R.id.videoView_vw);
        vidAddress = getIntent().getStringExtra(Constants.EXTRA_ANSWER_IS_TRUE);
        Toast.makeText(LocalVideoActivity.this,vidAddress, Toast.LENGTH_LONG).show();

        vidUri = Uri.parse(vidAddress);
        
        /*
        getInit();
        metaRetriver = new MediaMetadataRetriever(); 
        metaRetriver.setDataSource(vidAddress);
        try { 
            art = metaRetriver.getEmbeddedPicture(); 
            Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length); 
            album_art.setImageBitmap(songImage); 
            album.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)); 
            artist.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)); 
            genre.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE));

            genre.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        }
        catch (Exception e) 
        { 
            album_art.setBackgroundColor(Color.GRAY); 
            album.setText("Unknown Album"); 
            artist.setText("Unknown Artist"); 
            genre.setText("Unknown Genre"); 
        }
        */

        
        /* Custom video settings for internet */
        mVideoView_vw.setVideoURI(vidUri);
        mVideoView_vw.setMediaController(new MediaController(LocalVideoActivity.this));
        mVideoView_vw.start();
        
        Intent transparentLayoutInt = new Intent(LocalVideoActivity.this, DrawingActivity.class);
        startActivityForResult(transparentLayoutInt,Constants.REQUEST_CHOOSER);

        

    }
}
