package org.jacr.itunesapps.model.dtos.app;

import com.google.gson.annotations.SerializedName;

import org.jacr.itunesapps.model.dtos.app.category.AppCategory;
import org.jacr.itunesapps.model.dtos.app.price.AppPrice;
import org.jacr.itunesapps.model.dtos.app.release_date.AppReleaseDate;

import java.io.Serializable;
import java.util.List;

/**
 * AppInfo
 * Created by Jesus on 01/04/2016.
 */
public class AppInfo implements Serializable {

    // <editor-fold desc="Variables">

    private AppSummary summary;
    private AppRights rights;
    private AppCategory category;

    @SerializedName("title")
    private AppTitle appTitle;

    @SerializedName("im:name")
    private AppName appName;

    @SerializedName("im:image")
    private List<AppImage> images;

    @SerializedName("im:artist")
    private AppArtist artist;

    @SerializedName("im:price")
    private AppPrice price;

    @SerializedName("im:releaseDate")
    private AppReleaseDate releaseDate;

    // </editor-fold>

    // <editor-fold desc="Getters">

    public String getName() {
        return appName != null ? appName.getName() : "";
    }

    public String getAuthorName() {
        return artist != null ? artist.getLabel() : "";
    }

    public String getTitle() {
        return appTitle != null ? appTitle.getLabel() : "";
    }

    public String getSmallThumbnailUrl() {
        // Api always returns 3 images: 53px, 75px and 100px
        return images != null && !images.isEmpty() && images.size() == 3 ? images.get(0).getUrl() : "";
    }

    public String getMediumThumbnailUrl() {
        return images != null && !images.isEmpty() && images.size() == 3 ? images.get(1).getUrl() : "";
    }

    public String getBigThumbnailUrl() {
        return images != null && !images.isEmpty() && images.size() == 3 ? images.get(2).getUrl() : "";
    }

    public String getSummary() {
        return summary != null ? summary.getLabel() : "";
    }

    public String getPrice() {
        return price != null ? (price.getCurrency() + " " + price.getAmount()) : "";
    }

    public String getRights() {
        return rights != null ? rights.getLabel() : "";
    }

    public String getCategory() {
        return category != null ? category.getLabel() : "";
    }

    public String getReleaseDate() {
        return releaseDate != null ? releaseDate.getDate() : "";
    }

    //</editor-fold>

}
