package com.owcreativ.info.covid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MythsDetailsActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    PreAdapter adapter;
    List<PreData> preDataList;

    TextView title,tvTitle,tvDescription;
    ImageView thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myths_details);

        title = findViewById(R.id.title);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        thumbnail = findViewById(R.id.thumbnail);

        Bundle b = getIntent().getExtras();
        int id = -1; // or other values
        if(b != null)
            id = b.getInt("id");

        final String precautionsID = Integer.toString(id);



        preDataList = new ArrayList<>();


        getPrecautionsInfo(precautionsID);

    }


    private void getPrecautionsInfo(String id){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GET_MYTHS_DETAILS+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Sitfombe", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            JSONArray dataArray = jsonObject.getJSONArray("facts");
                            for (int i = 0; i < dataArray.length(); i++) {

                                JSONObject dataobj = dataArray.getJSONObject(i);


                                String title = dataobj.getString("title");
                                String description = dataobj.getString("description");
                                String image = dataobj.getString("photo");




                                tvTitle.setText("" + title);
                                tvDescription.setText("" + description);



                                Glide.with(MythsDetailsActivity.this)
                                        .load(image)
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(thumbnail);


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

    public void onBackPressed() {
        // close search view on back button pressed

        finish();
        startActivity(new Intent(getApplicationContext(), FactsActivity.class));
    }
}