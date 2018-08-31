package com.sailesh.sparks.earthquake.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sailesh.sparks.earthquake.R;
import com.sailesh.sparks.earthquake.adapters.EarthQuakeAdapter;
import com.sailesh.sparks.earthquake.model.EarthQuakeFeatures;
import com.sailesh.sparks.earthquake.util.Constants;
import com.sailesh.sparks.earthquake.util.VolleyTon;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private EarthQuakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.earth_recycle);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new EarthQuakeAdapter(this,new ArrayList<EarthQuakeFeatures>());

        mRecyclerView.setAdapter(mAdapter);

        fetchEarthQuakeData();
    }

    private void fetchEarthQuakeData() {
        String url = Constants.BASE_URL;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson= new Gson();
                JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                if (jsonObject.has(Constants.FEATURES)) {
                    JsonArray featuresArray = jsonObject.getAsJsonArray(Constants.FEATURES);
                    Type collectionType = new TypeToken<List<EarthQuakeFeatures>>() {
                    }.getType();

                    List<EarthQuakeFeatures> featuresList = gson.fromJson(featuresArray,
                            collectionType);
                    mAdapter.updateQuakeData(featuresList);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getNetworkTimeMs();
            }
        });
        VolleyTon.getInstance().addToRequestQueue(stringRequest);
    }
}
