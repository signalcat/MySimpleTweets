package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;

import org.parceler.Parcels;

/**
 * Created by hezhang on 10/7/17.
 */

public class UsersProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Get the user from Timeline activity
        User user = (User) Parcels.unwrap(getIntent().getParcelableExtra("clickedUser"));
        // Load the user head line
        populateUserHeadline(user);

        // Create the user fragment
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(user.screenName);
        // Display the user timeline fragment inside the container (dynamically)
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Make change
        ft.replace(R.id.flContainer, userTimelineFragment);
        // Commit
        ft.commit();
    }

        public void populateUserHeadline (User user) {
            TextView tvName = (TextView) findViewById(R.id.tvName);
            TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
            TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
            TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
            ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

            tvName.setText(user.name);
            tvTagline.setText(user.tagLine);
            tvFollowers.setText(user.followersCount + " Followers");
            tvFollowing.setText(user.followingCount + " Following");

            Glide.with(this).load(user.profileImageUrl).into(ivProfileImage);
    }
}
