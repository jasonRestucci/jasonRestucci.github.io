package com.example.cs_360_jasonrestucci_option1_projecttwo;

import static com.example.cs_360_jasonrestucci_option1_projecttwo.MainActivity.itemArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NewItemMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_item_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextQty = findViewById(R.id.editTextQty);
        Button buttonCreateItem = findViewById(R.id.buttonCreateItem);
        Button buttonBack = findViewById(R.id.buttonBack2);

        // takes user text, creates item, adds to database
        buttonCreateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(NewItemMenu.this);
                if (editTextName != null && editTextQty != null){
                    item item = new item(editTextName.getText().toString(), Integer.parseInt(editTextQty.getText().toString()));
                    itemArrayList.add(item);
                    sqLiteManager.addItemToDatabase(item);
                    Toast.makeText(NewItemMenu.this, "Item Added!", Toast.LENGTH_LONG).show();
                    editTextName.setText(null);
                    editTextQty.setText(null);
                }
            }
        });

        // back to main screen
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewItemMenu.this, MainActivity.class));
            }
        });
    }
}