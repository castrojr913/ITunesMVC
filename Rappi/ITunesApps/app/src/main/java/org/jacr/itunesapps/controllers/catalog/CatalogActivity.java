package org.jacr.itunesapps.controllers.catalog;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringRes;
import org.jacr.itunesapps.GlobalSetting;
import org.jacr.itunesapps.R;
import org.jacr.itunesapps.controllers.catalog.adapters.CatalogItemAdapter;
import org.jacr.itunesapps.controllers.catalog.adapters.CatalogItemData;
import org.jacr.itunesapps.model.ModelError;
import org.jacr.itunesapps.model.dtos.app.AppInfo;
import org.jacr.itunesapps.model.manager.ITunesAppsManager;
import org.jacr.itunesapps.model.manager.listener.BasicITunesAppsAdapter;
import org.jacr.itunesapps.utilities.helpers.ViewHelper;
import org.jacr.itunesapps.utilities.views.ScrollableSwipeRefreshLayout;
import org.jacr.itunesapps.utilities.views.listener.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

/**
 * CatalogActivity
 * Created by Jesus on 30/03/2016.
 */
@EActivity(R.layout.activity_catalog)
public class CatalogActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener.OnItemClickListener {

    //<editor-fold desc="Variables & Constants">

    private static final boolean MODE_ONLINE = true;
    private static final boolean MODE_OFFLINE = false;
    private static final boolean TOOLBAR_ICON_RETURN = true;
    private static final boolean TOOLBAR_ICON_NONE = false;

    private String[] categories;
    private List<AppInfo> apps;
    private CatalogItemAdapter gridAdapter;
    private boolean isShownCategoryList;

    //</editor-fold>

    // <editor-fold desc="View Instances">

    @ViewById(android.R.id.content)
    protected View rootView;

    @ViewById(R.id.catalog_layout_refresh)
    protected ScrollableSwipeRefreshLayout swipeToRefreshLayout;

    @ViewById(R.id.catalog_progressbar)
    protected View progressBarView;

    @ViewById(R.id.catalog_recyclerview_items)
    protected RecyclerView gridView;

    @ViewById(R.id.catalog_view_no_items)
    protected View noItemsView;

    @ViewById(R.id.catalog_toolbar)
    protected Toolbar toolbar;

    // </editor-fold>

    //<editor-fold desc="String Resources">

    @StringRes(R.string.loading_wait)
    protected String progressBarText;

    @StringRes(R.string.error_connectivity)
    protected String errorConnectivity;

    @StringRes(R.string.error_timeout)
    protected String errorTimeout;

    @StringRes(R.string.error_webservice)
    protected String errorWebservice;

    @StringRes(R.string.catalog_title)
    protected String defaultToolbarTitle;

    //</editor-fold>

    //<editor-fold desc="Color Resources">

    @ColorRes(R.color.app_refresh_color_1)
    protected int firstRefreshColor;

    @ColorRes(R.color.app_refresh_color_2)
    protected int secondRefreshColor;

    //</editor-fold>

    @AfterViews
    protected void init() {
        // Toolbar
        setSupportActionBar(toolbar);
        // Refresh Indicator
        swipeToRefreshLayout.setOnRefreshListener(this);
        swipeToRefreshLayout.setColorSchemeColors(firstRefreshColor, secondRefreshColor);
        swipeToRefreshLayout.setRecyclerView(gridView);
        // Progress Bar
        TextView progressBarTextView = (TextView) progressBarView.findViewById(R.id.progressbar_text);
        progressBarTextView.setText(progressBarText);
        // Grid
        gridView.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
        gridView.setLayoutManager(ViewHelper.isTablet(this) ?
                new GridLayoutManager(this, GlobalSetting.GATALOG_GRID_COLUMNS) : new LinearLayoutManager(this));
        // Fetch ITunes categories for Apps from cache if there are available data
        showProgressBar();
        loadCategories(MODE_OFFLINE);
        animateToolbar();
    }

