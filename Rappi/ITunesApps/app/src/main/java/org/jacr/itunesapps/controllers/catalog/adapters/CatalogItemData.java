package org.jacr.itunesapps.controllers.catalog.adapters;

/**
 * CatalogItemData
 * Created by Jesus on 4/2/2016.
 */
public class CatalogItemData {

    //<editor-fold desc="Variables">

    private String itemName;
    private String itemAuthor;
    private String itemImageUrl;

    //</editor-fold>

    //<editor-fold desc="Constructors">

    public CatalogItemData(String itemName) {
        this.itemName = itemName;
    }

    public CatalogItemData(String itemName, String itemAuthor, String itemImageUrl) {
        this.itemName = itemName;
        this.itemAuthor = itemAuthor;
        this.itemImageUrl = itemImageUrl;
    }

    //</editor-fold>

    //<editor-fold desc="Getters">

    public String getName() {
        return itemName;
    }

    public String getAuthorName() {
        return itemAuthor;
    }

    public String getImageUrl() {
        return itemImageUrl;
    }

    //</editor-fold>

}
