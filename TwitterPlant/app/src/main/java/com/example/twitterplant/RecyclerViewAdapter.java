package com.example.twitterplant;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<String> PlantNames = new ArrayList<>();
    private ArrayList<String> PlantIds = new ArrayList<>();
    private Context Context;

    public RecyclerViewAdapter(ArrayList<String> PlantNames, ArrayList<String> PlantIds, Context Context) {
        this.PlantNames = PlantNames;
        this.PlantIds = PlantIds;
        this.Context = Context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_plant_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.plantName.setText(PlantNames.get(position));
        holder.plantId.setText(PlantIds.get(position));
        holder.plantListItem.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Context, info_activity.class);
                intent.putExtra("plant_name", PlantNames.get(position));
                intent.putExtra("plant_id", PlantIds.get(position));
                Context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return PlantNames.size();}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView plantName;
        TextView plantId;
        RelativeLayout plantListItem;

        public ViewHolder(View itemView) {
            super(itemView);
            plantName = itemView.findViewById(R.id.plant_nome_add);
            plantId = itemView.findViewById(R.id.plant_id_add);
            plantListItem = itemView.findViewById(R.id.plant_list_item);
        }
    }
}