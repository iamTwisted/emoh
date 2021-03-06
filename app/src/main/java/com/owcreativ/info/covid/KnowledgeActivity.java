package com.owcreativ.info.covid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    KnowledgeAdapter adapter;
    List<KnowledgeData> knowledgeDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);

        recyclerView = (RecyclerView) findViewById(R.id.rvKnowledge);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        knowledgeDataList = new ArrayList<>();

        loadKnowledgebaseInfo();

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add to Cart
               onBackPressed();
            }
        });
    }

    private void loadKnowledgebaseInfo() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GET_KNOWLDGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            Log.d("Aws",response);

                            JSONArray jsonArray = new JSONArray(response);


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                Log.d("Knowledge",obj.getString("description"));

                                KnowledgeData knowledgeData = new KnowledgeData(
                                        obj.getString("title"),
                                        obj.getString("description"),
                                        obj.getString("category")
                                );

                                knowledgeDataList.add(knowledgeData);
                            }

                            adapter = new KnowledgeAdapter(knowledgeDataList, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onBackPressed() {
        // close search view on back button pressed

        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
