package org.jacr.itunesapps.model.dtos.app;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * AppName
 * Created by Jesus on 01/04/2016.
 */
class AppName implements Serializable {

    @SerializedName("label")
    private String name;

    public String getName() {
        return name;
    }

}
