package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hezhang on 10/3/17.
 */

public abstract class TweetsListFragment extends Fragment
        implements TweetAdapter.TweetAdapterListener {

    public interface TweetSelectedListener {
        // handle tweet selection
        public void onTweetSelected(Tweet tweet);
    }
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    TweetAdapter tweetAdapter;

    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    // inflation happens inside onCreateView

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);

        // Find the recycler view
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweet);
        // Init the arraylist (data source)
        tweets = new ArrayList<>();

        // Construct the adapter from this datasource
        tweetAdapter = new TweetAdapter(tweets, this);
        // Set the adapter
        rvTweets.setAdapter(tweetAdapter);

        // RecyclerView setup (layout manager, use adapter)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvTweets.setLayoutManager(linearLayoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                long lastId = tweets.get(tweetAdapter.getItemCount()-1).uid;
                loadNextDataFromApi(lastId);
                //populateTimeLine();
            }
        };

        //Adds the scroll listener to RecyclerView
        rvTweets.addOnScrollListener(scrollListener);

        return v;
    }

    public void addItems(JSONArray response) {
        // For each entry deserialize the JSON object
                for (int i = 0; i < response.length(); i++) {
                    // Convert each object to a Tweet model
                    // Add that Tweet model to our data source
                    // Notify the adapter that we've added an item
                    try {
                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                        tweets.add(tweet);
                        tweetAdapter.notifyItemInserted(tweets.size() - 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
    }

    @Override
    public void onItemSelected(View view, int position) {
        Tweet tweet = tweets.get(position);
        ((TweetSelectedListener) getActivity()).onTweetSelected(tweet);
    }

    // Append more page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public abstract void loadNextDataFromApi(long lastId);
}
