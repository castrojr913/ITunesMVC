package org.jacr.itunesapps.model.dtos.app.price;

import java.io.Serializable;

/**
 * AppPriceAttribute
 * Created by Jesus on 01/04/2016.
 */
class AppPriceAttribute implements Serializable {

    private String amount;
    private String currency;

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

}
