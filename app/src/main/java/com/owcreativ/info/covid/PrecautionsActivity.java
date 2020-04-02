package com.owcreativ.info.covid;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PrecautionsActivity extends AppCompatActivity {

    //DEFINING GETTING PRECAUTIONS DATA TOOLS
    ArrayList<PrecautionsListData> proSearch = new ArrayList<PrecautionsListData>();
    RecyclerView rvPrecautions;
    PrecautionsListAdapter precautionAdapter;
    SwipeRefreshLayout mySwipeRefreshLayout;


    ArrayList<NewsData> proSearch_2 = new ArrayList<NewsData>();
    RecyclerView rvNews;
    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precautions);

        rvPrecautions = findViewById(R.id.rvPrecautions);
        rvPrecautions.setHasFixedSize(true);
        rvPrecautions.setLayoutManager(new GridLayoutManager(PrecautionsActivity.this,2));

        getServerData();
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add to Cart
                onBackPressed();
            }
        });


        // ArrayList<PromoData> rvdata = getData();
        rvNews = findViewById(R.id.recycler_3);
        rvNews.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        rvNews.setLayoutManager(layoutManager);
        getServerData_2();


        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);



        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getServerData();
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });



    }

    private void doYourUpdate() {
        // TODO implement a refresh
        //setRefreshing(false); // Disables the refresh icon
        getServerData();
    }

    private void getServerData() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLs.GET_PRECAUTIONS, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = response.getJSONArray("precuations");
                            for (int p=0; p<jsonArray.length(); p++){
                                JSONObject jsonObject = jsonArray.getJSONObject(p);
                                PrecautionsListData promoData = gson.fromJson(String.valueOf(jsonObject), PrecautionsListData.class);
                                proSearch.add(promoData);
                            }
                            precautionAdapter = new PrecautionsListAdapter(getApplicationContext(), proSearch);
                            rvPrecautions.setAdapter(precautionAdapter);
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
