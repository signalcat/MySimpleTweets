package com.codepath.apps.restclienttemplate;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        String  screenName = getIntent().getStringExtra("screen_name");
        // Create the user fragment
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
        // Display the user timeline fragment inside the container (dynamically)
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Make change
        ft.replace(R.id.flContainer, userTimelineFragment);
        // Commit
        ft.commit();

        client = TwitterApp.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Deserialize the User object
                try {
                    User user = User.fromJSON(response);
                    // Set the title of the actionBar based on the user
                    getSupportActionBar().setTitle(user.screenName);
                } catch (JSONException e) {

                }
            }
        });
    }
}
