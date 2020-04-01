package com.owcreativ.info.covid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.RvViewHolder>{
    Context context;
    Dialog myDialog;
    String uniqueID = UUID.randomUUID().toString();


    ArrayList<AnswersData> answersData;
    public AnswersAdapter(Context context, ArrayList<AnswersData> answersData){
        this.context = context;
        this.answersData = answersData;

    }
    View view;
    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());



        view = inflater.from(parent.getContext()).inflate(R.layout.answer_list,parent,false);
        RvViewHolder rvViewHolder = new RvViewHolder(view);
        return rvViewHolder;
    }
    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        final AnswersData answersData = this.answersData.get(position);
        holder.tvAnswer.setText(answersData.getAnswer());


        //get first letter of each String item
        String firstLetter = String.valueOf(answersData.getAnswer().charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px




        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(answersData.getAnswer().equalsIgnoreCase("No")){

                final Context context2 = view.getContext();
                  new SweetAlertDialog(context2, SweetAlertDialog.BUTTON_POSITIVE)
                            .setTitleText("Sounds like you are feeling ok!")
                            .setContentText("This Coronavirus Self-Checker system is for those who may be sick on been in contact with someone who is sick. ")
                            .setConfirmText("Ok!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
//                                    onBackPressed();
                                    Intent intent = new Intent(context, MainActivity.class);
                                    context2.startActivity(intent);
                                   ((Activity)context2).finish();

                                }
                            })
                            .show();

                }else if(answersData.getAnswer().equalsIgnoreCase("Yes")){

                Context context = view.getContext();
                Intent intent = new Intent(context, ScreenChooserActivity.class);
                Bundle b = new Bundle();
                b.putInt("q_id", answersData.getId()); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                context.startActivity(intent);

                }else if(answersData.getAnswer().equalsIgnoreCase("Myself")){


                    Context context = view.getContext();
                    Intent intent = new Intent(context, SelfActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("q_id", answersData.getId()); //Your Question ID
                    b.putInt("status", 1); //Your id
                    b.putString("code", uniqueID );
                    intent.putExtras(b); //Put your id to your next Intent
                    context.startActivity(intent);

                }else if(answersData.getAnswer().equalsIgnoreCase("Someone Else")){

                    Context context = view.getContext();
                    Intent intent = new Intent(context, OtherActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("q_id", answersData.getId()); //Your id
                    b.putInt("status", 2); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    context.startActivity(intent);

                }else{


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ADD_SCORES,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {


                                    try {
                                        //converting response to json object
                                        JSONObject obj = new JSONObject(response);

                                        Log.d("Answers", response);
                                        Log.d("Status", response);
                                        Log.d("Still", obj.getString("status"));


                                        //if no error in response
                                        if (!obj.getBoolean("error")) {


                                            if(obj.getString("status").equalsIgnoreCase("1")){

                                                Intent intent = new Intent(context, SelfActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                                Bundle b = new Bundle();
                                                b.putInt("q_id", obj.getInt("qid")); //Your id
                                                b.putInt("status", 1); //Your id
                                                intent.putExtras(b); //Put your id to your next Intent
                                                context.startActivity(intent);
                                            }else if(obj.getString("status").equalsIgnoreCase("2")){

                                                Intent intent = new Intent(context, OtherActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                                Bundle b = new Bundle();
                                                b.putInt("q_id", obj.getInt("qid")); //Your id
                                                b.putInt("status", 2); //Your id
                                                intent.putExtras(b); //Put your id to your next Intent
                                                context.startActivity(intent);

                                            }






                                        } else {


                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("qid", String.valueOf(answersData.getId()));
                            params.put("aweight", answersData.getWeight());
                            params.put("qweight", answersData.getQweight());
                            params.put("status", answersData.getStatus());
                            params.put("people_id", "1");
                            params.put("code", uniqueID);
//                            params.put("answer_id", tvMealID.getText().toString());
                            return params;
                        }
                    };

                    VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

                }






            }
        });
    }


    @Override
    public int getItemCount() {
        return answersData.size();
    }
    public class RvViewHolder extends RecyclerView.ViewHolder {
        TextView tvAnswer;
        RelativeLayout llItem;
        public RvViewHolder(View itemView) {
            super(itemView);
            tvAnswer = itemView.findViewById(R.id.tvAnswer);
            llItem = itemView.findViewById(R.id.ll_item);
        }
    }
}
