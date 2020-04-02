package com.owcreativ.info.covid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                    return true;
                case R.id.navigationMyCourses:
                    return true;
                case R.id.navigationHome:
                    return true;
                case  R.id.navigationSearch:
                    return true;
                case  R.id.navigationMenu:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);
                    return true;
            }
            return false;
        }
    };

    //DEFINING GETTING PRECAUTIONS DATA TOOLS
    ArrayList<PrecautionData> proSearch = new ArrayList<PrecautionData>();
    RecyclerView rvPrecautions;
    PrecautionAdapter precautionAdapter;

    ArrayList<SignsData> proSearch_2 = new ArrayList<SignsData>();
    RecyclerView rvSymptoms;
    SignsAdapter signsAdapter;


    ArrayList<CategoriesData> proSearch_3 = new ArrayList<CategoriesData>();
    RecyclerView rvCategories;
    CategoriesAdapter categoriesAdapter;

    ArrayList<NewsData> proSearch_4 = new ArrayList<NewsData>();
    RecyclerView rvNews;
    UpdatesAdapter newsAdapter;


    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private String[] urls = new String[] {"https://demonuts.com/Demonuts/SampleImages/W-03.JPG", "https://demonuts.com/Demonuts/SampleImages/W-08.JPG", "https://demonuts.com/Demonuts/SampleImages/W-10.JPG",
            "https://demonuts.com/Demonuts/SampleImages/W-13.JPG", "https://demonuts.com/Demonuts/SampleImages/W-17.JPG", "https://demonuts.com/Demonuts/SampleImages/W-21.JPG"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView tv = (TextView) findViewById(R.id.title);
//        TextView tv_1 = (TextView) findViewById(R.id.categoriesTitle);
        TextView tv_2 = (TextView) findViewById(R.id.precautionsTitle);
        TextView tv_3 = (TextView) findViewById(R.id.symptomsTitle);
//        Typeface face = Typeface.createFromAsset(getAssets(),
//                "font/calibri.ttf");
//        tv.setTypeface(face);


        //or to support all versions use
        Typeface typeface = ResourcesCompat.getFont(MainActivity.this, R.font.wandery);
        tv.setTypeface(typeface);
//        tv_1.setTypeface(typeface);
        tv_2.setTypeface(typeface);
        tv_3.setTypeface(typeface);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setSelectedItemId(R.id.navigationHome);


        rvPrecautions = findViewById(R.id.recycler);
        rvPrecautions.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        rvPrecautions.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
//        rvPrecautions.setLayoutManager(layoutManager);
        getServerData();


        rvSymptoms = findViewById(R.id.recycler2);
        rvSymptoms.setHasFixedSize(true);
        rvSymptoms.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
        getServerData_2();

        rvCategories = findViewById(R.id.recycler_3);
        rvCategories.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        rvCategories.setLayoutManager(layoutManager);
        getServerData_3();


        // ArrayList<PromoData> rvdata = getData();
        rvNews = findViewById(R.id.recycler_0);
        rvNews.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        rvNews.setLayoutManager(layoutManager2);
        getServerData_0();


        // ArrayList<PromoData> rvdata = getData();
//        rvTechSolPoint = findViewById(R.id.recycler);
//        rvTechSolPoint.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
//        rvTechSolPoint.setLayoutManager(layoutManager);
//        getServerData();

        //btnDislike
        findViewById(R.id.btnCheckup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add to Cart
                finish();
                startActivity(new Intent(getApplicationContext(), ScreenStartActivity.class));

            }
        });

        findViewById(R.id.btnPrecautions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add to Cart
                finish();
                startActivity(new Intent(getApplicationContext(), PrecautionsActivity.class));

            }
        });

        findViewById(R.id.btnSymptoms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add to Cart
                finish();
                startActivity(new Intent(getApplicationContext(), SymptomsActivity.class));

            }
        });

        findViewById(R.id.btnFacts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add to Cart
                finish();
                startActivity(new Intent(getApplicationContext(), FactsActivity.class));
            }
        });


    }

    private void getServerData_0() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLs.GET_UPDATES, (JSONObject) null,
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
                                proSearch_4.add(promoData);
                            }
                            newsAdapter = new UpdatesAdapter(getApplicationContext(), proSearch_4);
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


    private void getServerData() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLs.PRECAUTIONS, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = response.getJSONArray("precautions");
                            for (int p=0; p<jsonArray.length(); p++){
                                JSONObject jsonObject = jsonArray.getJSONObject(p);
                                PrecautionData promoData = gson.fromJson(String.valueOf(jsonObject), PrecautionData.class);
                                proSearch.add(promoData);
                            }
                            precautionAdapter = new PrecautionAdapter(getApplicationContext(), proSearch);
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLs.SYMPTOMS, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = response.getJSONArray("symptoms");
                            for (int p=0; p<jsonArray.length(); p++){
                                JSONObject jsonObject = jsonArray.getJSONObject(p);
                                SignsData promoData = gson.fromJson(String.valueOf(jsonObject), SignsData.class);
                                proSearch_2.add(promoData);
                            }
                            signsAdapter = new SignsAdapter(getApplicationContext(), proSearch_2);
                            rvSymptoms.setAdapter(signsAdapter);
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


    private void getServerData_3() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLs.CATEGORIES, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.d("Sahara", response.toString());
                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = response.getJSONArray("categories");
                            for (int p=0; p<jsonArray.length(); p++){
                                JSONObject jsonObject = jsonArray.getJSONObject(p);
                                CategoriesData promoData = gson.fromJson(String.valueOf(jsonObject), CategoriesData.class);
                                proSearch_3.add(promoData);
                            }
                            categoriesAdapter = new CategoriesAdapter(getApplicationContext(), proSearch_3);
                            rvCategories.setAdapter(categoriesAdapter);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_prevention) {
            finish();
            startActivity(new Intent(getApplicationContext(), PrecautionsActivity.class));
        } else if (id == R.id.nave_symptoms) {

            finish();
            startActivity(new Intent(getApplicationContext(), SymptomsActivity.class));

        } else if (id == R.id.nav_precautions) {
            finish();
            startActivity(new Intent(getApplicationContext(), PrecautionsActivity.class));

        } else if (id == R.id.nav_self_test) {

            finish();
            startActivity(new Intent(getApplicationContext(), ScreenStartActivity.class));

        } else if (id == R.id.nav_knowledge) {

            finish();
            startActivity(new Intent(getApplicationContext(), KnowledgeActivity.class));

        } else if (id == R.id.nav_facts) {

            finish();
            startActivity(new Intent(getApplicationContext(), FactsActivity.class));

        }else if (id == R.id.nav_treatments) {

        }else if (id == R.id.nav_dosdonts) {

        }else if (id == R.id.nav_logout) {
            finish();
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
