package com.horvat.favoritelist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CategoryRecyclerAdapter.CategoryIsClickedInterface {

    private RecyclerView categoryRecyclerView;

    //This is not UI so we can initialize it in MainActivity *** Context is "this" MainActivity
    private CatregoryManager mCatregoryManager = new CatregoryManager(this);

    public static final String CATEGORY_OBJECT_KEY = "CATEGORY_KEY";
    public static final int MAIN_ACTIVITY_REQUEST_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Category> categories = mCatregoryManager.retrieveCategories();

        categoryRecyclerView = findViewById(R.id.category_recyclerview);


        categoryRecyclerView.setAdapter(new CategoryRecyclerAdapter(categories,this ));
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "FAB tapped", Toast.LENGTH_SHORT).show();
                displayCreateCategoryDialog();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayCreateCategoryDialog(){

        String alertTitle = "Enter the name of the category";
        String positiveButtonTitle = "Create";

        //There is no anonymus class and I can set only "this" as Context
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        EditText categoryEditText = new EditText(this);
        categoryEditText.setInputType(InputType.TYPE_CLASS_TEXT);

        alertBuilder.setTitle(alertTitle);
        alertBuilder.setView(categoryEditText);

        // onClick listener for positive Button     NEW DIALOGINTERFACE.ONCLICK LISTENER IS ANONYMUS CLASS
        alertBuilder.setPositiveButton(positiveButtonTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //1. New Category 2. CategooryManager needs an object to save and that is category
                Category category = new Category(categoryEditText.getText().toString(), new ArrayList<String>());
                mCatregoryManager.saveCategory(category);

                CategoryRecyclerAdapter categoryRecyclerAdapter =(CategoryRecyclerAdapter) categoryRecyclerView.getAdapter();
                categoryRecyclerAdapter.addCategory(category);

                dialog.dismiss();
                displayCategoryItems(category);
            }
        });


        alertBuilder.create().show();

    }


    private void displayCategoryItems(Category category){
        Intent categoryItemsIntent = new Intent(this,CategoryItemsActivity.class);
        categoryItemsIntent.putExtra(CATEGORY_OBJECT_KEY, category);

        startActivityForResult(categoryItemsIntent, MAIN_ACTIVITY_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MAIN_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if(data != null){
                mCatregoryManager.saveCategory((Category) data.getSerializableExtra(CATEGORY_OBJECT_KEY));
                updateCategories();
            }
        }
    }

    private void updateCategories() {

       ArrayList<Category> categories = mCatregoryManager.retrieveCategories();
       categoryRecyclerView.setAdapter(new CategoryRecyclerAdapter(categories, this));


    }

    @Override
    public void categoryIsClicked(Category category) {

        displayCategoryItems(category);

    }
}