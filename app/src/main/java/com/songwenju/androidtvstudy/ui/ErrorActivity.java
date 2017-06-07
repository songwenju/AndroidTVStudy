package com.songwenju.androidtvstudy.ui;

import android.app.Activity;
import android.os.Bundle;

import com.songwenju.androidtvstudy.R;


public class ErrorActivity extends Activity {

    private ErrorFragment mErrorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testError();
    }

    private void testError() {
        mErrorFragment = new ErrorFragment();
        getFragmentManager().beginTransaction().add(R.id.main_browse_fragment, mErrorFragment).commit();
    }
}
