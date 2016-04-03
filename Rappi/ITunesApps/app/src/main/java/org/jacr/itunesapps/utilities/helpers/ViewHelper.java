package org.jacr.itunesapps.utilities.helpers;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.jacr.itunesapps.R;
import org.jacr.itunesapps.utilities.helpers.picasso.PictureRoundedTransformation;

/**
 * ViewHelper
 * Created by Jesus Castro on 30/03/2016.
 */
public class ViewHelper {

    private static final int TABLET_DENSITY_THRESHOLD = 600; // dp

    //<editor-fold desc="Methods">

    public static void loadPicture(Context context, ImageView imageView, String imageUrl,
                                   int cornerRadius) {
        // Picasso supports cache and image processing, e.g, rounding its corners
        RequestCreator loader = StringHelper.isEmpty(imageUrl) ?
                Picasso.with(context).load(R.mipmap.ic_launcher) : Picasso.with(context).load(imageUrl);
        loader.error(android.R.drawable.ic_dialog_alert)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .transform(new PictureRoundedTransformation(cornerRadius, 0))
                .fit()
                .into(imageView);
    }

    public static void showMessage(View view, String message) {
        // Workaround: It's required to set an instance anonymous from View.OnClickListener in order to show
        // the button so that user can close the message. However, we don't need to process anything
        // inside onClick method
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    public static boolean isTablet(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int[] dps = {
                (int) (metrics.widthPixels * 160f / metrics.densityDpi),
                (int) (metrics.heightPixels * 160f / metrics.densityDpi)
        };
        // If a device width >= 600dp may be considered as a Tablet
        return dps[0] >= TABLET_DENSITY_THRESHOLD;
    }

    public static TextView getToolbarTextView(Toolbar toolbar) {
        TextView toolbarTextView = null;
        for (int i = 0; i < toolbar.getChildCount(); ++i) {
            View child = toolbar.getChildAt(i);
            if (child instanceof TextView) {
                toolbarTextView = (TextView) child;
                break;
            }
        }
        return toolbarTextView;
    }

    //</editor-fold>

}