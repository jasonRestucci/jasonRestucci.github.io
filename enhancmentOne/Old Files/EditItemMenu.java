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

public class EditItemMenu extends AppCompatActivity {
    String tempName;
    int tempQty;
    String tempNameNew;
    int tempQtyNew;
    boolean found;

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
        // declare variables that will hold item search stuff
        // connect need UI elements
        EditText editTextItemName = findViewById(R.id.editTextEditItemName);
        EditText editTextItemQty = findViewById(R.id.editTextEditItemQty);
        Button buttonSearch = findViewById(R.id.buttonEditItem);
        EditText editTextNewName = findViewById(R.id.editTextNewName);
        EditText editTextNewQty = findViewById(R.id.editTextNewQty);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        Button buttonBack = findViewById(R.id.buttonBack3);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        TextView editTextTitle = findViewById(R.id.textEditItemName);
        buttonDelete.setVisibility(View.INVISIBLE);

        // onclick for search button takes text in editTexts and searches itemArrayList
        // if match is found, toast message, flip visibilities, clears text input, sets variables to user input
        // if not, toast message
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempName = editTextItemName.getText().toString();
                tempQty = Integer.parseInt(editTextItemQty.getText().toString());
                found = false;

                for (int i = 0; i < itemArrayList.size(); i++){
                    if (itemArrayList.get(i).getName().equals(tempName) && itemArrayList.get(i).getQty() == tempQty){
                        found = true;
                        Toast.makeText(EditItemMenu.this, "Item found! Enter new name and quantity.", Toast.LENGTH_LONG).show();
                        editTextItemName.setVisibility(View.INVISIBLE);
                        editTextItemName.setText(null);
                        editTextItemQty.setVisibility(View.INVISIBLE);
                        editTextItemQty.setText(null);
                        buttonSearch.setVisibility(View.INVISIBLE);
                        editTextNewName.setVisibility(View.VISIBLE);
                        editTextNewQty.setVisibility(View.VISIBLE);
                        buttonUpdate.setVisibility(View.VISIBLE);
                        buttonDelete.setVisibility(View.VISIBLE);
                        editTextTitle.setText("Enter New Name/Quantity OR press DELETE to delete");

                    }
                }
                if (!found) {
                    Toast.makeText(EditItemMenu.this, "Item not found", Toast.LENGTH_LONG).show();
                    editTextItemName.setText(null);
                    editTextItemQty.setText(null);
                }
            }
        });

        // onclick for update button searches item arraylist again using variables
        // when match is found, sets new attributes, toast, flips visibilities, clears text input and variables
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(EditItemMenu.this);
                tempNameNew = editTextNewName.getText().toString();
                tempQtyNew = Integer.parseInt(editTextNewQty.getText().toString());
                if (found){
                    for (int i = 0; i < itemArrayList.size(); i++){
                        if (itemArrayList.get(i).getName().equals(tempName) && itemArrayList.get(i).getQty() == tempQty){
                            itemArrayList.get(i).setName(tempNameNew);                                                      // updates array
                            itemArrayList.get(i).setQty(tempQtyNew);
                            sqLiteManager.updateItemInDB(itemArrayList.get(i));                                             // updates database

                            Toast.makeText(EditItemMenu.this, "Item name set to: " + tempNameNew + ", Item quantity set to: " + tempQtyNew, Toast.LENGTH_LONG).show();

                            editTextItemName.setVisibility(View.VISIBLE);                                                   // flips UI
                            editTextItemQty.setVisibility(View.VISIBLE);
                            buttonSearch.setVisibility(View.VISIBLE);
                            editTextNewName.setVisibility(View.INVISIBLE);
                            editTextNewName.setText(null);
                            editTextNewQty.setVisibility(View.INVISIBLE);
                            editTextNewQty.setText(null);
                            buttonUpdate.setVisibility(View.INVISIBLE);
                            buttonDelete.setVisibility(View.INVISIBLE);
                            editTextTitle.setText("Enter Item Name and Quantity Below:");
                        }
                    }
                }
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
                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(EditItemMenu.this);
                if (found){
                    for (int i = 0; i < itemArrayList.size(); i++){
                        if (itemArrayList.get(i).getName().equals(tempName) && itemArrayList.get(i).getQty() == tempQty){
                            sqLiteManager.deleteItemInDB(itemArrayList.get(i));
                            itemArrayList.remove(itemArrayList.get(i));
                            //sqLiteManager.updateItemInDB(itemArrayList.get(i));

                            Toast.makeText(EditItemMenu.this, "Item Deleted!", Toast.LENGTH_LONG).show();

                            editTextItemName.setVisibility(View.VISIBLE);                                                   // flips UI
                            editTextItemQty.setVisibility(View.VISIBLE);
                            buttonSearch.setVisibility(View.VISIBLE);
                            editTextNewName.setVisibility(View.INVISIBLE);
                            editTextNewName.setText(null);
                            editTextNewQty.setVisibility(View.INVISIBLE);
                            editTextNewQty.setText(null);
                            buttonUpdate.setVisibility(View.INVISIBLE);
                            buttonDelete.setVisibility(View.INVISIBLE);
                            editTextTitle.setText("Enter Item Name and Quantity Below:");
                        }
                    }
                }
            }
        });
    }
}