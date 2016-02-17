package com.tcs.vendingmachine.pojo;

/**
 * Created by 461701 on 2/10/2016.
 */
public class Product {
    private int icon;
    private String name;
    private float price;
    private  int quantity;
    private int selectedQty;

    public Product(int icon,String name, float price, int quantity,int selectedQty){
        this.icon = icon;
        this.name=name;
        this.price=price;
        this.quantity=quantity;
        this.selectedQty=selectedQty;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSelectedQty() {
        return selectedQty;
    }

    public void setSelectedQty(int selectedQty) {
        this.selectedQty = selectedQty;
    }
}
