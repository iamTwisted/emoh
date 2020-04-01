package com.owcreativ.info.covid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ScreenStartActivity extends AppCompatActivity {

    final String title =  "starter";
    TextView tvContent;

    ArrayList<NewsData> proSearch_2 = new ArrayList<NewsData>();
    RecyclerView rvNews;
    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_start);

        getTerms();
        tvContent = (findViewById(R.id.tvContent));


        rvNews = findViewById(R.id.recycler_3);
        rvNews.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        rvNews.setLayoutManager(layoutManager);
        getServerData_2();


        //btnDislike
        findViewById(R.id.btnDecline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add to Cart
                new SweetAlertDialog(ScreenStartActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("You won't be able to continue with the survey")
                        .setConfirmText("Ok!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                onBackPressed();
                            }
                        })
                        .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });


        findViewById(R.id.btnAccept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add to Cart

                finish();
                startActivity(new Intent(getApplicationContext(), ScreenChooserActivity.class));

            }
        });

    }

    private void getTerms(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GET_TERMS+title,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray dataArray = jsonObject.getJSONArray("terms");
                            for (int i = 0; i < dataArray.length(); i++) {

                                JSONObject dataobj = dataArray.getJSONObject(i);

                                String id = dataobj.getString("id");
                                String title = dataobj.getString("title");
                                String content = dataobj.getString("content");


                                tvContent.setText("" + content);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }

    private void getServerData_2() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLs.GET_NEWS, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = response.getJSONArray("news");
                            for (int p=0; p<jsonArray.length(); p++){
                                JSONObject jsonObject = jsonArray.getJSONObject(p);
                                NewsData promoData = gson.fromJson(String.valueOf(jsonObject), NewsData.class);
                                proSearch_2.add(promoData);
                            }
                            newsAdapter = new NewsAdapter(getApplicationContext(), proSearch_2);
                            rvNews.setAdapter(newsAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }



    public void onBackPressed() {
        // close search view on back button pressed

        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


}
