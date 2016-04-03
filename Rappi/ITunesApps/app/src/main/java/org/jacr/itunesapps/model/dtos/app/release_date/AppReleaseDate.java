package org.jacr.itunesapps.model.dtos.app.release_date;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * AppReleaseDate
 * Created by Jesus on 01/04/2016.
 */
public class AppReleaseDate implements Serializable {

    @SerializedName("attributes")
    private AppReleaseDateAttribute attribute;

    public String getDate() {
        return attribute != null ? attribute.getLabel() : "";
    }

}
