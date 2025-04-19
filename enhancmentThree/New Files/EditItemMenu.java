package com.example.cs_360_jasonrestucci_option1_projecttwo;

import static com.example.cs_360_jasonrestucci_option1_projecttwo.MainActivity.itemArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
 * Date Last Modified: 4/1/25
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
        EditText editTextNewNotes = findViewById(R.id.editTextNewNotes);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        Button buttonBack = findViewById(R.id.buttonBack3);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        TextView currentNameTitle = findViewById(R.id.textCurrentName);
        TextView currentQtyTitle = findViewById(R.id.textCurrentQty);
        TextView currentCategoryTitle = findViewById(R.id.textCurrentCategory);
        TextView currentNotesTitle = findViewById(R.id.textNotes);

        // Brings in the index from clicked on item and set currentItem
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String indexString = extras.getString("index");
            int index = Integer.parseInt(indexString);
            currentItem = itemArrayList.get(index);
            currentNameTitle.setText(currentItem.getName());
            currentQtyTitle.setText(String.valueOf(currentItem.getQty()));
            currentCategoryTitle.setText(currentItem.getCategory());
            currentNotesTitle.setText(currentItem.getNotes());
        }


        // Updates item in array list and database
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try (SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(EditItemMenu.this)) {

                    if(!editTextNewName.getText().toString().isEmpty()){
                        String tempNameNew = editTextNewName.getText().toString();
                        currentItem.setName(tempNameNew.toUpperCase());
                    }
                    if(!editTextNewQty.getText().toString().isEmpty()){
                        int tempQtyNew = Integer.parseInt(editTextNewQty.getText().toString());
                        currentItem.setQty(tempQtyNew);
                    }
                    if(!editTextNewCategory.getText().toString().isEmpty()){
                        String tempCategoryNew = editTextNewCategory.getText().toString();
                        currentItem.setCategory(tempCategoryNew.toUpperCase());
                    }
                    if(!editTextNewNotes.getText().toString().isEmpty()){
                        String tempNotesNew = editTextNewNotes.getText().toString();
                        currentItem.setNotes(tempNotesNew.toUpperCase());
                    }
                    sqLiteManager.updateItemInDB(currentItem);
                }

                Toast.makeText(EditItemMenu.this, "Success!", Toast.LENGTH_LONG).show();

                editTextNewName.setText(null);
                editTextNewQty.setText(null);
                editTextNewCategory.setText(null);
                editTextNewNotes.setText(null);
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