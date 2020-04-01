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


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Carlos Magagula on 27/03/2020.
 */

public class KnowledgeAdapter extends RecyclerView.Adapter<KnowledgeAdapter.HeroViewHolder> {


    private List<KnowledgeData> knowledgeDataList;
    private Context context;

    private static int currentPosition = 0;

    public KnowledgeAdapter(List<KnowledgeData> knowledgeDataList, Context context) {
        this.knowledgeDataList = knowledgeDataList;
        this.context = context;
    }

    @Override
    public HeroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.knowledge_layout, parent, false);
        return new HeroViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final HeroViewHolder holder, final int position) {
        KnowledgeData knowledgeData = knowledgeDataList.get(position);
        holder.tvTitle.setText(knowledgeData.getTitle());
        holder.tvDescription.setText(knowledgeData.getDescription());
        holder.tvCategory.setText(knowledgeData.getCategory());


//        Glide.with(context).load(hero.getImageUrl()).into(holder.imageView);
        holder.linearLayout.setVisibility(View.GONE);

        //if the position is equals to the item position which is to be expanded
        if (currentPosition == position) {
            //creating an animation
            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

            //toggling visibility
            holder.linearLayout.setVisibility(View.VISIBLE);

            //adding sliding effect
            holder.linearLayout.startAnimation(slideDown);
        }

        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the position of the item to expand it
                currentPosition = position;

                //reloding the list
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return knowledgeDataList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCategory, tvDescription;
        LinearLayout linearLayout;

        HeroViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);


            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
