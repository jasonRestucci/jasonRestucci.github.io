package com.example.cs_360_jasonrestucci_option1_projecttwo;

/**
 * Author: Jason Restucci
 * Date Last Modified: 3/18/25
 * Description: Object Class for Item Models
 */

public class MyModel {
    String item = "";
    int qty = 0;
    int index = 0;

    public MyModel(String item, int qty, int index) {
        this.item = item;
        this.qty = qty;
        this.index= index;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setIndex(int index) { this.index = index; }

    public int getIndex(){ return index; }
}
