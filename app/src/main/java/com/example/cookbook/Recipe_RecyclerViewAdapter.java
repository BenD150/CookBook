package com.example.cookbook;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Recipe_RecyclerViewAdapter extends RecyclerView.Adapter<Recipe_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<RecipeModel> recipeModels;

    // Need the data and the context
    public Recipe_RecyclerViewAdapter(Context context, ArrayList<RecipeModel> recipeModels) {
        this.context = context;
        this.recipeModels = recipeModels;
    }



    @NonNull
    @Override
    public Recipe_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout and give view to each of our rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new Recipe_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Recipe_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assign values to the views we created in the recycler_view_row layout file
        holder.tvName.setText(recipeModels.get(position).getRecipeName());
        holder.tvPrep.setText(recipeModels.get(position).getPrepTime());
        holder.tvCook.setText(recipeModels.get(position).getCookTime());
        holder.imageView.setImageResource(recipeModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        // Get total number of items
        return recipeModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvName, tvPrep, tvCook;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recipeImage);
            tvName = itemView.findViewById(R.id.boldRName);
            tvPrep = itemView.findViewById(R.id.rPrepTime);
            tvCook = itemView.findViewById(R.id.rCookTime);
        }
    }



}
