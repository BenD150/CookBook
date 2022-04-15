package com.example.cookbook;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Locale;

public class Recipe_RecyclerViewAdapter extends RecyclerView.Adapter<Recipe_RecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
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
        context = parent.getContext();
        return new Recipe_RecyclerViewAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false), recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Recipe_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assign values to the views we created in the recycler_view_row layout file
        holder.tvName.setText(recipeModels.get(position).getRecipeName());
        String displayLanguage = Locale.getDefault().getDisplayLanguage();
        String newPrepTime = "";
        String newCookTime = "";
        if(displayLanguage.equals("中文")){
            newPrepTime = "准备时间: " + recipeModels.get(position).getPrepTime() + " 分钟.";
            newCookTime = "烹饪时间: " + recipeModels.get(position).getCookTime() + " 分钟.";
        } else {
            newPrepTime = "Prep Time: " + recipeModels.get(position).getPrepTime() + " min.";
            newCookTime = "Cook Time: " + recipeModels.get(position).getCookTime() + " min.";
        }
        holder.tvPrep.setText(newPrepTime);
        holder.tvCook.setText(newCookTime);
        Glide.with(context).load(recipeModels.get(position).getImage()).override(150, 150).into(holder.imageView);
        //holder.imageView.setImageResource(recipeModels.get(position).getImage());
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

    public RecipeModel getItem(int position) {
        return recipeModels.get(position);
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




            itemView.setOnClickListener(view -> {
                if (recyclerViewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onRecipeClick(position);
                    }
                }
            });
        }
    }
}