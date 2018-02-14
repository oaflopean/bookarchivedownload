package com.copypastapublishing.bookarchive;

/**
 * Created by jordan on 2/13/18.
 */

        import android.app.DownloadManager;
        import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Environment;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;



        import java.io.BufferedOutputStream;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.OutputStream;

public class ProfileActivity extends AppCompatActivity {
    private DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        // initialize the View objects
        TextView titleview = (TextView) findViewById(R.id.title);
        Button saveEpub = (Button) findViewById(R.id.shareProfile);
        Button saveText=(Button) findViewById(R.id.shareProfile2);
        TextView authorview = (TextView) findViewById(R.id.author);

        //getting the passed intent
        Intent intent = getIntent();
        final String title = intent.getStringExtra(DevelopersAdapter.KEY_TITLE);
        final String author = intent.getStringExtra(DevelopersAdapter.KEY_AUTHOR);
        final String TextURL=intent.getStringExtra(DevelopersAdapter.KEY_TEXT);
        final String epubURL=intent.getStringExtra(DevelopersAdapter.KEY_EPUB);
        final String idNum=intent.getStringExtra(DevelopersAdapter.KEY_ID);

        setTitle("Downloads");

        //using picasso to load images into the defined imageView


        titleview.setText(title);
        authorview.setText(author);

        //setting the onclick function of the developer url (opens in browser)
        saveEpub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(epubURL);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS.toString(), title+".epub");
                Long reference = downloadManager.enqueue(request);

            }
        });



        //setting the share intent for the profile
        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(TextURL);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS.toString(), title+".txt");
                Long reference = downloadManager.enqueue(request);

            }
        });


    }





}
