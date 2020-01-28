package com.example.twitterplant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class controls_activity extends AppCompatActivity {

    private Switch switch_temp;
    private Switch switch_hum;
    private Switch switch_lum;
    private TextView plant_id;
    private TextView plant_name;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls_activity);

        final String str_plant_name = getIntent().getStringExtra("plant_name_c");
        final String str_plant_id = getIntent().getStringExtra("plant_id_c");

        TextView plant_name = findViewById(R.id.plant_name_c);
        TextView plant_id = findViewById(R.id.plant_id_c);

        plant_name.setText(str_plant_name);
        plant_id.setText(str_plant_id);

        switch_temp = findViewById(R.id.switch_temp);
        switch_hum = findViewById(R.id.switch_hum);
        switch_lum = findViewById(R.id.switch_lum);

        queue = Volley.newRequestQueue(this);
        //getinfo("1"); Teste para plant_id=1
        getinfo(getIntent().getStringExtra("plant_id"));

        Button change_btn = (Button) findViewById(R.id.change_btn);
        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp_bd;
                int hum_bd;
                int lum_bd;

                if(switch_temp.isChecked()){
                    temp_bd=1; }
                else temp_bd=0;

                if(switch_hum.isChecked()){
                    hum_bd=1; }
                else hum_bd=0;

                if(switch_lum.isChecked()){
                    lum_bd=1;}
                else lum_bd=0;

                //senddata("1",temp_bd,hum_bd,lum_bd); Teste para plant_id=1
                senddata(getIntent().getStringExtra("plant_id"),temp_bd,hum_bd,lum_bd);

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    public void getinfo(String id) {
        String url = "http://192.168.10.72:8000/android/actuator?plant_id="+id;
        JsonArrayRequest getRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                try {
                    JSONObject response = responseArray.getJSONObject(0);
                    int temp_bool = Integer.parseInt(response.getString("Tactuator"));
                    int hum_bool = Integer.parseInt(response.getString("Hactuator"));
                    int lum_bool = Integer.parseInt(response.getString("Lactuator"));

                    if(temp_bool==0){
                        switch_temp.setChecked(false); }
                    else
                        switch_temp.setChecked(true);

                    if(hum_bool==0){
                        switch_hum.setChecked(false); }
                    else
                        switch_hum.setChecked(true);

                    if(lum_bool==0){
                        switch_lum.setChecked(false); }
                    else
                        switch_lum.setChecked(true);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(getRequest);
    }

    public void senddata(String id, final Integer Temperatura, Integer Humidade, Integer Luminosidade){
        final String plant_id = id;
        String url ="http://192.168.10.72:8000/android/actuator?plant_id="+id+"&Tactuator="+Temperatura+"&Hactuator="+Humidade+"&Lactuator="+Luminosidade;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(request);
    }
}