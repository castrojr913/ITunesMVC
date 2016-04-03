package org.jacr.itunesapps.controllers.splash;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.jacr.itunesapps.GlobalSetting;
import org.jacr.itunesapps.R;
import org.jacr.itunesapps.controllers.catalog.CatalogActivity_;
import org.jacr.itunesapps.utilities.views.listener.BasicAnimationAdapter;

/**
 * SplashActivity
 * Created by Jesus on 30/03/2016.
 */
@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {

    //<editor-fold desc="View Instances">

    @ViewById(R.id.splash_textview)
    protected TextView splashTextView;

    @ViewById(R.id.splash_background)
    protected RelativeLayout mainLayout;

    //</editor-fold>

    @ColorRes(R.color.app_theme_primary_dark)
    protected int animationBackgroundColor;

    @AfterViews
    protected void init() {
        startFirstAnimation(GlobalSetting.SPLASH_ANIMATION_TIME / 2);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                CatalogActivity_.intent(SplashActivity.this).start();
                finish();
            }

        }, GlobalSetting.SPLASH_TIME);
    }

    //<editor-fold desc="Animation">

    private void startFirstAnimation(final int time) {
        YoYo.with(Techniques.RollIn).duration(time)
                .withListener(new BasicAnimationAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        startSecondAnimation(GlobalSetting.SPLASH_ANIMATION_TIME / 2);
                    }
                }).playOn(splashTextView);
    }

    @UiThread
    protected void startSecondAnimation(int time) {
        mainLayout.setBackgroundColor(animationBackgroundColor);
        YoYo.with(Techniques.ZoomOutRight).duration(time).playOn(splashTextView);
    }

    //</editor-fold>

}