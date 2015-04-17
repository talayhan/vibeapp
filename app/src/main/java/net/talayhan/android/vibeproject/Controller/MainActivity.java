package net.talayhan.android.vibeproject.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;

import net.talayhan.android.vibeproject.R;
import net.talayhan.android.vibeproject.Util.Constants;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends Activity {

    @InjectView(R.id.fileChooser_bt) Button mFileChooser_bt;
    @InjectView(R.id.playBack_btn) Button mPlayback_bt;
    @InjectView(R.id.chart_bt) Button mChart_bt;

    private String videoPath;
    private String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

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
                        
                        /* check the device network state */
                        if (!isOnline()){
                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setContentText("Your device is now offline!\n" +
                                            "Please open your Network.")
                                    .setTitleText("Open Network Connection")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            showNoConnectionDialog(MainActivity.this);
                                            sweetAlertDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();
                        }else {

                            // Create the intent to start video activity
                            Intent i = new Intent(MainActivity.this, LocalVideoActivity.class);
                            i.putExtra(Constants.EXTRA_ANSWER_IS_TRUE, vidAddress);
                            startActivity(i);

                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }
                });
                pDialog.setCancelable(true);
                pDialog.show();

            }
        });

        mPlayback_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.NORMAL_TYPE)
                        .setContentText("Please first label some video!\n" +
                                        "Later come back here!.")
                        .setTitleText("Playback")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
        
        mChart_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ChartRecyclerView.class);
                startActivityForResult(i, Constants.REQUEST_CHART);
            }
        });
        
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
    
    /*
    * This method checks network situation, if device is airplane mode or something went wrong on
    * network stuff. Method returns false, otherwise return true.
    *
    * - This function inspired by below link,
    * http://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-timeouts
    *
    * @return boolean - network state 
    * * * */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Display a dialog that user has no internet connection
     * @param ctx1
     *
     * Code from: http://osdir.com/ml/Android-Developers/2009-11/msg05044.html
     */
    public static void showNoConnectionDialog(Context ctx1) {
        final Context ctx = ctx1;
        final SweetAlertDialog builder = new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE);
        builder.setCancelable(true);
        builder.setContentText("Open internet connection");
        builder.setTitle("No connection error!");
        builder.setConfirmText("Open wirless.");
        builder.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                ctx.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                builder.dismissWithAnimation();     
            }
        });

        builder.show();
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
        }else if (id == R.id.action_search){
            openSearch();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openSearch() {
        Toast.makeText(this,"Clicked Search button", Toast.LENGTH_SHORT).show();
    }
}
