package org.jacr.itunesapps.model.manager;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jacr.itunesapps.model.ModelError;
import org.jacr.itunesapps.model.manager.listener.ApiListener;
import org.jacr.itunesapps.utilities.helpers.LogHelper;
import org.jacr.itunesapps.utilities.helpers.NetworkHelper;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ApiManager
 * Created by Jesus on 01/04/2016.
 */
abstract class ApiManager {

    //<editor-fold desc="Constants & Variables">

    protected final static Map NO_HEADERS = Collections.EMPTY_MAP;
    protected final static Map NO_PARAMETERS = Collections.EMPTY_MAP;

    protected final Gson gson = new Gson();

    //</editor-fold>

    //<editor-fold desc="Abstract Methods">

    protected abstract Class<?> getLogTag();

    protected abstract void manageResponse(String url, byte[] response, ApiListener listener);

    //</editor-fold>

    //<editor-fold desc="Response & Request Management">

    protected <L extends ApiListener> void sendGetRequest(@NonNull final String url,
                                                          @NonNull final Map<String, String> parameters,
                                                          @NonNull final Map<String, String> headers,
                                                          @NonNull final L listener) {
        if (NetworkHelper.getInstance().isConnected()) {
            LogHelper.getInstance().debugRequest(getLogTag(), "GET", url, parameters, headers);
            NetworkHelper.getInstance().get(url, parameters, headers.isEmpty() ? buildDefaultHeaders() : headers,
                    buildApiResponse(url, listener));
        } else {
            listener.onError(ModelError.CONNECTIVITY_FAILURE);
        }
    }

    private Map<String, String> buildDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

    private Callback buildApiResponse(final String url, final ApiListener listener) {
        final Class<?> logTag = getLogTag();
        return new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                ModelError error;
                String exception = e.toString();
                if (exception.contains("ConnectException") || exception.contains("UnknownHostException")) {
                    error = ModelError.CONNECTIVITY_FAILURE;
                } else if (exception.contains("SocketTimeoutException")) {
                    error = ModelError.TIMEOUT_FAILURE;
                } else {
                    error = ModelError.WEBSERVICE_FAILURE;
                }
                LogHelper.getInstance().exception(logTag, e);
                listener.onError(error); // Notify error
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String requestType = response.request().method();
                byte[] responseStream = response.body().bytes();
                if (response.isSuccessful()) { // status code: [200, 300)
                    LogHelper.getInstance().debugResponse(logTag, url, requestType, response.code(), responseStream);
                    manageResponse(url, responseStream, listener); // Delegate the concrete manager / dao the rest of the process
                } else {
                    LogHelper.getInstance().errorResponse(logTag, url, requestType, response.code(), responseStream);
                    listener.onError(ModelError.WEBSERVICE_FAILURE); // Notify error
                }
            }

        };
    }

    //</editor-fold>

}