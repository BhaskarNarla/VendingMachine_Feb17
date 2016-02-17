package com.tcs.vendingmachine.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tcs.vendingmachine.BaseActivity;
import com.tcs.vendingmachine.HomeActivity;
import com.tcs.vendingmachine.R;
import com.tcs.vendingmachine.Utils.Constants;
import com.tcs.vendingmachine.Utils.Utils;
import com.tcs.vendingmachine.interfaces.OnFragmentInteractionListener;
import com.tcs.vendingmachine.pojo.Product;

import java.util.List;

public class SummaryFragment extends Fragment implements View.OnClickListener,TextWatcher{
    private BaseActivity mListener;
    private float mTotalAmt,mInsertedAmt,mChange=0.0f;
    Button mPurchaseBtn;
    TableLayout mProductsTl;
    EditText insertAmtEt;
    TextView mTotalAmtTv,mChangeTv;

    String[] tableHeaders ={"Product","Cost","Qty","Total"};

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener = (BaseActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        mTotalAmt = Utils.getTotalAmt();

        mPurchaseBtn = (Button)view.findViewById(R.id.purchaseBtn);
        mPurchaseBtn.setOnClickListener(this);

        insertAmtEt = (EditText)view.findViewById(R.id.insertAmtEt);
        insertAmtEt.addTextChangedListener(this);

        mChangeTv= (TextView)view.findViewById(R.id.changeTv);
        mProductsTl =(TableLayout) view.findViewById(R.id.productsTL);

        displaySelectedItems();

        mTotalAmtTv = (TextView)view.findViewById(R.id.totalAmtTv);
        if(mTotalAmt>=1) {
            String total = getString(R.string.dollar_sign) + Utils.roundToTwoDigit(mTotalAmt);
            mTotalAmtTv.setText(total);
        }else {
            String total = getString(R.string.cent_sign) + Utils.roundToTwoDigit(mTotalAmt);
            mTotalAmtTv.setText(total);
        }

        return view;
    }

    private void displaySelectedItems(){
        List<Product> selectedProducts = Utils.getSelectedItems();

        TableRow tr = new TableRow(getActivity());
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tr.setBackgroundColor(getResources().getColor(R.color.dark_gray));
        for (String header:tableHeaders){
            TextView headerTv = new TextView(getActivity());
            headerTv.setText(header);
            headerTv.setGravity(Gravity.CENTER);
            headerTv.setTextColor(getResources().getColor(R.color.white));
            headerTv.setPadding(5, 5, 5, 5);
            headerTv.setTextSize(18);
            tr.addView(headerTv);
        }
        mProductsTl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));

        for (Product product:selectedProducts){
            TableRow tr1 = new TableRow(getActivity());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView PnameTV = new TextView(getActivity());
            PnameTV.setText(product.getName());
            PnameTV.setGravity(Gravity.CENTER);
            PnameTV.setPadding(5, 5, 5, 5);
            PnameTV.setTextSize(16);
           // PnameTV.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
            tr1.addView(PnameTV);

            TextView PcostTV = new TextView(getActivity());
            float pric = product.getPrice();
            PcostTV.setGravity(Gravity.CENTER);
            PcostTV.setPadding(5, 5, 5, 5);
            PcostTV.setTextSize(16);
            if(pric>=1)
                PcostTV.setText(getString(R.string.dollar_sign)+Utils.roundToTwoDigit(pric));
            else
                PcostTV.setText(getString(R.string.cent_sign)+Utils.roundToTwoDigit(pric));
            //PcostTV.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
            tr1.addView(PcostTV);

            TextView PqtyTV = new TextView(getActivity());
            PqtyTV.setText(""+product.getSelectedQty());
            PqtyTV.setGravity(Gravity.CENTER);
            PqtyTV.setPadding(5, 5, 5, 5);
            PqtyTV.setTextSize(16);
            //PqtyTV.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
            tr1.addView(PqtyTV);

            TextView totalTV = new TextView(getActivity());
            totalTV.setGravity(Gravity.CENTER);
            totalTV.setPadding(5, 5, 5, 5);
            totalTV.setTextSize(16);
            float total = pric*product.getSelectedQty();
            if(total>=1)
                totalTV.setText(getString(R.string.dollar_sign)+Utils.roundToTwoDigit(total));
            else
                totalTV.setText(getString(R.string.cent_sign)+Utils.roundToTwoDigit(total));
            //totalTV.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
            tr1.addView(totalTV);

            mProductsTl.addView(tr1, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if(mInsertedAmt>=mTotalAmt){
            Bundle bundle = new Bundle();
            bundle.putFloat(Constants.TOTAL,mTotalAmt);
            bundle.putFloat(Constants.INSERTED,mInsertedAmt);
            bundle.putFloat(Constants.CHANGE,mChange);
            mListener.navigateToFragment(SuccessFragment.class, false, bundle);
        }else {
            Toast.makeText(getActivity(),"In sufficient amount",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!TextUtils.isEmpty(s)){
            mInsertedAmt = Float.parseFloat(s.toString());
            mChange = Utils.roundToTwoDigit(mInsertedAmt - mTotalAmt);

            if(mChange>=1)
                mChangeTv.setText(getString(R.string.dollar_sign)+Utils.roundToTwoDigit(mChange));
            else
                mChangeTv.setText(getString(R.string.cent_sign)+Utils.roundToTwoDigit(mChange));
        }else{
            mChange=0.0f;
            mChangeTv.setText("");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
