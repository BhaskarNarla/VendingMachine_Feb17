package com.tcs.vendingmachine.Utils;

import android.content.Context;

import com.tcs.vendingmachine.R;
import com.tcs.vendingmachine.pojo.Product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public static void initProducts(){
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(R.drawable.waterbottle,"waterbottle",1.5f,5,-1));
        products.add(new Product(R.drawable.sprite,"sprite",1.5f,3,-1));
        products.add(new Product(R.drawable.snickers,"snickers",0.5f,4,-1));
        products.add(new Product(R.drawable.pepsi,"pepsi",2.5f,2,-1));
        products.add(new Product(R.drawable.coke,"coke",1.5f,5,-1));
        products.add(new Product(R.drawable.oreo,"oreo",0.2f,5,-1));
        products.add(new Product(R.drawable.twix,"twix",0.5f,1,-1));

        DataHolder.getInstance().setAvailableProducts(products);

    }
    public static int getSelectedItemsCount(){
        int count = 0;
        List<Product> products = DataHolder.getInstance().getAvailableProducts();
        for(Product product:products){
            if(product.getSelectedQty()>0){
                count+=product.getSelectedQty();
            }
        }
        return count;
    }

    public static List<Product> getSelectedItems(){
        List<Product> selectedProducts= new ArrayList<>();
        List<Product> products = DataHolder.getInstance().getAvailableProducts();
        for(Product product:products){
            if(product.getSelectedQty()>0){
                selectedProducts.add(product);
            }
        }
        return selectedProducts;
    }

    public static void updateTransaction(){
        List<Product> products = DataHolder.getInstance().getAvailableProducts();
        for(Product product:products){
            int qty = product.getQuantity();
            int selectedQty = product.getSelectedQty();
            if(selectedQty>0){
                product.setQuantity(qty-selectedQty);
                product.setSelectedQty(-1);
            }
        }
        DataHolder.getInstance().setAvailableProducts(products);
    }

    public static Map<String, Integer> calculateChangeDenomination(Context context,double change) {
        int intPart = (int) change;
        float fracPart = ((int) (change * 100) - (intPart * 100) ) / 100f;

        Map<String, Integer> changeDenominationsMap = new LinkedHashMap<>();

        for (int i = Constants.usCurDenominations.length - 1; i >= 0; i--) {
            float curDenomination = Constants.usCurDenominations[i];
            if (intPart > 0) {
                int count = intPart / (int) curDenomination;
                intPart %= (int) curDenomination;
                if (count > 0) {
                    changeDenominationsMap.put(context.getString(R.string.dollar_sign)+(int) curDenomination , count);
                }
            } else if (fracPart > 0 && curDenomination < 1f) {
                int count = (int) (fracPart * 100) / (int) (curDenomination * 100);
                fracPart = ((int) (fracPart * 100) % (int) (curDenomination * 100)) / 100f;
                if (count > 0) {
                    changeDenominationsMap.put(context.getString(R.string.cent_sign)+(int)(curDenomination * 100), count);
                }
            }
        }
        return changeDenominationsMap;
    }

    public static float getTotalAmt(){
        float total = 0.0f;
        List<Product> products = DataHolder.getInstance().getAvailableProducts();
        for(Product p:products){
            if(p.getSelectedQty()>0){
                total += (p.getSelectedQty()*p.getPrice());
            }
        }
        return total;
    }

    public static float roundToTwoDigit(float param) {
        return Math.round(param * 100.0) / 100.0f;
    }
}