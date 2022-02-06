package com.krishna.jaya.livewallpaper.Listeners;


import com.krishna.jaya.livewallpaper.Models.SearchApiResponse;

public  interface SearchResponselistener {
   void onFetch(SearchApiResponse response, String message);
   void onError(String message);

}
