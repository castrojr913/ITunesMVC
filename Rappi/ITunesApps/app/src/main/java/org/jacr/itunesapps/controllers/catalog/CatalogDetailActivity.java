package org.jacr.itunesapps.controllers.catalog;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.jacr.itunesapps.GlobalSetting;
import org.jacr.itunesapps.R;
import org.jacr.itunesapps.model.dtos.app.AppInfo;
import org.jacr.itunesapps.utilities.helpers.ViewHelper;

/**
 * CatalogDetailActivity
 * Created by Jesus on 02/04/2016.
 */
@EActivity(R.layout.activity_catalog_detail)
public class CatalogDetailActivity extends AppCompatActivity {

    //<editor-fold desc="View Instances">

    @ViewById(R.id.catalog_detail_layout_header)
    protected LinearLayout headerLayout;

    @ViewById(R.id.catalog_detail_layout_body)
    protected RelativeLayout bodyLayout;

    @ViewById(R.id.catalog_detail_toolbar)
    protected Toolbar toolbar;

    @ViewById(R.id.catalog_detail_header_textview_title)
    protected TextView titleHeaderTextView;

    @ViewById(R.id.catalog_detail_header_textview_subtitle)
    protected TextView subTitleHeaderTextView;

    @ViewById(R.id.catalog_detail_header_imageview)
    protected ImageView headerImageView;

    @ViewById(R.id.catalog_detail_textview_description)
    protected TextView descriptionTextView;

    @ViewById(R.id.catalog_detail_textview_price)
    protected TextView priceTextView;

    @ViewById(R.id.catalog_detail_textview_category)
    protected TextView categoryTextView;

    @ViewById(R.id.catalog_detail_textview_release_date)
    protected TextView releaseDateTextView;

    @ViewById(R.id.catalog_detail_textview_rights)
    protected TextView rightsTextView;

    //</editor-fold>

    //<editor-fold desc="Bundle / Extra">

    @Extra("appDetail")
    protected AppInfo appInfo;

    //</editor-fold>

    @AfterViews
    void init() {
        // Setting up Toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        // --
        printData();
        animateView(GlobalSetting.CATALOG_APP_DETAIL_ANIMATION_TIME);
    }

    // <editor-fold desc="About Toolbar">

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_catalog, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // </editor-fold>

    //<editor-fold desc="Data Display">

    public void printData() {
        ViewHelper.loadPicture(this, headerImageView, appInfo.getMediumThumbnailUrl(), 60);
        titleHeaderTextView.setText(appInfo.getName());
        subTitleHeaderTextView.setText(appInfo.getAuthorName());
        descriptionTextView.setText(appInfo.getSummary());
        priceTextView.setText(appInfo.getPrice());
        categoryTextView.setText(appInfo.getCategory());
        releaseDateTextView.setText(appInfo.getReleaseDate());
        rightsTextView.setText(appInfo.getRights());
    }

    private void animateView(final int duration) {
        YoYo.with(Techniques.ZoomInRight).duration(duration).playOn(headerLayout);
        YoYo.with(Techniques.BounceInRight).duration(duration).playOn(bodyLayout);
    }

    //</editor-fold>

}