package com.songwenju.androidtvstudy.ui;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.songwenju.androidtools.util.LogUtil;
import com.songwenju.androidtvstudy.R;
import com.songwenju.androidtvstudy.data.VideoItemLoader;
import com.songwenju.androidtvstudy.model.CustomListRow;
import com.songwenju.androidtvstudy.model.IconHeaderItem;
import com.songwenju.androidtvstudy.model.Movie;
import com.songwenju.androidtvstudy.recommendation.RecommendationFactory;
import com.songwenju.androidtvstudy.ui.background.PicassoBackgroundManager;
import com.songwenju.androidtvstudy.ui.presenter.CardPresenter;
import com.songwenju.androidtvstudy.ui.presenter.CustomListRowPresenter;
import com.songwenju.androidtvstudy.ui.presenter.IconHeaderItemPresenter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * songwenju on 17-3-27 : 13 : 38.
 * 邮箱：songwenju@outlook.com
 */

public class MainFragment extends BrowseFragment {

    /* Adapter and ListRows */
    private ArrayObjectAdapter mRowsAdapter;
    private CustomListRow mGridItemListRow;
    private ArrayList<CustomListRow> mVideoListRowArray;

    /* Grid row item settings */
    private static final int GRID_ITEM_WIDTH = 300;
    private static final int GRID_ITEM_HEIGHT = 200;
    private static final String GRID_STRING_ERROR_FRAGMENT = "ErrorFragment";
    private static final String GRID_STRING_VERTICAL_GRID_FRAGMENT = "VerticalGridFragment";
    private static final String GRID_STRING_GUIDED_STEP_FRAGMENT = "GuidedStepFragment";
    private static final String GRID_STRING_RECOMMENDATION = "Recommendation";
    private static final String GRID_STRING_SPINNER = "Spinner";

    private static final int VIDEO_ITEM_LOADER_ID = 1;
    private PicassoBackgroundManager mPicassoBackgroundManager;

    ArrayList<Movie> mItems = null; //MovieProvider.getMovieItems();
    private static int recommendationCounter = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        LogUtil.i(this, "MainFragment.onActivityCreated.");
        super.onActivityCreated(savedInstanceState);
        /**
         * 1.设置标题和颜色信息
         * 2.编写Adapter继承Presenter
         * 3.创建ArrayObjectAdapter
         * 4.创建HeaderItem：name,description,contentDescription
         * 5.创建GridItemPresenter和GridRowAdapter并将GridItemPresenter设置给GridRowAdapter
         * 6.给ArrayObjectAdapter添加header和GridItemAdapter
         * 7.setAdapter
         *
         */
        setupUIElements();

        /* Set up rows with light data. done in main thread. */
        loadRows();
        setRows();

        setupEventListeners();

         /* Set up rows with heavy data (data from web, content provider etc) is done in background thread using Loader */
        LoaderManager.enableDebugLogging(true);
        getLoaderManager().initLoader(VIDEO_ITEM_LOADER_ID, null, new MainFragmentLoaderCallbacks());


