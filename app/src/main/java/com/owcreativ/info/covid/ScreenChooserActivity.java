package com.owcreativ.info.covid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScreenChooserActivity extends AppCompatActivity {

    TextView tvContent;
    String question_id = "0";

    //DEFINING GETTING PRECAUTIONS DATA TOOLS
    ArrayList<AnswersData> proSearch = new ArrayList<AnswersData>();
    RecyclerView rvAnswers;
    AnswersAdapter answersAdapter;

    ArrayList<NewsData> proSearch_2 = new ArrayList<NewsData>();
    RecyclerView rvNews;
    NewAdapter newAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_chooser);


        Bundle b = getIntent().getExtras();
        int q_id = -1; // or other values
        if(b != null) {
            q_id = b.getInt("q_id");
            question_id = Integer.toString(q_id);
        }else {
            question_id = "0";
        }

        tvContent = (findViewById(R.id.tvContent));

        getQuestions(question_id);


        rvAnswers = (RecyclerView) findViewById(R.id.rvAnswer);

        //        rvTechSolPoint = findViewById(R.id.recycler);
        rvAnswers.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rvAnswers.setLayoutManager(layoutManager);


        rvNews = findViewById(R.id.recycler_3);
        rvNews.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        rvNews.setLayoutManager(layoutManager2);
        getServerData_2();


    }

    private void getServerData_2() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLs.NEWS, (JSONObject) null,
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
                            newAdapter = new NewAdapter(getApplicationContext(), proSearch_2);
                            rvNews.setAdapter(newAdapter);
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



    private void getQuestions(String id){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GET_QUESTIONS+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray dataArray = jsonObject.getJSONArray("questions");
                            for (int i = 0; i < dataArray.length(); i++) {

                                JSONObject dataobj = dataArray.getJSONObject(i);

                                String id = dataobj.getString("id");
                                String weight = dataobj.getString("weight");
                                String question = dataobj.getString("question");

                                question_id = id;

                                tvContent.setText("" + question);

                                getAnswerData(id);
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


    private void getAnswerData(String q_id) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLs.GET_ANSWER+q_id, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = response.getJSONArray("answers");
                            for (int p=0; p<jsonArray.length(); p++){
                                JSONObject jsonObject = jsonArray.getJSONObject(p);
                                AnswersData answersData = gson.fromJson(String.valueOf(jsonObject), AnswersData.class);
                                proSearch.add(answersData);
                            }
                            answersAdapter = new AnswersAdapter(getApplicationContext(), proSearch);
                            rvAnswers.setAdapter(answersAdapter);
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
