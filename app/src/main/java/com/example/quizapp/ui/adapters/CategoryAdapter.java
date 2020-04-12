package com.example.quizapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.model.Category;
import com.example.quizapp.ui.CategorySelectionListener;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<Category> categories;
    private final CategorySelectionListener categorySelectionListener;

    public CategoryAdapter(final List<Category> categories, final CategorySelectionListener categorySelectionListener) {
        this.categories = categories;
        this.categorySelectionListener = categorySelectionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_adapter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.categoryName.setText(categories.get(position).categoryName);
        holder.categoryDescription.setText(categories.get(position).categoryDescription);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorySelectionListener.onCategorySelect(categories.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView categoryName;
        TextView categoryDescription;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.view = itemView;
            this.categoryName = this.view.findViewById(R.id.category_adapter_item_category_name);
            this.categoryDescription = this.view.findViewById(R.id.category_adapter_item_category_description);
        }
    }
}
