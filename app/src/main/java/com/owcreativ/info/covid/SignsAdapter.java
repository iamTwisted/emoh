package com.owcreativ.info.covid;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
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
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;


import java.util.ArrayList;


public class SignsAdapter extends RecyclerView.Adapter<SignsAdapter.RvViewHolder>{
    Context context;
    ArrayList<SignsData> signsData;
    public SignsAdapter(Context context, ArrayList<SignsData> signsData){
        this.context = context;
        this.signsData = signsData;
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
    public void onBindViewHolder(final RvViewHolder holder, int position) {
        final SignsData signsData = this.signsData.get(position);
        holder.tvTitle.setText(signsData.getName());
        String imgUrl = signsData.getImage();


        //get first letter of each String item
        String firstLetter = String.valueOf(signsData.getName().charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRect(firstLetter, color);
//                .buildRound(firstLetter, color); // radius in px

        holder.thumbnail.setImageDrawable(drawable);


        // With thumbnail url
//        Glide.with(context).load(signsData.getImage())
//                .thumbnail(Glide.with(context).load(thumbUrl))
//                .apply(requestOptions).into(holder.thumbnail);

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
        return signsData.size();
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
