package com.example.shoppinglist.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppinglist.MainActivity;
import com.example.shoppinglist.db.AppDatabase;
import com.example.shoppinglist.db.Category;
import com.example.shoppinglist.db.Items;

import java.util.List;

public class ShowItemsListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Items>> listOfItems;
    private AppDatabase appDatabase;

    public ShowItemsListViewModel( Application application) {
        super(application);

        listOfItems = new MutableLiveData<List<Items>>();
        appDatabase = AppDatabase.getDBinstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Items>> getItemsListObserver(){
        return  listOfItems;
    }

    public void getAllItemsList(int categoryId){
        List<Items> itemsList = appDatabase.shoppingListDao().getAllItemsList(categoryId);
        if(itemsList.size() > 0){
            listOfItems.postValue(itemsList);
        }
        else
            listOfItems.postValue(null);
    }

    public void insertItem(Items item){
        appDatabase.shoppingListDao().insertItems(item);
        getAllItemsList(item.categoryId);
    }

    public void updateItem(Items item){
        appDatabase.shoppingListDao().updateItems(item);
        getAllItemsList(item.categoryId);
    }

    public void deleteItem(Items item){
        appDatabase.shoppingListDao().deleteItems(item);
        getAllItemsList(item.categoryId);
    }
}
