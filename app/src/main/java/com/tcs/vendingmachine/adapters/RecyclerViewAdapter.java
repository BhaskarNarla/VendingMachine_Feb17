package com.tcs.vendingmachine.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcs.vendingmachine.R;
import com.tcs.vendingmachine.interfaces.RecyclerViewEventListener;
import com.tcs.vendingmachine.pojo.Product;

import java.util.List;

/**
 * Created by 461701 on 1/19/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<Product> mList;
    private final RecyclerViewEventListener events;
    private final Context context;
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public RecyclerViewAdapter(List<Product> mList, Context context,RecyclerViewEventListener events){
        this.mList = mList;
        this.events = events;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycletview_row, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v,events);
        return pvh;
    }
    @Override
    public void onBindViewHolder(ViewHolder personViewHolder, int i) {

        Product product = mList.get(i);
        if(product.getSelectedQty()>0){
            personViewHolder.selectedQty.setVisibility(View.VISIBLE);
            personViewHolder.selectedQty.setText(""+product.getSelectedQty());
        }else{
            personViewHolder.selectedQty.setVisibility(View.INVISIBLE);
        }
        personViewHolder.icon.setImageResource(product.getIcon());
        personViewHolder.productName.setText(product.getName());

        if(product.getPrice()>=1)
            personViewHolder.price.setText(context.getString(R.string.dollar_sign)+product.getPrice());
        else
            personViewHolder.price.setText(context.getString(R.string.cent_sign)+product.getPrice());

        personViewHolder.availbality.setText("Avl:"+product.getQuantity());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final CardView cv;
        final ImageView icon;
        final TextView selectedQty;
        final TextView productName;
        final TextView price;
        final TextView availbality;
        final RecyclerViewEventListener events;

        ViewHolder(View itemView, RecyclerViewEventListener events) {
            super(itemView);
            this.events = events;
            cv = (CardView)itemView.findViewById(R.id.cv);
            cv.setOnClickListener(this);
            productName = (TextView)itemView.findViewById(R.id.product_name);
            price = (TextView)itemView.findViewById(R.id.Price);
            availbality = (TextView)itemView.findViewById(R.id.Available);
            selectedQty = (TextView)itemView.findViewById(R.id.selectedQty);
            icon = (ImageView)itemView.findViewById(R.id.icon);
        }

        @Override
        public void onClick(View v) {
            events.onRVItemClick(v, getAdapterPosition());
        }
    }

}
