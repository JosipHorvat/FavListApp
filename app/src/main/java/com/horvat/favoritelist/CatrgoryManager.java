package com.horvat.favoritelist;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

public class CatrgoryManager {

    private Context mContext;

    public CatrgoryManager(Context context){
        mContext = context;
    }

    // Function that will save data to SharedPreferences
    public void saveCategory(Category category){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // set is just like array but set values must be unique.

        //Array list need to be converted
        //category.getItems is getter of ArrayList of items.
        HashSet itemsHashSet = new HashSet(Arrays.asList(category.getItems()));
        editor.putStringSet(category.getName(), itemsHashSet );

        editor.apply();
    }

    //retriving data
    //vraca sve kategorije u shared preferences bazi
    public ArrayList<Category> retrieveCategories(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        //Pristup svim podacima koji su spremljeni u sharedPreferences
        // Kljuc je String a "anytype" data je ?
        Map<String, ?> data = sharedPreferences.getAll();

        ArrayList<Category> categories = new ArrayList<>();

        for (Map.Entry<String ,?> entry:  data.entrySet()){
            // Sada treba konvertirati obrnuto: hash set u arrayList
            // U save metodi sam konvertirao array listu u hashSet
            Category category = new Category(entry.getKey(), new ArrayList<String>((HashSet)entry.getValue()));

            //ma kraju dodajem unutar categories novu category
            categories.add(category);
        }

        return categories;

    }
    // This is syntax on how to use shared preference to store data (in this case i am storing data of Category)
}