    //<editor-fold desc="About Toolbar">

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // It's required to show button on Toolbar
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* When user clicks on some Toolbar button  */
        switch (item.getItemId()) {
            case android.R.id.home:
                loadCategories(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void animateToolbar() {
        YoYo.with(Techniques.Shake).duration(GlobalSetting.CATALOG_ANIMATION_TIME)
                .playOn(ViewHelper.getToolbarTextView(toolbar));
    }

    //</editor-fold>

    //<editor-fold desc="GUI Events Handling">

    @Override
    public void onItemClick(int position) {
        if (isShownCategoryList) {
            loadAppsXCategory(categories[position]);
        } else {
            CatalogDetailActivity_.intent(this).extra("appDetail", apps.get(position)).start();
        }
    }

    @Override
    public void onRefresh() {
        loadCategories(MODE_ONLINE);
    }

    @Override
    public void onBackPressed() {
        if (isShownCategoryList) {
            finish();
        } else {
            loadCategories(false);
        }
    }

    //</editor-fold>

    private void loadCategories(boolean shouldUpdate) {
        setToolbarData(TOOLBAR_ICON_NONE, defaultToolbarTitle);
        ITunesAppsManager.getInstance().getCategories(shouldUpdate, new BasicITunesAppsAdapter() {

            @Override
            public void onLoadCategories(@NonNull Set<String> categories) {
                holdCategoriesInfo(categories);
                List<CatalogItemData> items = new ArrayList<>();
                for (String category : categories) {
                    items.add(new CatalogItemData(category, "", ""));
                }
                displayDataOnGrid(items);
            }

            @Override
            public void onError(@NonNull ModelError error) {
                displayError(error);
            }

        });
    }

    private void loadAppsXCategory(@NonNull String category) {
        setToolbarData(TOOLBAR_ICON_RETURN, category);
        ITunesAppsManager.getInstance().getAppsXCategory(category, new BasicITunesAppsAdapter() {

            @Override
            public void onLoadApps(@NonNull List<AppInfo> apps) {
                holdAppsInfo(apps);
                List<CatalogItemData> items = new ArrayList<>();
                for (AppInfo app : apps) {
                    items.add(new CatalogItemData(app.getName(), app.getAuthorName(), app.getSmallThumbnailUrl()));
                }
                displayDataOnGrid(items);
            }

        });
    }

    //<editor-fold desc="Error & Data Display">

    @UiThread
    protected void displayDataOnGrid(@NonNull List<CatalogItemData> data) {
        hideIndicators();
        if (data.isEmpty()) {
            showNoItemsView();
        } else {
            showItemsView();
            if (gridAdapter != null) {
                for (int i = 0; i < gridAdapter.getItemCount(); i++) {
                    gridAdapter.notifyItemRemoved(i);
                }
            }
            gridAdapter = new CatalogItemAdapter(this, data);
            // Add animation
            gridView.setItemAnimator(new FadeInAnimator());
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(gridAdapter);
            ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
            // --
            gridView.setAdapter(scaleAdapter);
        }
    }

    @UiThread
    protected void displayError(@NonNull ModelError error) {
        hideIndicators();
        String message = error == ModelError.CONNECTIVITY_FAILURE ? errorConnectivity
                : error == ModelError.TIMEOUT_FAILURE ? errorTimeout : errorWebservice;
        ViewHelper.showMessage(rootView, message);
    }

    protected void showProgressBar() {
        progressBarView.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.GONE);
    }

    protected void hideProgressBar() {
        progressBarView.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
    }

    protected void hideIndicators() {
        hideProgressBar();
        swipeToRefreshLayout.setRefreshing(false);
    }

    protected void showItemsView() {
        gridView.setVisibility(View.VISIBLE);
        noItemsView.setVisibility(View.GONE);
    }

    protected void showNoItemsView() {
        gridView.setVisibility(View.GONE);
        noItemsView.setVisibility(View.VISIBLE);
    }

    // </editor-fold>

    //<editor-fold desc="Others">

    private void setToolbarData(boolean shouldShowHomeIcon, @NonNull String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(shouldShowHomeIcon);
        }
        toolbar.setTitle(title);
    }

    private void holdCategoriesInfo(@NonNull Set<String> categories) {
        isShownCategoryList = true;
        this.categories = null;
        this.categories = new String[categories.size()];
        this.categories = categories.toArray(this.categories);
    }

    private void holdAppsInfo(@NonNull List<AppInfo> apps) {
        isShownCategoryList = false;
        if (this.apps == null) {
            this.apps = new ArrayList<>();
        }
        this.apps.clear();
        this.apps.addAll(apps);
    }

    //</editor-fold>

}