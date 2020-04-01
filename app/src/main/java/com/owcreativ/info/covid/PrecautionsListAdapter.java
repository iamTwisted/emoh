package com.owcreativ.info.covid;

import android.content.Context;
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

import java.util.ArrayList;


public class PrecautionsListAdapter extends RecyclerView.Adapter<PrecautionsListAdapter.RvViewHolder>{
    Context context;
    ArrayList<PrecautionsListData> precautionData;
    public PrecautionsListAdapter(Context context, ArrayList<PrecautionsListData> precautionData){
        this.context = context;
        this.precautionData = precautionData;
    }
    View view;
    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        view = inflater.from(parent.getContext()).inflate(R.layout.precautionslist_layout,parent,false);
        RvViewHolder rvViewHolder = new RvViewHolder(view);
        return rvViewHolder;
    }
    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        final PrecautionsListData precautionData = this.precautionData.get(position);
        holder.tvTitle.setText(precautionData.getName());
        holder.tvDescription.setText(precautionData.getDescription());
        String imgUrl = precautionData.getImage();

/*
        //get first letter of each String item
        String firstLetter = String.valueOf(precautionData.getName().charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        holder.thumbnail.setImageDrawable(drawable);*/

        Glide.with(context).load(precautionData.getImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);



        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Context context = view.getContext();
//                Intent intent = new Intent(context, MealViewActivity.class);
//                Bundle b = new Bundle();
//                b.putInt("meal", promoData.getId()); //Your id
//                intent.putExtras(b); //Put your id to your next Intent
//                context.startActivity(intent);


            }
        });
    }



    @Override
    public int getItemCount() {
        return precautionData.size();
    }
    public class RvViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        ImageView thumbnail;
        LinearLayout llItem;
        public RvViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            llItem = itemView.findViewById(R.id.ll_item);
        }
    }
}
