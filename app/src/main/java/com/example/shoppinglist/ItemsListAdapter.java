package com.example.shoppinglist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.db.Category;
import com.example.shoppinglist.db.Items;

import java.util.List;
import java.util.zip.Inflater;

public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.MyHolder> {

    private Context context;
    private List<Items> itemsList;
    private HandleItemsClick clickListener;


    public ItemsListAdapter(Context context, HandleItemsClick clickListener){
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setItemsList(List<Items> itemsList){
        this.itemsList = itemsList;
        notifyDataSetChanged();
    }

    @NonNull

    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row, parent, false);

        return new MyHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ItemsListAdapter.MyHolder holder, int position) {

        holder.tvItemName.setText(itemsList.get(position).itemName.toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.itemClick(itemsList.get(position));
            }
        });

        holder.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.editItem(itemsList.get(position));
            }
        });

        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.removeItem(itemsList.get(position));
            }
        });

        if(itemsList.get(position).completed)
            holder.tvItemName.setPaintFlags(holder.tvItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        else
            holder.tvItemName.setPaintFlags(0);
    }

    @Override
    public int getItemCount() {
        if(itemsList == null || itemsList.size() == 0)
            return 0;
        else
            return itemsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView tvItemName;
        ImageView removeItem;
        ImageView editItem;

        public MyHolder( View itemView) {

            super(itemView);

            tvItemName = itemView.findViewById(R.id.tvCategoryName);
            removeItem = itemView.findViewById(R.id.removeCategory);
            editItem = itemView.findViewById(R.id.editCategory);
        }
    }

    public interface HandleItemsClick{
        void itemClick(Items items);
        void removeItem(Items items);
        void editItem(Items items);
    }
}
