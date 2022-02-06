package com.krishna.jaya.livewallpaper.Listeners;

import com.krishna.jaya.livewallpaper.Models.CuratedApiResponse;

public  interface CuratedResponselistener {
   void onFetch(CuratedApiResponse response,String message);
   void onError(String message);

}
