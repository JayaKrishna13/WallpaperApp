package com.krishna.jaya.livewallpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.krishna.jaya.livewallpaper.Models.Photo;
import com.squareup.picasso.Picasso;

import retrofit2.http.Url;

public class WallpaperActivity extends AppCompatActivity {

    ImageView wallPaper;
    FloatingActionButton fabDownload,fabSetWallpaper;
    ProgressDialog dialog;

Photo photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        wallPaper=findViewById(R.id.wallPaper);
        fabDownload=findViewById(R.id.fabDownload);
        fabSetWallpaper=findViewById(R.id.fabSetWallpaper);
        dialog=new ProgressDialog(this);

        photo=(Photo)getIntent().getSerializableExtra("photo");

        Picasso.get().load(photo.getSrc().getOriginal()).into(wallPaper);

        fabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager=null;
                downloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);

                Uri uri=Uri.parse(photo.getSrc().getLarge());
                DownloadManager.Request request=new DownloadManager.Request(uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(true)
                        .setTitle("WallPaper_"+photo.getPhotographer())
                        .setMimeType("image/jpeg")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,"WallPaper_"+photo.getPhotographer()+".jpg");
                downloadManager.enqueue(request);

                Toast.makeText(WallpaperActivity.this, "WallPaper Downloaded", Toast.LENGTH_SHORT).show();
            }
        });

        fabSetWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                WallpaperManager wallpaperManager=WallpaperManager.getInstance(WallpaperActivity.this);
                Bitmap bitmap=((BitmapDrawable)wallPaper.getDrawable()).getBitmap();

                try{

                    wallpaperManager.setBitmap(bitmap);


                    Toast.makeText(WallpaperActivity.this, "Wallpaper applied !", Toast.LENGTH_SHORT).show();


                }
                catch (Exception e) {

                    e.printStackTrace();
                    Toast.makeText(WallpaperActivity.this, "couldn't add wallpaper", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}