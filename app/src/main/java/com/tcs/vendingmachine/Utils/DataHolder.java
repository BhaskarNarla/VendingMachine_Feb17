package com.tcs.vendingmachine.Utils;

import com.tcs.vendingmachine.pojo.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 461701 on 2/10/2016.
 */
public class DataHolder {
    private static DataHolder ourInstance = new DataHolder();

    private List<Product> availableProducts;
    private List<String> currency;
    private List<Product> selectedProducts;

    public static DataHolder getInstance() {
        return ourInstance;
    }

    private DataHolder() {
    }

    public List<Product> getAvailableProducts() {
        return availableProducts;
    }

    public void setAvailableProducts(List<Product> availableProducts) {
        this.availableProducts = availableProducts;
    }

    public List<String> getCurrency() {
        return currency;
    }

    public void setCurrency(List<String> currency) {
        this.currency = currency;
    }

    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<Product> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }
}
