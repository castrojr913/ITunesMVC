package org.jacr.itunesapps.model.manager.listener;

import org.jacr.itunesapps.model.ModelError;
import org.jacr.itunesapps.model.dtos.app.AppInfo;

import java.util.List;
import java.util.Set;

/**
 * BasicITunesAppsAdapter
 * Created by Jesus on 4/1/2016.
 */
public class BasicITunesAppsAdapter implements ITunesAppsListener {

    @Override
    public void onLoadApps(List<AppInfo> apps) {
        // Blank
    }

    @Override
    public void onLoadCategories(Set<String> categories) {
        // Blank
    }

    @Override
    public void onError(ModelError error) {
        // Blank
    }

}
