package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by hezhang on 10/3/17.
 */

public class TweetsPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] {"Home", "Mentions"};
    private Context context;

    public TweetsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    // Return the total # of fragments
    @Override
    public int getCount() {
        return 2;
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
