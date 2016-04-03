package org.jacr.itunesapps.model.dtos.app.price;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * AppPrice
 * Created by Jesus on 01/04/2016.
 */
public class AppPrice implements Serializable {

    @SerializedName("attributes")
    private AppPriceAttribute attribute;

    public String getAmount() {
        return attribute != null ? attribute.getAmount() : "";
    }

    public String getCurrency() {
        return attribute != null ? attribute.getCurrency() : "";
    }

}
