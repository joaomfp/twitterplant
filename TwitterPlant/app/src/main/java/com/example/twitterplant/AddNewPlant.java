package com.example.twitterplant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;

public class AddNewPlant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_plant);

        Button add_plant_btn = (Button) findViewById(R.id.add_plant_btn);
        add_plant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText PlantIds = findViewById(R.id.plant_id);
                EditText PlantNames = findViewById(R.id.plant_name_c);

                SharedPreferences prefs = getSharedPreferences("shared_prefs",MODE_PRIVATE);
                String plant_list = prefs.getString("plant_list","plants");
                Gson gson = new Gson();
                HashMap<String, String> hash_plant_list = new HashMap<String, String>();
                plant_list="plants";
                if (plant_list != "plants"){
                    java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
                    hash_plant_list = gson.fromJson(plant_list, type);

                }

                hash_plant_list.put(PlantIds.getText().toString(),PlantNames.getText().toString());
                String hashMapString = gson.toJson(hash_plant_list);
                prefs.edit().putString("plant_list", hashMapString).apply();
                Intent intent_add = new Intent();
                setResult(Activity.RESULT_OK,intent_add);
                finish();
                }
            }
        );
    }
}
