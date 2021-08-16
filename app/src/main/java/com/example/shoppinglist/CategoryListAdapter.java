package com.example.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.db.Category;

import java.util.List;
import java.util.zip.Inflater;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyHolder> {

    private Context context;
    private List<Category> categoryList;
    private HandleCategoryClick clickListener;
    private List<View> views;

    public CategoryListAdapter(Context context, HandleCategoryClick clickListener){
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setCategoryList(List<Category> categoryList){
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull

    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.MyHolder holder, int position) {

        holder.tvCategoryName.setText(categoryList.get(position).categoryName.toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.itemClick(categoryList.get(position));
            }
        });

        holder.editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.editItem(categoryList.get(position));
            }
        });

        holder.removeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.removeItem(categoryList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(categoryList == null || categoryList.size() == 0)
        return 0;
        else
            return categoryList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView tvCategoryName;
        ImageView removeCategory;
        ImageView editCategory;

        public MyHolder( View itemView) {

            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            removeCategory = itemView.findViewById(R.id.removeCategory);
            editCategory = itemView.findViewById(R.id.editCategory);
        }
    }

    public interface HandleCategoryClick{
        void itemClick(Category category);
        void removeItem(Category category);
        void editItem(Category category);
    }
}
