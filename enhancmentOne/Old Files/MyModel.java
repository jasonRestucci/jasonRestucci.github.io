package com.example.cs_360_jasonrestucci_option1_projecttwo;

public class MyModel {
    String item = "";
    int qty = 0;

    public MyModel(String item, int qty) {
        this.item = item;
        this.qty = qty;
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
}
