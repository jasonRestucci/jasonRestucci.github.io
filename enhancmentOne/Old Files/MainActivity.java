package com.example.cs_360_jasonrestucci_option1_projecttwo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<MyModel> myModelInvList;
    CustomAdapter customAdapter;
    public static ArrayList<item> itemArrayList = new ArrayList<item>();
    public static int vistCounter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        vistCounter++;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //populate itemArrayList/memory with database items
        loadFromDBToMemory();

        // link variables to buttons
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonQuit = findViewById(R.id.buttonBack2);
        Button buttonEdit = findViewById(R.id.buttonEdit);

        //prevents cards from duplicated after navigating back to main activity
        if (vistCounter > 1){
            myModelInvList.clear();
        }
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
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        // brings user to edit item screen
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EditItemMenu.class));
            }
        });
    }

    //populates memory from database
    private void loadFromDBToMemory(){
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateItemArrayList();
    }

    // puts items on the screen
    private void displayItems() {
        recyclerView = findViewById(R.id.recyclerViewInventory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        // list for item models (see single_items.xml)
        myModelInvList = new ArrayList<>();

        // adds new item models to screen using items in itemArrayList
        for (int i = 0; i < itemArrayList.size(); i++){
            myModelInvList.add(new MyModel(itemArrayList.get(i).getName(), itemArrayList.get(i).getQty()));
        }

        customAdapter = new CustomAdapter(this, myModelInvList);
        recyclerView.setAdapter(customAdapter);
    }


}