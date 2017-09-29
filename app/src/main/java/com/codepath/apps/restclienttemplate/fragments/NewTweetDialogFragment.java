package com.codepath.apps.restclienttemplate.fragments;

import android.app.DialogFragment;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.User;

import org.parceler.Parcels;

/**
 * Created by hezhang on 9/29/17.
 */

public class NewTweetDialogFragment extends android.support.v4.app.DialogFragment {

    private User userMe;
    private TextView myUserName;
    private TextView myScreenName;
    private ImageView myProfilePic;

    public NewTweetDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
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

        //Get the user info from the data bundle
        userMe = (User) Parcels.unwrap(getArguments().getParcelable("userInfo"));
        setUpViews(view);

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

}

