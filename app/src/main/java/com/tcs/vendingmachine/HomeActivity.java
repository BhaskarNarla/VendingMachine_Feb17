package com.tcs.vendingmachine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tcs.vendingmachine.Fragments.HomeFragment;
import com.tcs.vendingmachine.Utils.Utils;
import com.tcs.vendingmachine.interfaces.OnFragmentInteractionListener;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView refillButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Utils.initProducts();
        refillButton = (ImageView) findViewById(R.id.refillIB);
        refillButton.setOnClickListener(this);

        navigateToFragment(HomeFragment.class, false, null);
    }


    @Override
    public void onClick(View v) {
        Utils.initProducts();
        refreshCurrentFragment();
    }
}
