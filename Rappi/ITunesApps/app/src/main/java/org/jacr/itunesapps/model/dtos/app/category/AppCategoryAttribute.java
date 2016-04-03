package org.jacr.itunesapps.model.dtos.app.category;

import java.io.Serializable;

/**
 * AppCategoryAttribute
 * Created by Jesus on 01/04/2016.
 */
class AppCategoryAttribute implements Serializable {

    //<editor-fold desc="Variables">

    private String id;
    private String term;
    private String scheme;
    private String label;

    //</editor-fold>

    //<editor-fold desc="Getters">

    public String getId() {
        return id;
    }

    public String getTerm() {
        return term;
    }

    public String getScheme() {
        return scheme;
    }

    public String getLabel() {
        return label;
    }

    //</editor-fold>

}
