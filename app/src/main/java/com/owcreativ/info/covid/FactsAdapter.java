package com.owcreativ.info.covid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


public class FactsAdapter extends RecyclerView.Adapter<FactsAdapter.RvViewHolder>{
    Context context;
    ArrayList<FactsData> factsData;
    public FactsAdapter(Context context, ArrayList<FactsData> factsData){
        this.context = context;
        this.factsData = factsData;
    }
    View view;
    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        view = inflater.from(parent.getContext()).inflate(R.layout.facts_layout,parent,false);
        RvViewHolder rvViewHolder = new RvViewHolder(view);
        return rvViewHolder;
    }
    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        final FactsData factsData = this.factsData.get(position);
        holder.tvTitle.setText(factsData.getTitle());
        String imgUrl = factsData.getPhoto();


       Glide.with(context).load(factsData.getPhoto())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);

        /*Glide.with(context).load(newsData.getAvatar())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.avatar);*/

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
        return factsData.size();
    }
    public class RvViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCreatedate,tvPosition,tvDescription;
        ImageView thumbnail,avatar;
        LinearLayout llItem;
        public RvViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCreatedate = itemView.findViewById(R.id.tvCreatedate);
            tvPosition = itemView.findViewById(R.id.tvPosition);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            avatar = itemView.findViewById(R.id.avatar);
            llItem = itemView.findViewById(R.id.ll_item);
        }
    }
}
