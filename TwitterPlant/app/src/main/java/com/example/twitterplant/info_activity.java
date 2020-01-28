package com.example.twitterplant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class info_activity extends AppCompatActivity {
    int temp = 0;
    int hum = 0;
    int lum = 0;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_activity);

        queue = Volley.newRequestQueue(this);
        getInfo();
    }


    protected void onRestart() {
        super.onRestart();
        queue = Volley.newRequestQueue(this);
        getInfo();
    }

    public void getInfo() {
            final String str_plant_name = getIntent().getStringExtra("plant_name");
            final String str_plant_id = getIntent().getStringExtra("plant_id");

            TextView plant_name = findViewById(R.id.plant_name_info);
            TextView plant_id = findViewById(R.id.plant_id_info);
            plant_name.setText(str_plant_name);
            plant_id.setText(str_plant_id);

            getdata(getIntent().getStringExtra("plant_id"));
            //getdata("1"); TESTE para planta_id=1

            Button control_btn = (Button) findViewById(R.id.control_btn);
            control_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_controls = new Intent(info_activity.this, controls_activity.class);
                    intent_controls.putExtra("plant_id_c", str_plant_id);
                    intent_controls.putExtra("plant_name_c", str_plant_name);
                    startActivityForResult(intent_controls, 1);
                }
            });

            Button limit_btn = (Button) findViewById(R.id.limit_btn);
            limit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_limit = new Intent(info_activity.this, limit_activity.class);
                    intent_limit.putExtra("temp", temp);
                    intent_limit.putExtra("hum", hum);
                    intent_limit.putExtra("lum", lum);
                    intent_limit.putExtra("plant_name", str_plant_name);
                    intent_limit.putExtra("plant_id",str_plant_id);
                    startActivityForResult(intent_limit, 1);
                }
            });

            ImageView refresh = (ImageView) findViewById(R.id.refresh);
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_refresh = getIntent();
                    finish();
                    startActivity(intent_refresh);
                }
            });
    }

    public void sendInfo(String Plant_name,
                         String Plant_id,
                         int TempVal,int HumVal ,int LumVal,
                         int TempLimit, int HumLimit, int LumLimit) {

        TextView temp_value = findViewById(R.id.temp_value);
        TextView hum_value = findViewById(R.id.hum_value);
        TextView lum_value = findViewById(R.id.lum_value);

        TextView temp_limit_value = findViewById(R.id.temp_limit_value);
        TextView hum_limit_value = findViewById(R.id.hum_limit_value);
        TextView lum_limit_value = findViewById(R.id.lum_limit_value);

        temp_value.setText(String.valueOf(TempVal));
        temp_limit_value.setText(String.valueOf(TempLimit));
        hum_value.setText(String.valueOf(HumVal));
        hum_limit_value.setText(String.valueOf(HumLimit));
        lum_value.setText(String.valueOf(LumVal));
        lum_limit_value.setText(String.valueOf(LumLimit));
    }

    public void getdata(String id) {
        String url = "http://192.168.10.72:8000/android/status?plant_id="+id;
        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {
                        try {
                            JSONObject response = responseArray.getJSONObject(0);
                                try {
                                    int val_temp = Integer.parseInt(response.getString("temperature"));
                                    int val_hum = Integer.parseInt(response.getString("humidity"));
                                    int val_lum = Integer.parseInt(response.getString("luminosity"));
                                    int limit_temp = Integer.parseInt(response.getString("temperatureT"));
                                    int limit_hum = Integer.parseInt(response.getString("humidityT"));
                                    int limit_lum = Integer.parseInt(response.getString("luminosityT"));

                                    sendInfo(response.getString("name"), response.getString("plant_id"), val_temp, val_hum, val_lum, limit_temp, limit_hum, limit_lum);
                                    temp = limit_temp;
                                    hum = limit_hum;
                                    lum = limit_lum;

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        sendInfo("","", 0, 0, 0, 0, 0, 0);
                    }
                });
        queue.add(getRequest);
    }
}