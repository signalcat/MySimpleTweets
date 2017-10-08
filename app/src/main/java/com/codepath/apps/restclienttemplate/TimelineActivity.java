package com.codepath.apps.restclienttemplate;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Parcel;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.fragments.HomeTimeLineFragment;
import com.codepath.apps.restclienttemplate.fragments.NewTweetDialogFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class TimelineActivity extends AppCompatActivity
    implements TweetsListFragment.TweetSelectedListener, NewTweetDialogFragment.OnPostListener {

    private TwitterClient client;
    private User userMe;

    // It is ridiculously and unnecessarily cumbersome to obtain a reference to homeTimeLineFragment, so we use this hack to simply things.
    // Unless this class itself gets unloaded, this should be reliable.
    //public static HomeTimeLineFragment homeTimeLineFragment;

    // If viewpager is dynamic with many pages and fragments, we
    // want to use this to smartly cache the fragments
    private SmartFragmentStatePagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitter_icon_square);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // get the view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);

        // Get the smart adapter
        adapterViewPager = new TweetsPagerAdapter(getSupportFragmentManager());

        // set the adapter for the pager
        vpPager.setAdapter(adapterViewPager);
        // setup the tablayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

        // Get the user's info
        client = TwitterApp.getRestClient();
        getMyInfoObject();

    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.miCompose) {
            // Show new tweet fragment
            showNewTweetDialog();
        }
        return true;
    }

    public void onProfileView(MenuItem item) {
        // Launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
        // user_timeline
        // verify_credentials
    }

    @Override
    public void onTweetSelected(Tweet tweet) {
        Toast.makeText(this, tweet.body, Toast.LENGTH_LONG).show();
    }

    private void showNewTweetDialog() {
        FragmentManager fm  = getSupportFragmentManager();
        NewTweetDialogFragment newTweetDialogFragment = NewTweetDialogFragment.newInstance(Parcels.wrap(userMe));
        newTweetDialogFragment.show(fm, "fragment_new_tweet");
    }

    private void getMyInfoObject() {
        client.getMyInfo(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
                try {
                    userMe = User.fromJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("TwitterClient", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }


    @Override
    public void onUpdateTweet(Tweet newTweet) {
        // Gets homeline Fragment item within the pager
        HomeTimeLineFragment homeTimeLineFragment = (HomeTimeLineFragment) adapterViewPager.getRegisteredFragment(0);
        // Pass the new tweet to the homeline fragment
        homeTimeLineFragment.insertTweetAtTop(newTweet);
        Toast.makeText(getContext(), "onUpdate", Toast.LENGTH_LONG).show();
    }
}
