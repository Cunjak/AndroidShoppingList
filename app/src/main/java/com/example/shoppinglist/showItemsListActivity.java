package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppinglist.db.Category;
import com.example.shoppinglist.db.Items;
import com.example.shoppinglist.viewmodel.MainActivityViewModel;
import com.example.shoppinglist.viewmodel.ShowItemsListViewModel;

import java.util.List;

public class showItemsListActivity extends AppCompatActivity implements ItemsListAdapter.HandleItemsClick {

    private int categoryId;
    private ItemsListAdapter itemsListAdapter;
    private ShowItemsListViewModel viewModel;
    private RecyclerView recyclerView;
    private TextView noResultTextView;
    private EditText addNewItemInput;
    private Items itemToUpdate = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items_list);

        categoryId = getIntent().getIntExtra("categoryId", 0);
        String categoryName = getIntent().getStringExtra("categoryName");

        getSupportActionBar().setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        addNewItemInput = findViewById(R.id.addNewItemInput);
        noResultTextView = findViewById(R.id.noResultTextView);
        ImageView saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = addNewItemInput.getText().toString();
                if(TextUtils.isEmpty(itemName)) {
                    Toast.makeText(showItemsListActivity.this, "Must enter item name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(itemToUpdate == null)
                    saveNewItem(itemName);
                else
                    updateNewItem(itemName);

            }
        });

        initRecyclerView();
        initViewModel();

    }

    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(ShowItemsListViewModel.class);
        viewModel.getItemsListObserver().observe(this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> items) {
                if(items == null){
                    noResultTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                else {
                    itemsListAdapter.setItemsList(items);
                    noResultTextView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initRecyclerView(){

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsListAdapter = new ItemsListAdapter(this, this);
        recyclerView.setAdapter(itemsListAdapter);
    }
    private void saveNewItem(String itemName){
        Items item = new Items();
        item.categoryId = categoryId;
        item.itemName = itemName;
        viewModel.insertItem(item);
        addNewItemInput.setText("");
    }

    @Override
    public void itemClick(Items item) {
        if(item.completed)
            item.completed = false;
        else
            item.completed = true;
        viewModel.updateItem(item);
    }

    @Override
    public void removeItem(Items item) {
        viewModel.deleteItem(item);
    }

    @Override
    public void editItem(Items item) {
        itemToUpdate = item;
        addNewItemInput.setText(item.itemName);
    }
    private void updateNewItem(String newItemName){
        itemToUpdate.itemName = newItemName;
        viewModel.updateItem(itemToUpdate);
        addNewItemInput.setText("");
        itemToUpdate = null;
    }
}