package com.jantzapps.jantz.tablafy;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jantzapps.jantz.tablafy.TabFragment.int_items;
import static com.jantzapps.jantz.tablafy.TabFragment.tabLayout;


public class MyAdapter  extends FragmentStatePagerAdapter {


    public MyAdapter(FragmentManager fm)
    {
        super(fm);

    }
    
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ListenFragment();
            case 1:
                return new MyStandFragment();
            case 2:
                return new MyTabsFragment();
        }
        return null;

    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }



    @Override
    public int getCount() {

        return int_items;
    }

    public CharSequence getPageTitle(int position){
        return null;
    }
}

