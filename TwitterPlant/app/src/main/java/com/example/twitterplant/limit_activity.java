package com.example.twitterplant;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class limit_activity extends AppCompatActivity {
    private SeekBar seek_temp;
    private TextView value_temp;
    private SeekBar seek_hum;
    private TextView value_hum;
    private SeekBar seek_lum;
    private TextView value_lum;
    private TextView plant_name;
    private TextView plant_id;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limit_activity);
        seek_bar();

        seek_temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value_temp.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){}
        });

        seek_hum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value_hum.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seek_lum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value_lum.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        queue = Volley.newRequestQueue(this);

        Button change_def = (Button) findViewById(R.id.change_def);
        change_def.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer temp = (int) seek_temp.getProgress();
                Integer hum = (int) seek_hum.getProgress();
                Integer lum = (int) seek_lum.getProgress();

                sendinfo(getIntent().getStringExtra("plant_id"),temp,hum,lum);
                //sendinfo("1",temp,hum,lum); Teste para plant_id=1

                Intent intent_info = new Intent();
                intent_info.putExtra("plant_name",getIntent().getStringExtra("plant_name"));
                intent_info.putExtra("plant_id",getIntent().getStringExtra("plant_id"));
                setResult(Activity.RESULT_OK,intent_info);
                finish();
            }
        });
    }

    private void seek_bar(){
        plant_name = findViewById(R.id.limit_plant_name);
        plant_id = findViewById(R.id.limit_plant_id);
        seek_temp = findViewById(R.id.seek_temp);
        seek_hum = findViewById(R.id.seek_hum);
        seek_lum = findViewById(R.id.seek_lum);
        value_temp = findViewById(R.id.value_temp);
        value_hum = findViewById(R.id.value_hum);
        value_lum = findViewById(R.id.value_lum);
        getinfo();
    }

    public void getinfo(){
            plant_name.setText(getIntent().getStringExtra("plant_name"));
            plant_id.setText(getIntent().getStringExtra("plant_id"));
            Integer temp_limit_value = getIntent().getIntExtra("temp", 0);
            Integer hum_limit_value = getIntent().getIntExtra("hum", 0);
            Integer lum_limit_value = getIntent().getIntExtra("lum", 0);

            value_temp.setText(String.valueOf(temp_limit_value));
            value_hum.setText(String.valueOf(hum_limit_value));
            value_lum.setText(String.valueOf(lum_limit_value));
            seek_temp.setProgress(temp_limit_value);
            seek_hum.setProgress(hum_limit_value);
            seek_lum.setProgress(lum_limit_value);
    }

    public void sendinfo(final String id, final Integer th_temp, final Integer th_hum, final Integer th_lum){
        String url = "http://192.168.10.72:8000/android/threshold?plant_id="+id+"&temperatureT="+th_temp+"&humidityT="+th_hum+"&luminosityT="+th_lum;
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {}
                    },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                }){
        };
        queue.add(request);
    }
}
