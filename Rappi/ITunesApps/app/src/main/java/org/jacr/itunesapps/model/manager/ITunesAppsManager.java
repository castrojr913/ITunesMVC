package org.jacr.itunesapps.model.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;

import org.jacr.itunesapps.RootApplication;
import org.jacr.itunesapps.model.ApiUrl;
import org.jacr.itunesapps.model.ModelError;
import org.jacr.itunesapps.model.dtos.ITunesFeed;
import org.jacr.itunesapps.model.dtos.app.AppInfo;
import org.jacr.itunesapps.model.manager.listener.ApiListener;
import org.jacr.itunesapps.model.manager.listener.ITunesAppsListener;
import org.jacr.itunesapps.utilities.helpers.LogHelper;
import org.jacr.itunesapps.utilities.helpers.StringHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ITunesAppsManager
 * Created by Jesus on 01/04/2016.
 */
public class ITunesAppsManager extends ApiManager {

    //<editor-fold desc="Constants & Variables"

    private static final String PREFERENCE_NAME = "ITunesAppsPreferences";
    private static final String PREFERENCE_NAME_KEY_APP_INFO = "appInfo";

    private static ITunesAppsManager singleton;

    //</editor-fold>

    //<editor-fold desc="Singleton">

    private ITunesAppsManager() {
        // Blank
    }

    public static ITunesAppsManager getInstance() {
        if (singleton == null) {
            singleton = new ITunesAppsManager();
        }
        return singleton;
    }

    //</editor-fold>

    //<editor-fold desc="Manager Overrides">

    @Override
    protected Class<?> getLogTag() {
        return ITunesAppsManager.class;
    }

    @Override
    protected void manageResponse(@NonNull String url, byte[] response, @NonNull ApiListener listener) {
        try {
            if (url.equals(ApiUrl.BASE) && listener instanceof ITunesAppsListener) {
                ITunesFeed feed = gson.fromJson(new String(response), ITunesFeed.class);
                serializeAppsList(feed.getInfo());
                ((ITunesAppsListener) listener).onLoadCategories(fetchCategories(feed.getInfo()));
            } else {
                listener.onError(ModelError.WEBSERVICE_FAILURE);
            }
        } catch (Exception e) {
            LogHelper.getInstance().exception(getLogTag(), e);
            listener.onError(ModelError.WEBSERVICE_FAILURE);
        }
    }

    //</editor-fold>

    //<editor-fold desc="Http Requests">

    public void getCategories(boolean shouldUpdate, @NonNull ITunesAppsListener listener) {
        List<AppInfo> dtos = getCachedAppsList();
        if (dtos != null && !dtos.isEmpty() && !shouldUpdate) {
            listener.onLoadCategories(fetchCategories(dtos));
        } else {
            sendGetRequest(ApiUrl.BASE, NO_HEADERS, NO_PARAMETERS, listener);
        }
    }

    private Set<String> fetchCategories(@NonNull List<AppInfo> dtos) {
        Set<String> categories = new HashSet<>();
        for (AppInfo dto : dtos) {
            categories.add(dto.getCategory());
        }
        return categories;
    }

    public void getAppsXCategory(@NonNull String category, @NonNull ITunesAppsListener listener) {
        List<AppInfo> dtos = getCachedAppsList();
        List<AppInfo> result = new ArrayList<>();
        for (AppInfo dto : dtos) {
            if (dto.getCategory().equals(category)) {
                result.add(dto);
            }
        }
        listener.onLoadApps(result);
    }

    //</editor-fold>

    //<editor-fold desc="Data Cache">

    private List<AppInfo> getCachedAppsList() {
        String serializedModel = getPreferences().getString(PREFERENCE_NAME_KEY_APP_INFO, null);
        if (StringHelper.isEmpty(serializedModel)) {
            return Collections.EMPTY_LIST;
        } else {
            Type type = new TypeToken<List<AppInfo>>() {
            }.getType();
            return gson.fromJson(serializedModel, type);
        }
    }

    private void serializeAppsList(@NonNull List<AppInfo> appInfo) {
        String serializedModel = gson.toJson(appInfo);
        LogHelper.getInstance().debug(getLogTag(), serializedModel);
        SharedPreferences.Editor preferencesEditor = getPreferences().edit();
        preferencesEditor.putString(PREFERENCE_NAME_KEY_APP_INFO, serializedModel);
        preferencesEditor.apply();
    }

    public void clearCache() {
        SharedPreferences.Editor preferencesEditor = getPreferences().edit();
        preferencesEditor.clear();
        preferencesEditor.apply();
    }

    private SharedPreferences getPreferences() {
        return RootApplication.getInstance().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    //</editor-fold>

}