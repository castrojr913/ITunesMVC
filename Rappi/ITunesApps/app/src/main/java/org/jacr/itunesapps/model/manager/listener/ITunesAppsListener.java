package org.jacr.itunesapps.model.manager.listener;

import org.jacr.itunesapps.model.dtos.app.AppInfo;

import java.util.List;
import java.util.Set;

/**
 * ITunesAppsListener
 * Created by Jesus on 01/04/2016.
 */
public interface ITunesAppsListener extends ApiListener {

    void onLoadApps(List<AppInfo> apps);

    void onLoadCategories(Set<String> categories);

}
