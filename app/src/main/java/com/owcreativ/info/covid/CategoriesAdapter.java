package com.owcreativ.info.covid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.RvViewHolder>{
    Context context;
    ArrayList<CategoriesData> categoriesData;
    public CategoriesAdapter(Context context, ArrayList<CategoriesData> categoriesData){
        this.context = context;
        this.categoriesData = categoriesData;
    }
    View view;
    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        view = inflater.from(parent.getContext()).inflate(R.layout.category_layout,parent,false);
        RvViewHolder rvViewHolder = new RvViewHolder(view);
        return rvViewHolder;
    }
    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        final CategoriesData categoriesData = this.categoriesData.get(position);
        holder.tvTitle.setText(categoriesData.getName());
        String imgUrl = categoriesData.getPhoto();


        Glide.with(context).load(categoriesData.getPhoto())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);




        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view.getContext();
                Intent intent = new Intent(context, DetailsActivity.class);
                Bundle b = new Bundle();
                b.putInt("id", categoriesData.getId()); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                context.startActivity(intent);


            }
        });
    }



    @Override
    public int getItemCount() {
        return categoriesData.size();
    }
    public class RvViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView thumbnail;
        LinearLayout llItem;
        public RvViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            llItem = itemView.findViewById(R.id.ll_item);
        }
    }
}
