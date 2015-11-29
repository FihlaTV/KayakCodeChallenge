package com.shaznee.kayak.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.shaznee.kayak.R;
import com.shaznee.kayak.fragments.AirlinesFragment;
import com.shaznee.kayak.fragments.FavoriteAirlinesFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a FavoriteAirlinesFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return new AirlinesFragment();
            case 1:
                return new FavoriteAirlinesFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.title_airlines_tab).toUpperCase();
            case 1:
                return context.getString(R.string.title_favorite_airlines_tab).toUpperCase();
        }
        return null;
    }
}