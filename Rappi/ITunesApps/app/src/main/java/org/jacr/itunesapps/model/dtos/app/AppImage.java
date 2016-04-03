package org.jacr.itunesapps.model.dtos.app;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * AppImage
 * Created by Jesus on 01/04/2016.
 */
class AppImage implements Serializable {

    @SerializedName("label")
    private String url;

    public String getUrl() {
        return url;
    }

}
