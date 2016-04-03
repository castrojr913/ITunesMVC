package org.jacr.itunesapps.model.manager.listener;

import org.jacr.itunesapps.model.ModelError;

/**
 * ApiListener
 * Created by Jesus on 01/04/2016.
 */
public interface ApiListener {

    void onError(ModelError error);

}
