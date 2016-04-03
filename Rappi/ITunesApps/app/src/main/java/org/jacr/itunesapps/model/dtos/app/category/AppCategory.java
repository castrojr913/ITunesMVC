package org.jacr.itunesapps.model.dtos.app.category;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * AppCategory
 * Created by Jesus on 01/04/2016.
 */
public class AppCategory implements Serializable {

    @SerializedName("attributes")
    private AppCategoryAttribute attribute;

    public String getLabel() {
        return attribute != null ? attribute.getLabel() : "";
    }

}
