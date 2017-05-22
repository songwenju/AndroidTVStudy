package com.songwenju.androidtvstudy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.songwenju.androidtools.util.LogUtil;

import java.util.ArrayList;

/**
 * songwenju on 17-3-27 : 13 : 38.
 * 邮箱：songwenju@outlook.com
 */

public class MainFragment extends BrowseFragment {

    private ArrayObjectAdapter mRowsAdapter;
    private static final int GRID_ITEM_WIDTH = 300;
    private static final int GRID_ITEM_HEIGHT = 200;
    private SimpleBackgroundManager mSimpleBackgroundManager;
    private PicassoBackgroundManager mPicassoBackgroundManager;

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

        loadRows();
        setupEventListeners();

//        mSimpleBackgroundManager = new SimpleBackgroundManager(getActivity());
        mPicassoBackgroundManager = new PicassoBackgroundManager(getActivity());

    }

    private void setupEventListeners() {
        setOnItemViewClickedListener(new ItemViewSelectedListener());
        setOnItemViewClickedListener(new ItemClickedListener());
    }

    private class ItemClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
//            LogUtil.i(this,"ItemClickedListener.onItemClicked.");
//            if (item instanceof Movie) {
//                Movie movie = (Movie) item;
//                LogUtil.d(this,"ItemClickedListener.onItemClicked.movie:"+movie);
//                Intent intent = new Intent(getActivity(), DetailsActivity.class);
//                intent.putExtra(DetailsActivity.MOVIE, movie);
//
//                getActivity().startActivity(intent);
//            }
            // each time the item is clicked, code inside here will be executed.
            if (item instanceof Movie) {
                Movie movie = (Movie) item;
//                LogUtil.i(this,"ItemClickedListener.onItemClicked.");
                LogUtil.i(this,"ItemClickedListener.onItemClicked.Item: " + item.toString());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);

                getActivity().startActivity(intent);
            } else if (item instanceof String){
                if (item == "ErrorFragment") {
                    Intent intent = new Intent(getActivity(), ErrorActivity.class);
                    startActivity(intent);
                } else if (item == "GuidedStepFragment") {
                    Intent intent = new Intent(getActivity(), GuidedStepActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
            LogUtil.i(this, "ItemViewSelectedListener.onItemClicked.");
            if (item instanceof String) {
                mPicassoBackgroundManager.updatBackgroundWithDelay("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/10/RIMG0656.jpg");
            } else if (item instanceof Movie) {
                mPicassoBackgroundManager.updatBackgroundWithDelay(((Movie) item).getCardImageUrl());
            }
        }
    }

    private void loadRows() {
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        /* GridItemPresenter */
        HeaderItem gridItemPresenterHeader = new HeaderItem(0, "GridItemPresenter");

        GridItemPresenter gridItemPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(gridItemPresenter);
        gridRowAdapter.add("ErrorFragment");
        gridRowAdapter.add("GuidedStepFragment");
        gridRowAdapter.add("ITEM 3");

        mRowsAdapter.add(new ListRow(gridItemPresenterHeader, gridRowAdapter));

        /* CardPresenter */
        HeaderItem cardPresenterHeader = new HeaderItem(1, "CardPresenter");
        CardPresenter cardPresenter = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);
//
//        for (int i = 0; i < 10; i++) {
//            Movie movie = new Movie();
//            if (i % 3 == 0) {
//                movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02580.jpg");
//            } else if (i % 3 == 1) {
//                movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02630.jpg");
//            } else {
//                movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02529.jpg");
//            }
//            movie.setTitle("title" + i);
//            movie.setStudio("studio" + i);
//            String description = "Lorem ipsum dolor sit amet, qui mundi vivendum cu. Mazim dicant possit te his. Quo solet dicant prodesset eu, pri deseruisse concludaturque ea, saepe maiorum sea et. Impetus discere sed at. Vim eu novum erant integre, te tale voluptatibus est. Facer labores te mel.\n" +
//                    "\n" +
//                    "Dictas denique qualisque mea id, cu mei verear fabellas. Mel no autem nusquam, viderer oblique te mei. At minimum corpora consulatu vim. Cibo nominavi vis no, in verterem vulputate eos, essent iriure cu vel. Ius ferri expetendis ad, omnes aeterno nominati id his, eum debitis lobortis comprehensam id.\n" +
//                    "\n" +
//                    "Illud dicit nostrud sit no. Eu quod nostro pro. Ut gubergren mnesarchum has, nostro detracto scriptorem et quo, no illud phaedrum recteque sea. Ad his summo probatus recusabo. Qui amet tale viris et, ei his quodsi torquatos adipiscing. Laudem malorum no eum, accusam mandamus sit ex, est ut tractatos dissentiet. Dictas feugiat usu et, an his cibo appareat placerat, eu quis dignissim qui.\n" +
//                    "\n" +
//                    "Euripidis neglegentur eu per, denique singulis vel cu, malis dolore ne duo. Cum no iracundia persecuti expetendis. Vim alii dolore malorum at, veniam perfecto salutandi cu nec, vix ad nonumes consulatu scripserit. At sit nonumy dolores aliquando, eu nam sumo legere. Eu maiorum adipisci torquatos his, vidit appareat eos no.\n" +
//                    "\n" +
//                    "Solet laboramus no quo, cu aperiam inermis vix. Eum animal graecis id, ne quodsi abhorreant sit. Tale persequeris te qui. Labitur invenire explicari in vix."
//                    + "Lorem ipsum dolor sit amet, qui mundi vivendum cu. Mazim dicant possit te his. Quo solet dicant prodesset eu, pri deseruisse concludaturque ea, saepe maiorum sea et. Impetus discere sed at. Vim eu novum erant integre, te tale voluptatibus est. Facer labores te mel.\n" +
//                    "\n" +
//                    "Dictas denique qualisque mea id, cu mei verear fabellas. Mel no autem nusquam, viderer oblique te mei. At minimum corpora consulatu vim. Cibo nominavi vis no, in verterem vulputate eos, essent iriure cu vel. Ius ferri expetendis ad, omnes aeterno nominati id his, eum debitis lobortis comprehensam id.\n" +
//                    "\n" +
//                    "Illud dicit nostrud sit no. Eu quod nostro pro. Ut gubergren mnesarchum has, nostro detracto scriptorem et quo, no illud phaedrum recteque sea. Ad his summo probatus recusabo. Qui amet tale viris et, ei his quodsi torquatos adipiscing. Laudem malorum no eum, accusam mandamus sit ex, est ut tractatos dissentiet. Dictas feugiat usu et, an his cibo appareat placerat, eu quis dignissim qui.\n" +
//                    "\n" +
//                    "Euripidis neglegentur eu per, denique singulis vel cu, malis dolore ne duo. Cum no iracundia persecuti expetendis. Vim alii dolore malorum at, veniam perfecto salutandi cu nec, vix ad nonumes consulatu scripserit. At sit nonumy dolores aliquando, eu nam sumo legere. Eu maiorum adipisci torquatos his, vidit appareat eos no.\n" +
//                    "\n" +
//                    "Solet laboramus no quo, cu aperiam inermis vix. Eum animal graecis id, ne quodsi abhorreant sit. Tale persequeris te qui. Labitur invenire explicari in vix.";
//            movie.setDescription(description);
//            cardRowAdapter.add(movie);
//        }

        ArrayList<Movie> mItems = MovieProvider.getMovieItems();
        for (Movie movie : mItems) {
            cardRowAdapter.add(movie);
        }
        mRowsAdapter.add(new ListRow(cardPresenterHeader, cardRowAdapter));
        /* set */
        setAdapter(mRowsAdapter);

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


}
