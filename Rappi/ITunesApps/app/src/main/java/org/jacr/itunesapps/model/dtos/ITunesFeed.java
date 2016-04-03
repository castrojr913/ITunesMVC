package org.jacr.itunesapps.model.dtos;

import org.jacr.itunesapps.model.dtos.app.AppInfo;

import java.util.List;

/**
 * ITunesFeed
 * Created by Jesus on 01/04/2016.
 */
public class ITunesFeed {

    private Feed feed;

    public List<AppInfo> getInfo() {
        return feed.getInfo();
    }

}
