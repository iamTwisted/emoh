package com.owcreativ.info.covid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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

public class SelfActivity extends AppCompatActivity {

    TextView tvContent,tvResults;
    String question_id = "";
    String status = "";

    //DEFINING GETTING PRECAUTIONS DATA TOOLS
    ArrayList<AnswersData> proSearch = new ArrayList<AnswersData>();
    RecyclerView rvAnswers;
    AnswersAdapter answersAdapter;
    LinearLayout contentPanel,resultsPanel,fixedPanel,buttonPanel;

    //DEFINING GETTING PRECAUTIONS DATA TOOLS
    ArrayList<PromoData> proSearch2 = new ArrayList<PromoData>();
    RecyclerView rvSymptoms;
    PromoAdapter symptomsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);


        contentPanel = (LinearLayout) findViewById(R.id.contentPanel);
        fixedPanel = (LinearLayout) findViewById(R.id.fixedPanel);
        buttonPanel = (LinearLayout) findViewById(R.id.buttonPanel);

        Bundle b = getIntent().getExtras();
        int q_id = -1; // or other values
        int s_id = -1; // or other values
        if(b != null) {
            q_id = b.getInt("q_id");
            s_id = b.getInt("status");
            question_id = Integer.toString(q_id);
            status = Integer.toString(s_id);

        }else {
            question_id = "0";
        }

        getQuestions(question_id,status);

        tvContent = (findViewById(R.id.tvContent));

        rvAnswers = (RecyclerView) findViewById(R.id.rvAnswer);


        rvAnswers.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rvAnswers.setLayoutManager(layoutManager);

        rvSymptoms = findViewById(R.id.rvSymptoms);
        rvSymptoms.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rvSymptoms.setLayoutManager(layoutManager2);
        getServerData_3();
    }


    private void getServerData_3() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLs.SYMPTOMS, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.d("Sahara", response.toString());
                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = response.getJSONArray("symptoms");
                            for (int p=0; p<jsonArray.length(); p++){
                                JSONObject jsonObject = jsonArray.getJSONObject(p);
                                PromoData promoData = gson.fromJson(String.valueOf(jsonObject), PromoData.class);
                                proSearch2.add(promoData);
                            }
                            symptomsAdapter = new PromoAdapter(getApplicationContext(), proSearch2);
                            rvSymptoms.setAdapter(symptomsAdapter);
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

    private void getQuestions(String qid, String qstatus){

        Log.d("QuestionID", URLs.GET_QS+qstatus+"&id="+qid);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GET_QS+qstatus+"&id="+qid+"&people_id=1",
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

                                if( id.equalsIgnoreCase("0")){

                                    contentPanel.setVisibility(View.VISIBLE);
                                    fixedPanel.setVisibility(View.VISIBLE);
                                    buttonPanel.setVisibility(View.VISIBLE);

                                }
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