        mPicassoBackgroundManager = new PicassoBackgroundManager(getActivity());
        mPicassoBackgroundManager.updateBackgroundWithDelay();
    }

    private void setupEventListeners() {
        LogUtil.i(this, "MainFragment.setupEventListeners.");
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
        setOnItemViewClickedListener(new ItemClickedListener());
        // Existence of this method make In-app search icon visible
        setOnSearchClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.i(this, "MainFragment.onClick.");
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private class ItemClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
            LogUtil.i(this, "ItemClickedListener.onItemClicked.");
            // each time the item is clicked, code inside here will be executed.
            if (item instanceof Movie) {
                Movie movie = (Movie) item;
//                LogUtil.i(this,"ItemClickedListener.onItemClicked.");
                LogUtil.i(this, "ItemClickedListener.onItemClicked.Item: " + item.toString());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);

                getActivity().startActivity(intent);
            } else if (item instanceof String) {
                if (item == GRID_STRING_ERROR_FRAGMENT) {
                    Intent intent = new Intent(getActivity(), ErrorActivity.class);
                    startActivity(intent);
                } else if (item == GRID_STRING_GUIDED_STEP_FRAGMENT) {
                    Intent intent = new Intent(getActivity(), GuidedStepActivity.class);
                    startActivity(intent);
                } else if (item == GRID_STRING_VERTICAL_GRID_FRAGMENT) {
//                    Intent intent = new Intent(getActivity(), VerticalGridActivity.class);
//                    startActivity(intent);
                } else if (item == GRID_STRING_RECOMMENDATION) {
                    LogUtil.i(this, "ItemClickedListener.onItemClicked.");
                    RecommendationFactory recommendationFactory = new RecommendationFactory(getActivity().getApplicationContext());
                    Movie movie = mItems.get(recommendationCounter % mItems.size());
                    recommendationFactory.recommend(recommendationCounter, movie, NotificationCompat.PRIORITY_HIGH);
                    Toast.makeText(getActivity(), "Recommendation sent (item " + recommendationCounter + ")", Toast.LENGTH_SHORT).show();
                    recommendationCounter++;
                } else if (item == GRID_STRING_SPINNER) {
                    // Show SpinnerFragment, while backgroundtask is executed
                    new ShowSpinnerTask().execute();
                }
            }
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {

        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
            LogUtil.i(this, "ItemViewSelectedListener.onItemClicked.");
            if (item instanceof String) {
                mPicassoBackgroundManager.updateBackgroundWithDelay("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/10/RIMG0656.jpg");
            } else if (item instanceof Movie) {
                mPicassoBackgroundManager.updateBackgroundWithDelay(((Movie) item).getCardImageUrl());
            }
        }
    }

    private void setupUIElements() {
//        setBadgeDrawable(getActivity()
//                .getResources()
//                .getDrawable(R.drawable.app_icon_your_company));//设置图标

        setTitle("Hello Android TV!");  //设置title

        //HEADERS_ENABLED 显示左侧导航栏，HEADERS_DISABLED 不显示 HEADERS_HIDDEN 隐藏，到边缘按左键还能显示
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // 设置快速导航（或 headers) 背景色
        setBrandColor(getResources().getColor(R.color.fastlane_background));
        // 设置搜索的颜色
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));

        setHeaderPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object o) {
                return new IconHeaderItemPresenter();
            }
        });
    }

    private void loadRows() {
        /* GridItemPresenter */
        IconHeaderItem gridItemPresenterHeader = new IconHeaderItem(0, "GridItemPresenter", R.drawable.ic_add_white_48dp);
        GridItemPresenter gridItemPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(gridItemPresenter);
        gridRowAdapter.add(GRID_STRING_ERROR_FRAGMENT);
        gridRowAdapter.add(GRID_STRING_GUIDED_STEP_FRAGMENT);
        gridRowAdapter.add(GRID_STRING_RECOMMENDATION);
        gridRowAdapter.add(GRID_STRING_SPINNER);
        mGridItemListRow = new CustomListRow(gridItemPresenterHeader, gridRowAdapter);
    }

    /**
     * Updates UI after loading Row done.
     */
    private void setRows() {
        mRowsAdapter = new ArrayObjectAdapter(new CustomListRowPresenter()); // Initialize

        if(mVideoListRowArray != null) {
            for (CustomListRow videoListRow : mVideoListRowArray) {
                mRowsAdapter.add(videoListRow);
            }
        }
        if(mGridItemListRow != null) {
            mRowsAdapter.add(mGridItemListRow);
        }

        /* Set */
        setAdapter(mRowsAdapter);
    }



    private class MainFragmentLoaderCallbacks implements LoaderManager.LoaderCallbacks<LinkedHashMap<String, List<Movie>>> {
        @Override
        public Loader<LinkedHashMap<String, List<Movie>>> onCreateLoader(int id, Bundle args) {
            /* Create new Loader */
            LogUtil.i(this,"MainFragmentLoaderCallbacks.onCreateLoader.");
            if(id == VIDEO_ITEM_LOADER_ID) {
                LogUtil.i(this, "create VideoItemLoader");
                //return new VideoItemLoader(getActivity());
                return new VideoItemLoader(getActivity().getApplicationContext());
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<LinkedHashMap<String, List<Movie>>> loader, LinkedHashMap<String, List<Movie>> data) {
            LogUtil.i(this, "onLoadFinished");
            /* Loader data has prepared. Start updating UI here */
            switch (loader.getId()) {
                case VIDEO_ITEM_LOADER_ID:
                    LogUtil.i(this, "VideoLists UI update");

                    /* Hold data reference to use it for recommendation */
                    mItems = new ArrayList<Movie>();

                    /* loadRows: videoListRow - CardPresenter */
                    int index = 1;
                    mVideoListRowArray = new ArrayList<>();
                    CardPresenter cardPresenter = new CardPresenter();

                    if (null != data) {
                        for (Map.Entry<String, List<Movie>> entry : data.entrySet()) {
                            ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);
                            List<Movie> list = entry.getValue();

                            for (int j = 0; j < list.size(); j++) {
                                Movie movie = list.get(j);
                                cardRowAdapter.add(movie);
                                mItems.add(movie);           // Add movie reference for recommendation purpose.
                            }
                            IconHeaderItem header = new IconHeaderItem(index, entry.getKey(), R.drawable.ic_play_arrow_white_48dp);
                            index++;
                            CustomListRow videoListRow = new CustomListRow(header, cardRowAdapter);
                            videoListRow.setNumRows(1);
                            mVideoListRowArray.add(videoListRow);
                        }
                    } else {
                        LogUtil.i(this, "An error occurred fetching videos");
                    }

                    /* Set */
                    setRows();
            }
        }

        @Override
        public void onLoaderReset(Loader<LinkedHashMap<String, List<Movie>>> loader) {
            LogUtil.i(this, "onLoadReset");
            /* When it is called, Loader data is now unavailable due to some reason. */

        }

    }

    private class GridItemPresenter extends Presenter {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {

        }
    }

    private class ShowSpinnerTask extends AsyncTask<Void, Void, Void> {
        SpinnerFragment mSpinnerFragment;

        @Override
        protected void onPreExecute() {
            mSpinnerFragment = new SpinnerFragment();
            getFragmentManager().beginTransaction().add(R.id.main_browse_fragment, mSpinnerFragment).commit();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Do some background process here.
            // It just waits 5 sec in this Tutorial
            SystemClock.sleep(5000);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getFragmentManager().beginTransaction().remove(mSpinnerFragment).commit();
        }
    }
}
