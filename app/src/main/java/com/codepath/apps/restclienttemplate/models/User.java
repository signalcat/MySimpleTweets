package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by hezhang on 9/25/17.
 */
@Parcel
public class User {

    // List the attributes
    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;

    public User() {
    }

    // Deserialize the JSON
    public static User fromJSON(JSONObject json) throws JSONException {
        User user = new User();

        //Extract and fill the values
        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");

        return user;
    }
}
