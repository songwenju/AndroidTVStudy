package com.songwenju.androidtvstudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.songwenju.androidtools.util.LogUtil;

/**
 * Created by songwenju on 17-5-23.
 */

public class SearchActivity extends Activity{
    private SearchFragment mSearchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchFragment = (SearchFragment) getFragmentManager().findFragmentById(R.id.search_fragment);
    }

    @Override
    public boolean onSearchRequested() {
        LogUtil.i(this,"SearchActivity.onSearchRequested.");
        //if (mSearchFragment.hasResults()) {
        startActivity(new Intent(this, SearchActivity.class));
        //} else {
        //mSearchFragment.startRecognition();
        //}
        return true;
    }
}
