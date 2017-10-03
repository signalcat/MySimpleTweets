package com.codepath.apps.restclienttemplate.fragments;

import android.app.DialogFragment;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TimelineActivity;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by hezhang on 9/29/17.
 */

public class NewTweetDialogFragment extends android.support.v4.app.DialogFragment {

    private User userMe;
    private TextView myUserName;
    private TextView myScreenName;
    private ImageView myProfilePic;
    private EditText etCompose;
    private Tweet mytweet;
    private String tweetInput;

    private TwitterClient client;

    public NewTweetDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    // 1. Defines the listener interface with a method
    //    passing back filters as result to activity.
    public interface OnPostListener {
        void onUpdateTweet(Tweet newTweet);
    }

    public static NewTweetDialogFragment newInstance(Parcelable userMe) {
        NewTweetDialogFragment frag = new NewTweetDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("userInfo", userMe);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_tweet, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the user info from the data bundle
        userMe = (User) Parcels.unwrap(getArguments().getParcelable("userInfo"));
        setUpViews(view);

        // Get the new tweet from edit box
        etCompose = (EditText) view.findViewById(R.id.etCompose);

        // Hook up onclick listener to save the tweet and post
        Button btnTweet = (Button) view.findViewById(R.id.btnTweet);
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store the text into the user object
                tweetInput = etCompose.getText().toString();
                //userMe.status = etCompose.getText().toString();
                Log.d("TwitterClient", "BEGIN|" + tweetInput + "|END");
                postNewTweet(tweetInput);

                String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
                SimpleDateFormat currentTime = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
                Log.d("Current time: ", currentTime.toString());
                Tweet newTweet = new Tweet(userMe.uid, userMe, userMe.screenName, "Sun Oct 01 00:37:26 +0000 2017", tweetInput);
                //t.save();
                //List<Tweet> tweets = SQLite.select().from(Tweet.class).queryList();
                //for (Tweet z: tweets)
                //    Log.i("TwitterClient", "onClick: " + z.toString());

                // Return filters back to activity through the implemented listener
                OnPostListener listener = (OnPostListener) getActivity();
                listener.onUpdateTweet(newTweet);
                // Close the dialog to return back to the parent activity
                dismiss();

            }
        });

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
    }

    public void setUpViews(View view) {
        myUserName = (TextView) view.findViewById(R.id.tvMyUserName);
        myScreenName = (TextView) view.findViewById(R.id.tvMyScreenName);
        myProfilePic = (ImageView) view.findViewById(R.id.ivMyProfileImage);

        myUserName.setText(userMe.name);
        myScreenName.setText(userMe.screenName);
        Glide.with(getContext())
                .load(userMe.profileImageUrl)
                .into(myProfilePic);
    }

    // Post
    public void postNewTweet(String content) {
        // URL encoding
        String urlString = null;
        try {
            urlString = URLEncoder.encode(content,"UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            Log.d("TwitterClient", "Failed to encode message: |" + urlString + "|");
        }

        client = TwitterApp.getRestClient();
        client.postNewTweet(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", "onSuccess() called with: statusCode = [" + statusCode + "], headers = [" + headers + "], response = [" + response + "]");
                Log.d("TwitterClient", response.toString());
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
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        }, urlString);
    }
}

