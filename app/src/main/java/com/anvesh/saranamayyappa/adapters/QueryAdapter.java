package com.anvesh.saranamayyappa.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.anvesh.saranamayyappa.Fragments.FragmentQueryFour;
import com.anvesh.saranamayyappa.Fragments.FragmentQueryOne;
import com.anvesh.saranamayyappa.Fragments.FragmentQueryThree;
import com.anvesh.saranamayyappa.Fragments.FragmentQueryTwo;

/**
 * Created by Hi on 27-06-2018.
 */
public class QueryAdapter extends FragmentPagerAdapter {
    public QueryAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new FragmentQueryOne();
        }
        if (position == 1) {
            fragment = new FragmentQueryTwo();
        }
        if (position == 2) {
            fragment = new FragmentQueryThree();
        }
        if(position==3){
            fragment=new FragmentQueryFour();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}



