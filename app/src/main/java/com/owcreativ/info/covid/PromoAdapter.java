package com.owcreativ.info.covid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.RvViewHolder>{
    Context context;
    ArrayList<PromoData> promoData;
    public PromoAdapter(Context context, ArrayList<PromoData> promoData){
        this.context = context;
        this.promoData = promoData;
    }
    View view;
    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        view = inflater.from(parent.getContext()).inflate(R.layout.symptomslist,parent,false);
        RvViewHolder rvViewHolder = new RvViewHolder(view);
        return rvViewHolder;
    }
    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        final PromoData promoData = this.promoData.get(position);
        holder.tvTitle.setText(promoData.getTitle());


        Glide.with(context).load(promoData.getPhoto())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);


        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Context context = view.getContext();
//                Intent intent = new Intent(context, TariffActivity.class);
//                Bundle b = new Bundle();
//                b.putInt("code", Integer.parseInt(promoData.getCode())); //Your id
//                intent.putExtras(b); //Put your id to your next Intent
//                context.startActivity(intent);


            }
        });
    }



    @Override
    public int getItemCount() {
        return promoData.size();
    }
    public class RvViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        public ImageView thumbnail;
        RelativeLayout llItem;

        public RvViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            thumbnail = view.findViewById(R.id.thumbnail);
            llItem = itemView.findViewById(R.id.ll_item);
        }
    }
}
