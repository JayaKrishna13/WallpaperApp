package com.krishna.jaya.livewallpaper;

import android.content.Context;
import android.widget.Toast;

import com.krishna.jaya.livewallpaper.Listeners.CuratedResponselistener;
import com.krishna.jaya.livewallpaper.Listeners.SearchResponselistener;
import com.krishna.jaya.livewallpaper.Models.CuratedApiResponse;
import com.krishna.jaya.livewallpaper.Models.SearchApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class RequestManager {
    public RequestManager(Context context) {
        this.context = context;
    }

    Context context;
    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public void getCuratedWallPapers(CuratedResponselistener listener,String page){
        CallWallPaperList callWallPaperList=retrofit.create(CallWallPaperList.class);
        Call<CuratedApiResponse> call=callWallPaperList.getWallPapers(page,"80");
        call.enqueue(new Callback<CuratedApiResponse>() {
            @Override
            public void onResponse(Call<CuratedApiResponse> call, Response<CuratedApiResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, "An Error Occurred", Toast.LENGTH_SHORT).show();
                }
                else{
                    listener.onFetch(response.body(), response.message());
                }
            }

            @Override
            public void onFailure(Call<CuratedApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());

            }
        });



    }
    public void searchCuratedWallPapers(SearchResponselistener listener, String page,String query){
        CallWallPaperListSearch  callWallPaperListSearch=retrofit.create(CallWallPaperListSearch.class);
        Call<SearchApiResponse> call=callWallPaperListSearch. searchWallPapers(query,page,"80");
        call.enqueue(new Callback<SearchApiResponse>() {
            @Override
            public void onResponse(Call<SearchApiResponse> call, Response<SearchApiResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, "An Error Occurred", Toast.LENGTH_SHORT).show();
                }
                else{
                    listener.onFetch(response.body(), response.message());
                }
            }

            @Override
            public void onFailure(Call<SearchApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());

            }
        });



    }

    private interface CallWallPaperList{
        @Headers({
                "Accept: application/json",
                "Authorization: 563492ad6f91700001000001d40326dcc5eb4d20a93239d8e6620660"
        })
        @GET("curated")
        Call<CuratedApiResponse> getWallPapers(
                @Query("page") String page,
                @Query("per_page") String per_page
        );

    }

    private interface CallWallPaperListSearch{
        @Headers({
                "Accept: application/json",
                "Authorization: 563492ad6f91700001000001d40326dcc5eb4d20a93239d8e6620660"
        })
        @GET("search")
        Call<SearchApiResponse> searchWallPapers(
                @Query("query") String query,
                @Query("page") String page,
                @Query("per_page") String per_page
        );

    }


}
