package com.owcreativ.info.covid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;


public class PrecautionAdapter extends RecyclerView.Adapter<PrecautionAdapter.RvViewHolder>{
    Context context;
    ArrayList<PrecautionData> precautionData;
    public PrecautionAdapter(Context context, ArrayList<PrecautionData> precautionData){
        this.context = context;
        this.precautionData = precautionData;
    }
    View view;
    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        view = inflater.from(parent.getContext()).inflate(R.layout.precaution_layout,parent,false);
        RvViewHolder rvViewHolder = new RvViewHolder(view);
        return rvViewHolder;
    }
    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        final PrecautionData precautionData = this.precautionData.get(position);
        holder.tvTitle.setText(precautionData.getName());
        String imgUrl = precautionData.getImage();


        //get first letter of each String item
        String firstLetter = String.valueOf(precautionData.getName().charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        holder.thumbnail.setImageDrawable(drawable);

       /* Glide.with(context)
                .load(precautionData.getImage())
                .thumbnail(0.5f)
                .into(holder.thumbnail);*/

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view.getContext();
                Intent intent = new Intent(context, PreDetailsActivity.class);
                Bundle b = new Bundle();
                b.putInt("id", precautionData.getId()); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                context.startActivity(intent);
                ((Activity)context).finish();


            }
        });
    }



    @Override
    public int getItemCount() {
        return precautionData.size();
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
