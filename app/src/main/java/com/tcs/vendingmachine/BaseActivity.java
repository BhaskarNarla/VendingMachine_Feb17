package com.tcs.vendingmachine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tcs.vendingmachine.Fragments.HomeFragment;

/**
 * Base class for handling tool bar and fragment transactions
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private long mBackPressedTime;
    private ImageView refillButton;

    private FragmentManager.OnBackStackChangedListener mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            if(isHomeFragmentVisible()){
                refillButton.setVisibility(View.VISIBLE);
            }else {
                refillButton.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
        getSupportFragmentManager().addOnBackStackChangedListener(mOnBackStackChangedListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportFragmentManager().removeOnBackStackChangedListener(mOnBackStackChangedListener);
    }


    /**
     * Returns the toolbar for the current activity
     *
     * @return Toolbar
     */
    private Toolbar getActionBarToolbar() {
        if (mToolBar == null) {
            mToolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            if (mToolBar != null) {
                setSupportActionBar(mToolBar);
                if (null != getSupportActionBar()) {
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                    //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    // getSupportActionBar().setHomeButtonEnabled(true);

                    refillButton = (ImageView) mToolBar.findViewById(R.id.refillIB);
                }
            }
        }
        return mToolBar;
    }


    public void navigateToFragment(Class<?> className, boolean addToBackStack, Bundle bundle) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = null;

        try {
            fragment = (Fragment) className.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bundle != null && fragment != null) {
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(R.id.container, fragment, className.toString());

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(className.toString());
        }

        if(className.equals(HomeFragment.class)){
            refillButton.setVisibility(View.VISIBLE);
        }else {
            refillButton.setVisibility(View.GONE);
        }

        fragmentTransaction.commitAllowingStateLoss();


    }

    /**
     * Refreshes the fragment
     */
    public void refreshCurrentFragment() {

        Fragment currentFragment = getCurrentFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);

        fragmentTransaction.commit();

    }

    /**
     * checks the fragment container for raise ticket fragment
     *
     * @return true/false
     */
    protected boolean isHomeFragmentVisible() {

        Fragment currentFragment = getCurrentFragment();

        if (currentFragment instanceof HomeFragment) {
            return true;
        }
        return false;

    }

    /**
     * Returns current fragment in the container
     *
     * @return Fragment
     */
    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.container);
    }

    /**
     * Method to return the back stack count
     *
     * @return int back stack count
     */
    private int getBackStackCount() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return fragmentManager.getBackStackEntryCount();
    }

    @Override
    public void onBackPressed() {
        int count = getBackStackCount();
        if (count < 1) {
            if (System.currentTimeMillis() < mBackPressedTime + 2000) {
                finish();
            } else {
                Toast.makeText(BaseActivity.this, getString(R.string.exit_message), Toast.LENGTH_SHORT).show();
                mBackPressedTime = System.currentTimeMillis();
            }
        } else {
            removeCurrentFragment();
            super.onBackPressed();
        }
    }

    public void removeCurrentFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFrag =  getSupportFragmentManager().findFragmentById(R.id.container);

        if (currentFrag != null)
            transaction.remove(currentFrag);

        transaction.commit();
    }

}
