package com.example.cs_360_jasonrestucci_option1_projecttwo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs_360_jasonrestucci_option1_projecttwo.data.LoginRepository;
import com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Author: Jason Restucci
 * Date Last Modified: 4/1/2025
 * Description: Main screen, displays items in DB and hosts the access to other menus
 */

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    public static ArrayList<item> itemArrayList = new ArrayList<item>();
    public static int visitCounter;
    String searchMethod = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        visitCounter++;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //prevents cards from duplicated after navigating back to main activity
        if (visitCounter > 1){
            itemArrayList.clear();
        }
        
        //populate itemArrayList/memory with database items
        loadFromDBToMemory();

        // link variables to buttons
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonQuit = findViewById(R.id.buttonBack2);
        SearchView searchView = findViewById(R.id.searchBar);
        ToggleButton nameToggle = findViewById(R.id.toggleButtonSearchName);
        ToggleButton categoryToggle = findViewById(R.id.toggleButtonSearchCategory);

        displayItems();

        // brings user to new item screen
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NewItemMenu.class));
            }
        });

        // brings user to login screen
        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LoginRepository.logout();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        // Searches for item by name or category
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<item> filteredItemModelList = new ArrayList<item>();

                for (item item: itemArrayList){
                    if (item.getName().contains(s.toUpperCase()) && searchMethod.equals("name")){
                        filteredItemModelList.add(item);
                    } else if (item.getCategory().contains(s.toUpperCase()) && searchMethod.equals("category")) {
                        filteredItemModelList.add(item);
                    }
                }
                customAdapter = new CustomAdapter(getApplicationContext(), filteredItemModelList);
                recyclerView.setAdapter(customAdapter);

                return false;
            }
        });

        //sets search to name
        nameToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                categoryToggle.setChecked(false);
                nameToggle.setBackgroundColor(Color.WHITE);
                nameToggle.setTextColor(Color.BLACK);
                categoryToggle.setBackgroundColor(Color.DKGRAY);
                categoryToggle.setTextColor(Color.WHITE);
                searchMethod = "name";
            }
        });

        // sets search to category
        categoryToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                nameToggle.setChecked(false);
                categoryToggle.setBackgroundColor(Color.WHITE);
                categoryToggle.setTextColor(Color.BLACK);
                nameToggle.setBackgroundColor(Color.DKGRAY);
                nameToggle.setTextColor(Color.WHITE);
                searchMethod = "category";
            }
        });
    }

    //populates memory from database
    private void loadFromDBToMemory(){
        try (SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this)) {
            sqLiteManager.populateItemArrayList();
        }
    }

    // puts items on the screen
    private void displayItems() {
        recyclerView = findViewById(R.id.recyclerViewInventory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        if(!itemArrayList.isEmpty()) {
            itemArrayList.sort(Comparator.comparing(item::getName));
        }

        customAdapter = new CustomAdapter(this, itemArrayList);
        recyclerView.setAdapter(customAdapter);
    }
}