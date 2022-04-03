package com.example.cookbook;

import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Recipe_RecyclerViewAdapter extends RecyclerView.Adapter<Recipe_RecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<RecipeModel> recipeModels;


    // Need the data and the context
    public Recipe_RecyclerViewAdapter(Context context, ArrayList<RecipeModel> recipeModels, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.recipeModels = recipeModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }



    @NonNull
    @Override
    public Recipe_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Recipe_RecyclerViewAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false), recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Recipe_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assign values to the views we created in the recycler_view_row layout file
        holder.tvName.setText(recipeModels.get(position).getRecipeName());
        System.out.println("Holder binded viewholder recipename as " + recipeModels.get(position).getRecipeName());
        holder.tvPrep.setText(recipeModels.get(position).getPrepTime());
        holder.tvCook.setText(recipeModels.get(position).getCookTime());
        holder.imageView.setImageResource(recipeModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        // Get total number of items
        System.out.println("Item count is returning " + recipeModels.size());
        return recipeModels.size();
    }

    public void filterList(ArrayList<RecipeModel> filteredList) {
        recipeModels = filteredList;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvName, tvPrep, tvCook;


        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recipeImage);
            tvName = itemView.findViewById(R.id.boldRName);
            tvPrep = itemView.findViewById(R.id.rPrepTime);
            tvCook = itemView.findViewById(R.id.rCookTime);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onRecipeClick(position);
                        }
                    }
                }
            });
        }
    }
}