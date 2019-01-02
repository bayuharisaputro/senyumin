package com.example.bayuharisaputro.senyumin.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.bayuharisaputro.senyumin.Fragment.Hot;
import com.example.bayuharisaputro.senyumin.Fragment.Main;
import com.example.bayuharisaputro.senyumin.Fragment.PostKamu;

/**
 * Created by Chirag on 30-Jul-17.
 */

public class PagerAdapter2 extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapter2(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                PostKamu tab1 = new PostKamu();
                return tab1;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
