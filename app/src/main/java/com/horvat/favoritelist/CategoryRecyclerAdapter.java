package com.horvat.favoritelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    interface  CategoryIsClickedInterface{

        void categoryIsClicked(Category category);
    }

    private ArrayList<Category> categories;
    private CategoryIsClickedInterface categoryIsClickedListener;

    public CategoryRecyclerAdapter(ArrayList<Category> categories, CategoryIsClickedInterface categoryIsClickedListener) {
        this.categories = categories;
        this.categoryIsClickedListener = categoryIsClickedListener;

    }

    // String[] categories = {"Hobbies", "Sports", "Games", "Foods", "Electronic Gadgets", "Countries"};


    //2 new layour resource file :category view holder -> LinearLayout
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.category_viewholder,parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.getTxtCategoryNumber().setText(position +1 + "");
        holder.getTxtCategoryName().setText(categories.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            categoryIsClickedListener.categoryIsClicked(categories.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void addCategory(Category category){
        categories.add(category);

        notifyItemInserted(categories.size()-1);
    }
}
