package com.example.cs_360_jasonrestucci_option1_projecttwo;

import static com.example.cs_360_jasonrestucci_option1_projecttwo.MainActivity.itemArrayList;

/**
 * Author: Jason Restucci
 * Date Last Modified: 4/1/25
 * Description: Class for item object
 */

public class item {
    private String name;
    private int qty;
    private int id;
    private String category;
    private  String notes;

    public item(String name, int qty, String category, String notes) {
        this.name = name;
        this.qty = qty;
        this.category = category;
        this.notes = notes;

        if (itemArrayList.isEmpty()){
            this.id = 0;
        }
        else{
            this.id = itemArrayList.size();
        }
    }

    public item(int id, String name, int qty, String category, String notes) {
        this.name = name;
        this.qty = qty;
        this.id = id;
        this.category = category;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category){ this.category = category; }

    public String getCategory() { return category; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }
}
