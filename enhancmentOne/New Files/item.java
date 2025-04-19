package com.example.cs_360_jasonrestucci_option1_projecttwo;

import static com.example.cs_360_jasonrestucci_option1_projecttwo.MainActivity.itemArrayList;

/**
 * Author: Jason Restucci
 * Date Last Modified: 3/18/25
 * Description: Class for item object
 */

public class item {
    private String name;
    private int qty;
    private int id;
    private String category;

    public item(String name, int qty, String category) {
        this.name = name;
        this.qty = qty;
        this.category = category;

        if (itemArrayList.isEmpty()){
            this.id = 0;
        }
        else{
            this.id = itemArrayList.size();
        }
    }

    public item(int id, String name, int qty, String category) {
        this.name = name;
        this.qty = qty;
        this.id = id;
        this.category = category;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category){ this.category = category; }

    public String getCategory() { return category; }
}
