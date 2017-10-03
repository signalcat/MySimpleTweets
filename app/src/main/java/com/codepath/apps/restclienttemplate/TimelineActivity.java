package com.codepath.apps.restclienttemplate;

import android.os.Parcel;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.fragments.NewTweetDialogFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
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

public class TimelineActivity extends AppCompatActivity {

    TweetsListFragment fragmentTweetsList;

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

//        if (tweets != null && tweetAdapter != null && scrollListener != null) {
//            // 1. First, clear the array of data
//            tweets.clear();
//            // 2. Notify the adapter of the update
//            tweetAdapter.notifyDataSetChanged(); // or notifyItemRangeRemoved
//            // 3. Reset endless scroll listener when performing a new search
//            scrollListener.resetState();
//        }

        fragmentTweetsList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);

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
            //showNewTweetDialog();
        }
        return true;
    }

//    private void showNewTweetDialog() {
//        FragmentManager fm  = getSupportFragmentManager();
//        NewTweetDialogFragment newTweetDialogFragment = NewTweetDialogFragment.newInstance(Parcels.wrap(userMe));
//        newTweetDialogFragment.show(fm, "fragment_new_tweet");
//    }

//    @Override
//    public void onUpdateTweet(Tweet newTweet) {
//        tweets.add(0,newTweet);
//        tweetAdapter.notifyItemInserted(0);
//
//        newTweet.save();
//        List<Tweet> tweetDb = SQLite.select().from(Tweet.class).queryList();
//        for (Tweet z: tweetDb)
//            Log.i("TwitterClient", "onClick: " + z.toString());
//
//    }

}
