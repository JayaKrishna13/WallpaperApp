package com.krishna.jaya.livewallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.krishna.jaya.livewallpaper.Adapters.CuratedAdapter;
import com.krishna.jaya.livewallpaper.Listeners.CuratedResponselistener;
import com.krishna.jaya.livewallpaper.Listeners.OnRecyclerClickListener;
import com.krishna.jaya.livewallpaper.Listeners.SearchResponselistener;
import com.krishna.jaya.livewallpaper.Models.CuratedApiResponse;
import com.krishna.jaya.livewallpaper.Models.Photo;
import com.krishna.jaya.livewallpaper.Models.SearchApiResponse;

import java.net.URL;
import java.util.List;
import java.util.Random;

import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity implements OnRecyclerClickListener {
   RecyclerView recyclerView_home;
   ProgressDialog dialog;
   RequestManager requestManager;
   CuratedAdapter adapter;
   FloatingActionButton fabNext,fabPrev;
   int page,searchpage;
   int counter;
   String query1;
    final Random myRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabNext=findViewById(R.id.fabNext);
        fabPrev=findViewById(R.id.fabPrev);

        dialog=new ProgressDialog(this);
        dialog.setTitle("Loading...");
        requestManager=new RequestManager(this);
        requestManager.getCuratedWallPapers(curatedResponselistener,String.valueOf(myRandom.nextInt(10)));

        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              if(counter==0){
                  String nextPage=String.valueOf(page+1);

                  requestManager.getCuratedWallPapers(curatedResponselistener, nextPage);

                  dialog.show();
              }
              else if(counter==1){
                  String nextPage1=String.valueOf(searchpage+1);
                  requestManager.searchCuratedWallPapers(searchResponselistener,nextPage1,query1);

                  dialog.show();
              }

            }
        });


        fabPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter==0){if(page>1){
                    String prevPage=String.valueOf(page-1);
                    requestManager.getCuratedWallPapers(curatedResponselistener, prevPage);

                    dialog.show();}}

                else if(counter==1){ if(searchpage>1){
                    String prevPage1=String.valueOf(searchpage-1);
                    requestManager.searchCuratedWallPapers(searchResponselistener,prevPage1,query1);

                    dialog.show();}}




            }
        });


    }




    public final CuratedResponselistener curatedResponselistener=new CuratedResponselistener() {
        @Override
        public void onFetch(CuratedApiResponse response, String message) {
            dialog.dismiss();
            counter=0;
            if(response.getPhotos().isEmpty()){
                Toast.makeText(MainActivity.this, "No Image Found", Toast.LENGTH_SHORT).show();
                return;
            }
            page=response.getPage();
            showData(response.getPhotos());

        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

        }
    };

    private void showData(List<Photo> photos) {

        recyclerView_home=findViewById(R.id.recycler_home);
        recyclerView_home.setHasFixedSize(true);
        recyclerView_home.setLayoutManager(new GridLayoutManager
                (this,2));
        adapter=new CuratedAdapter(MainActivity.this,photos,this);

        recyclerView_home.setAdapter(adapter);


    }

    @Override
    public void onClick(Photo photo) {

        startActivity(new Intent(MainActivity.this,WallpaperActivity.class)
                .putExtra("photo",photo));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem= menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                requestManager.searchCuratedWallPapers(searchResponselistener,"1",query);
                dialog.show();
                query1=query;

                return true;


            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private final SearchResponselistener searchResponselistener=new SearchResponselistener() {
        @Override
        public void onFetch(SearchApiResponse response, String message) {
            dialog.dismiss();
            counter=1;
            if(response.getPhotos().isEmpty())
            {
                Toast.makeText(MainActivity.this, "no image found", Toast.LENGTH_SHORT).show();
                return;
            }
            searchpage=response.getPage();

            showData(response.getPhotos());



        }

        @Override
        public void onError(String message) {


        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();



    }
}