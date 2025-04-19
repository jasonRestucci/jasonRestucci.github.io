package com.example.cs_360_jasonrestucci_option1_projecttwo;

import static com.example.cs_360_jasonrestucci_option1_projecttwo.MainActivity.itemArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Author: Jason Restucci
 * Date Last Modified: 3/29/25
 * Description: Menu for editing items
 */

public class EditItemMenu extends AppCompatActivity {
    item currentItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_item_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // connect variables to UI elements
        EditText editTextNewName = findViewById(R.id.editTextNewName);
        EditText editTextNewQty = findViewById(R.id.editTextNewQty);
        EditText editTextNewCategory = findViewById(R.id.editTextNewCategory);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        Button buttonBack = findViewById(R.id.buttonBack3);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        TextView currentNameTitle = findViewById(R.id.textCurrentName);
        TextView currentQtyTitle = findViewById(R.id.textCurrentQty);
        TextView currentCategoryTitle = findViewById(R.id.textCurrentCategory);

        // Brings in the index from clicked on item and set currentItem
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String indexString = extras.getString("index");
            int index = Integer.parseInt(indexString);
            currentItem = itemArrayList.get(index);
            currentNameTitle.setText(currentItem.getName());
            currentQtyTitle.setText(String.valueOf(currentItem.getQty()));
            currentCategoryTitle.setText(currentItem.getCategory());
        }


        // Updates item in array list and database
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try (SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(EditItemMenu.this)) {
                    String tempNameNew = editTextNewName.getText().toString();
                    int tempQtyNew = Integer.parseInt(editTextNewQty.getText().toString());
                    String tempCategoryNew = editTextNewCategory.getText().toString();

                    currentItem.setName(tempNameNew);                                                      // updates array
                    currentItem.setQty(tempQtyNew);
                    currentItem.setCategory(tempCategoryNew);
                    sqLiteManager.updateItemInDB(currentItem);                                             // updates database
                }

                Toast.makeText(EditItemMenu.this, "Success!", Toast.LENGTH_LONG).show();

                editTextNewName.setText(null);
                editTextNewQty.setText(null);
                editTextNewCategory.setText(null);
            }
        });

        // onclick for back button to go back to main screen
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditItemMenu.this, MainActivity.class));
            }
        });

        // onclick to delete found item
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try (SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(EditItemMenu.this)) {
                    Toast.makeText(EditItemMenu.this, currentItem.getName() + " deleted!", Toast.LENGTH_LONG).show();
                    sqLiteManager.deleteItemInDB(currentItem);
                }
                itemArrayList.remove(currentItem);
            }
        });
    }
}