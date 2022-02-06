package com.krishna.jaya.livewallpaper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.krishna.jaya.livewallpaper.Listeners.OnRecyclerClickListener;
import com.krishna.jaya.livewallpaper.Models.Photo;
import com.krishna.jaya.livewallpaper.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CuratedAdapter extends RecyclerView.Adapter<CuratedAdapter.CuratedViewHolder> {
   Context context;
   List<Photo> photoList;
   OnRecyclerClickListener listener;

    public CuratedAdapter(Context context, List<Photo> photoList, OnRecyclerClickListener listener) {
        this.context = context;
        this.photoList = photoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CuratedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CuratedViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.home_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CuratedViewHolder holder, int position) {
        Picasso.get().load(photoList.get(position).getSrc().getMedium()).into(holder.imageView_list);
        holder.home_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(photoList.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class CuratedViewHolder extends RecyclerView.ViewHolder{
      CardView home_list_container;
      ImageView imageView_list;
        public CuratedViewHolder(@NonNull View itemView) {
            super(itemView);
           home_list_container=itemView.findViewById(R.id.home_list_container);
           imageView_list=itemView.findViewById(R.id.imageView_list);

        }
    }


}
