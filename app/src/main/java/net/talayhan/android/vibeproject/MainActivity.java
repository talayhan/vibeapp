package net.talayhan.android.vibeproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends ActionBarActivity {

    /* private members */
    private Button mFileChooser_bt;
    private Button mChart_bt;
    private String videoPath;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFileChooser_bt = (Button) findViewById(R.id.fileChooser_bt);
        mFileChooser_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {        
                /* Progress dialog */
                pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.NORMAL_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Network Type");
                pDialog.setContentText("How would you like to watch video?");
                pDialog.setConfirmText("Local");
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        // Local
                        // Create the ACTION_GET_CONTENT Intent
                        Intent getContentIntent = FileUtils.createGetContentIntent();

                        Intent intent = Intent.createChooser(getContentIntent, "Select a file");
                        startActivityForResult(intent, Constants.REQUEST_CHOOSER);

                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
                pDialog.setCancelText("Internet");
                pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
                pDialog.setCancelable(true);
                pDialog.show();

            }
        });
        
        mChart_bt = (Button) findViewById(R.id.chart_bt);
        mChart_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nothing do it yet
            }
        });
        
        
        
        /*
        new AlertDialog.Builder(this)
                .setTitle("Internet or Local?")
                .setMessage("Would you like to watch the video on the internet or local storage?")
                .setPositiveButton("Internet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                        // Internet
                            /* define string to test VideoView component *
                        vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
                        vidUri = Uri.parse(vidAddress);
                            
                            /* Custom video settings for internet *
                        mVideoView_vw.setVideoURI(vidUri);
                        mVideoView_vw.setMediaController(new MediaController(LocalVideoActivity.this));
                        mVideoView_vw.start();
                            
                            /* Start transparent activity *
                        Intent intent = new Intent(LocalVideoActivity.this, DrawingActivity.class);
                        startActivityForResult(intent, Constants.REQUEST_CHOOSER);
                        
                        *
                    }
                })

                */

        /* initialize file chooser button *
        mFileChooser_bt = (Button) findViewById(R.id.fileChooser_bt);
        mFileChooser_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the ACTION_GET_CONTENT Intent
                Intent getContentIntent = FileUtils.createGetContentIntent();

                Intent intent = Intent.createChooser(getContentIntent, "Select a file");
                startActivityForResult(intent, Constants.REQUEST_CHOOSER);
            }
        });
        
        /* initialize video test button *
        mVibe_bt = (Button) findViewById(R.id.vibeTest_bt);
        mVibe_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the intent to start video activity
                Intent i = new Intent(MainActivity.this, LocalVideoActivity.class);
                startActivity(i);
                
            }
        });
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_CHOOSER:
                if (resultCode == RESULT_OK) {

                    final Uri uri = data.getData();

                    // Get the File path from the Uri
                    String path = FileUtils.getPath(this, uri);
                    
                    Toast.makeText(this, "Choosen file: " + path,Toast.LENGTH_LONG).show();

                    // Alternatively, use FileUtils.getFile(Context, Uri)
                    if (path != null && FileUtils.isLocal(path)) {
                        File file = new File(path);
                    }

                    // Create the intent to start video activity
                    Intent i = new Intent(MainActivity.this, LocalVideoActivity.class);
                    i.putExtra(Constants.EXTRA_ANSWER_IS_TRUE,path);
                    startActivity(i);

                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
