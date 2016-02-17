package com.tcs.vendingmachine.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tcs.vendingmachine.BaseActivity;
import com.tcs.vendingmachine.HomeActivity;
import com.tcs.vendingmachine.R;
import com.tcs.vendingmachine.Utils.Constants;
import com.tcs.vendingmachine.Utils.Utils;
import com.tcs.vendingmachine.interfaces.OnFragmentInteractionListener;

import java.util.Iterator;
import java.util.Map;

public class SuccessFragment extends Fragment implements View.OnClickListener {
    private BaseActivity mListener;
    private float mChange=0.0f;
    private TableLayout mChangeTl;
    private Button mHomeBtn;
    private TextView mTotalAmtTv;
    private TextView mChangeTv;
    private TextView mInsertAmtTv;

    public SuccessFragment() {
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
        View view = inflater.inflate(R.layout.fragment_success, container, false);
        Bundle bundle = getArguments();
        if(bundle!=null){
            float mTotalAmt = bundle.getFloat(Constants.TOTAL);
            float mInsertedAmt = bundle.getFloat(Constants.INSERTED);
            mChange = bundle.getFloat(Constants.CHANGE);
            mHomeBtn = (Button)view.findViewById(R.id.homeBtn);
            mHomeBtn.setOnClickListener(this);
            mTotalAmtTv = (TextView)view.findViewById(R.id.totalAmtTv);
            mInsertAmtTv = (TextView)view.findViewById(R.id.insertedAmtTv);
            mChangeTv = (TextView)view.findViewById(R.id.changeTv);

            if(mTotalAmt >1)
                mTotalAmtTv.setText(getString(R.string.dollar_sign)+Utils.roundToTwoDigit(mTotalAmt));
            else
                mTotalAmtTv.setText(getString(R.string.cent_sign)+Utils.roundToTwoDigit(mTotalAmt));

            if(mInsertedAmt >1)
                mInsertAmtTv.setText(getString(R.string.dollar_sign)+Utils.roundToTwoDigit(mInsertedAmt));
            else
                mInsertAmtTv.setText(getString(R.string.cent_sign)+Utils.roundToTwoDigit(mInsertedAmt));

            if(mChange>1)
                mChangeTv.setText(getString(R.string.dollar_sign)+Utils.roundToTwoDigit(mChange));
            else
                mChangeTv.setText(getString(R.string.cent_sign)+Utils.roundToTwoDigit(mChange));
        }
        mChangeTl = (TableLayout)view.findViewById(R.id.changeTL);
        displayChange();
        Utils.updateTransaction();
        return view;
    }

    private void displayChange(){

        Map<String,Integer> changeDenomination = Utils.calculateChangeDenomination(getContext(),mChange);
        Iterator it = changeDenomination.entrySet().iterator();

        TableRow headerRow = new TableRow(getActivity());
        headerRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TextView headerTv = new TextView(getActivity());
        headerTv.setText(getString(R.string.change));
        headerTv.setGravity(Gravity.CENTER);
        headerTv.setPadding(5, 5, 5, 5);
        headerRow.addView(headerTv);

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            TableRow tr = new TableRow(getActivity());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView value = new TextView(getActivity());
            value.setText(pair.getKey().toString());
            value.setGravity(Gravity.CENTER);
            value.setPadding(5, 5, 5, 5);
            value.setTextSize(18);
            value.setTextColor(getResources().getColor(R.color.green));
            tr.addView(value);

            TextView times = new TextView(getActivity());
            times.setText("" + pair.getValue());
            times.setGravity(Gravity.CENTER);
            times.setPadding(5, 5, 5, 5);
            times.setTextSize(18);
            times.setTextColor(getResources().getColor(R.color.green));
            tr.addView(times);

            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException

            mChangeTl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if(v==mHomeBtn){
            //mListener.navigateToFragment(HomeFragment.class,false,null);
            //mListener.removeCurrentFragment();
            mListener.onBackPressed();
        }
    }
}
