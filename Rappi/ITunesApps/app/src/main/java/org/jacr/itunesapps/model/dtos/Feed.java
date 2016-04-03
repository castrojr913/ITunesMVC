package org.jacr.itunesapps.model.dtos;

import org.jacr.itunesapps.model.dtos.app.AppInfo;

import java.util.List;

/**
 * Feed
 * Created by Jesus on 01/04/2016.
 */
class Feed {

    private List<AppInfo> entry;

    public List<AppInfo> getInfo() {
        return entry;
    }

}
