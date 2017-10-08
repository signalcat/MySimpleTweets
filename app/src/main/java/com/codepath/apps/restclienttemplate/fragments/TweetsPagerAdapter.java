package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.codepath.apps.restclienttemplate.SmartFragmentStatePagerAdapter;

/**
 * Created by hezhang on 10/3/17.
 */
// Extend from SmartFragmentStatePagerAdapter now instead for more dynamic ViewPager items
public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter {
    private static int NUM_ITEMS = 2;

    private String tabTitles[] = new String[] {"Home", "Mentions"};

    public TweetsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // Return the total # of fragments
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Return the fragment to use depending on the position
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeTimeLineFragment();
        } else if (position == 1) {
            return new MentionsTimelineFragment();
        } else {
            return null;
        }
    }

    // Return the title

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
