package com.tcs.vendingmachine.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tcs.vendingmachine.BaseActivity;
import com.tcs.vendingmachine.R;
import com.tcs.vendingmachine.Utils.DataHolder;
import com.tcs.vendingmachine.Utils.Utils;
import com.tcs.vendingmachine.adapters.RecyclerViewAdapter;
import com.tcs.vendingmachine.interfaces.OnFragmentInteractionListener;
import com.tcs.vendingmachine.interfaces.RecyclerViewEventListener;
import com.tcs.vendingmachine.pojo.Product;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment implements RecyclerViewEventListener,View.OnClickListener {

    private RecyclerView mRecyclerView;
    private Button mContinueBtn;
    private BaseActivity mListener;
    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener = (BaseActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mContinueBtn = (Button)view.findViewById(R.id.continueBtn);
        mContinueBtn.setOnClickListener(this);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.rcv);
        mRecyclerView.setHasFixedSize(true);
        //LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        GridLayoutManager glm = new GridLayoutManager(getActivity(),3);
        mRecyclerView.setLayoutManager(glm);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(DataHolder.getInstance().getAvailableProducts(),getContext(),HomeFragment.this);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onRVItemClick(View view, int position) {
        showSelectQtyAlert(position);
    }

    private void showSelectQtyAlert(final int position){
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View view = factory.inflate(R.layout.select_quantity_dialog, null);
        final AlertDialog SelectQtyDialog = new AlertDialog.Builder(getActivity()).create();
        SelectQtyDialog.setView(view);

        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectQtyDialog.dismiss();
            }
        });
        view.findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText qtyET = (EditText)view.findViewById(R.id.qty);
                int selectedCount = Utils.getSelectedItemsCount();
                List<Product> products = DataHolder.getInstance().getAvailableProducts();
                if(!TextUtils.isEmpty(qtyET.getText())) {
                    int value = Integer.parseInt(qtyET.getText().toString());
                    if( value > products.get(position).getQuantity()){
                        Toast.makeText(getActivity(),"Available products "+products.get(position).getQuantity(),Toast.LENGTH_SHORT).show();
                    }else if(selectedCount+value > 5)
                        Toast.makeText(getActivity(),"You can not select more than 5 products",Toast.LENGTH_SHORT).show();
                    else {
                        products.get(position).setSelectedQty(value);
                        DataHolder.getInstance().setAvailableProducts(products);
                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(DataHolder.getInstance().getAvailableProducts(),getContext(), HomeFragment.this);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.invalidate();
                        SelectQtyDialog.dismiss();
                    }
                }else{
                    DataHolder.getInstance().getAvailableProducts().get(position).setSelectedQty(-1);
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(DataHolder.getInstance().getAvailableProducts(),getContext(),HomeFragment.this);
                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.invalidate();
                    SelectQtyDialog.dismiss();
                }

            }
        });

        SelectQtyDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v==mContinueBtn){
            if(Utils.getSelectedItemsCount()<=0){
                Toast.makeText(getActivity(),"please select min 1 product to continue",Toast.LENGTH_SHORT).show();
            }else {
                mListener.navigateToFragment(SummaryFragment.class, true, null);
            }
        }
    }
}
