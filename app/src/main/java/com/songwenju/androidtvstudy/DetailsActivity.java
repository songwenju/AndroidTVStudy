package com.songwenju.androidtvstudy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class DetailsActivity extends FragmentActivity {
    public static final String MOVIE = "Movie";
    public static final String SHARED_ELEMENT_NAME = "hero";
    public static final String NOTIFICATION_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }
}
