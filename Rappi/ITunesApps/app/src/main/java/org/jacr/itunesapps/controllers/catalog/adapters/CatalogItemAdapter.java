package org.jacr.itunesapps.controllers.catalog.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jacr.itunesapps.R;
import org.jacr.itunesapps.utilities.helpers.StringHelper;
import org.jacr.itunesapps.utilities.helpers.ViewHelper;

import java.util.List;

/**
 * CatalogItemAdapter
 * Created by Jesus on 01/04/2016.
 */
public class CatalogItemAdapter extends RecyclerView.Adapter<CatalogItemAdapter.ViewHolder> {

    //<editor-fold desc="Variables">

    private Context context;
    private List<CatalogItemData> items;

    //</editor-fold>

    // <editor-fold desc="View Holder">

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView appNameTextView;
        TextView authorTextView;
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            appNameTextView = (TextView) view.findViewById(R.id.catalog_item_textview_app_name);
            authorTextView = (TextView) view.findViewById(R.id.catalog_item_textview_app_author);
            imageView = (ImageView) view.findViewById(R.id.catalog_item_image);
        }

    }

    //</editor-fold>

    public CatalogItemAdapter(Context context, List<CatalogItemData> items) {
        this.context = context;
        this.items = items;
    }

    //<editor-fold desc="Adapter Overrides">

    // Create new views (invoked by the layout manager)
    @Override
    public CatalogItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_catalog_grid_item, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CatalogItemData data = items.get(position);
        ViewHelper.loadPicture(context, holder.imageView, data.getImageUrl(), 20);
        holder.appNameTextView.setText(data.getName());
        if (StringHelper.isEmpty(data.getAuthorName())) {
            holder.authorTextView.setVisibility(View.GONE);
        } else {
            holder.authorTextView.setVisibility(View.VISIBLE);
            holder.authorTextView.setText(data.getAuthorName());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //</editor-fold>

}