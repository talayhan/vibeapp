package net.talayhan.android.vibeproject.Controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
        /*
        album_art = (ImageView) findViewById(R.id.album_art); 
        album = (TextView) findViewById(R.id.Album); 
        artist = (TextView) findViewById(R.id.artist_name); 
        genre = (TextView) findViewById(R.id.genre); 
        */
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
        metaRetriver = new MediaMetadataRetriever();
        metaRetriver.setDataSource(vidAddress);
        try {
            testMediaRetrieverInfo();
        }
        catch (Exception e) 
        {
            album_art.setBackgroundColor(Color.GRAY);
            album.setText("[Exception] - Unknown Album");
            artist.setText("[Exception] - Unknown Artist");
            genre.setText("[Exception] - Unknown Genre");
        }

        
        /* Custom video settings for internet */
        mVideoView_vw.setVideoURI(vidUri);
        mVideoView_vw.setMediaController(new MediaController(LocalVideoActivity.this));
        mVideoView_vw.start();
        
        Intent transparentLayoutInt = new Intent(LocalVideoActivity.this, DrawingActivity.class);
        startActivityForResult(transparentLayoutInt,Constants.REQUEST_FINISH);



    }

    /**
     * @deprecated This method may useless. *
     * @return void
     * This method checks mediaretriever object to get all information about video.
     * * * * */
    private void testMediaRetrieverInfo() {

            /*
            art = metaRetriver.getEmbeddedPicture();
            Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
            album_art.setImageBitmap(songImage);
            album.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
            artist.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
            genre.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE));

            genre.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));

            */
            /*

            allOtherInformation += "\n\nNew Row\n";
            allOtherInformation += metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR) + "\n";
            allOtherInformation += metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST)+ "\n";
            allOtherInformation += metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)+ "\n";
            allOtherInformation += metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CD_TRACK_NUMBER)+ "\n";
            allOtherInformation += metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE)+ "\n";
            allOtherInformation += metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION) + "\n\n";

            allOtherInformation += metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE)+ "\n";
            allOtherInformation += metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO)+ "\n";
            allOtherInformation += metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)+ "\n";
            allOtherInformation += metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)+ "\n";
            allOtherInformation += metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)+ "\n";
            allOtherInformation += metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)+ "\n\n";

            allOtherInformation += metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR)+ "\n";
            allOtherInformation += metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_LOCATION)+ "\n";
            allOtherInformation += metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE)+ "\n";

            informations.setText(allOtherInformation);

            */
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
