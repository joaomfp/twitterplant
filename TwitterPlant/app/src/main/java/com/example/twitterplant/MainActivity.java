package com.example.twitterplant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> PlantNames;
    private ArrayList<String> PlantIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plant_list();
        Button add_plant = (Button) findViewById(R.id.add_plant);
        add_plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_add = new Intent(MainActivity.this, AddNewPlant.class);
                startActivityForResult(intent_add,1);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        plant_list();
    }

    private void plant_list(){
        PlantNames = new ArrayList<>();
        PlantIds = new ArrayList<>();

        SharedPreferences prefs = getSharedPreferences("shared_prefs" , MODE_PRIVATE);
        String plant_list = prefs.getString("plant_list", "plants");
        Gson gson = new Gson();
        HashMap<String, String> hash_plant_list = new HashMap<String, String>();

        if (plant_list != "plants"){
            Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            hash_plant_list = gson.fromJson(plant_list, type);
            Iterator it = hash_plant_list.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry plant = (Map.Entry)it.next();
                PlantIds.add(plant.getKey().toString());
                PlantNames.add(plant.getValue().toString());
            }
            RecyclerView recyclerView = findViewById(R.id.plant_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new RecyclerViewAdapter(PlantNames,PlantIds,this));
        }
    }
}
