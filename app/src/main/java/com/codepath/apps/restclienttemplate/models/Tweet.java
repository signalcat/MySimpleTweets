package com.codepath.apps.restclienttemplate.models;

import android.os.Debug;
import android.text.format.DateUtils;
import android.util.Log;

import com.codepath.apps.restclienttemplate.TweetDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.text.format.DateUtils.SECOND_IN_MILLIS;

/**
 * Created by hezhang on 9/25/17.
 */
@Table(database = TweetDatabase.class)
@Parcel(analyze = {Tweet.class})
public class Tweet extends BaseModel {

    // List out the attributes
    @Column
    public String body;
    @Column
    @PrimaryKey
    public long uid; // data base ID for the tweet

    public User user;
    @Column
    public String screenName;
    @Column
    public String createdAt;
    @Column
    public String timeElapsed; // The difference between the create time and the current time
    public static long now = System.currentTimeMillis();

    // Constructor used for testing
    public Tweet(long uid, User user, String screenName, String createdAt, String body, String timeElapsed) {
        this.uid = uid;
        this.user = user;
        this.screenName = screenName;
        this.createdAt = createdAt;
        this.body = body;
        this.timeElapsed = timeElapsed;

    }
    public Tweet() {

    }
    // Deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // Extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.screenName = tweet.user.screenName;
        tweet.timeElapsed = getRelativeTimeAgo(tweet.createdAt);
        return tweet;
    }

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //relativeDate.
        return relativeDate;
    }

}
