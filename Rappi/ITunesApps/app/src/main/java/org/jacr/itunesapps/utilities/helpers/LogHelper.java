package org.jacr.itunesapps.utilities.helpers;

import android.support.annotation.NonNull;

import java.util.Map;

import timber.log.Timber;

/**
 * LogHelper
 * Created by Jesus Castro on 30/03/2016.
 */
public class LogHelper {

    private static LogHelper singleton;

    //<editor-fold desc="Singleton">

    private LogHelper() {
        // blank
    }

    public static LogHelper getInstance() {
        if (singleton == null) {
            singleton = new LogHelper();
            Timber.plant(new Timber.DebugTree());
        }
        return singleton;
    }

    //</editor-fold">

    //<editor-fold desc="General Purpose Logging">

    public void debug(@NonNull final Class<?> sourceClass, @NonNull final String message) {
        Timber.tag(sourceClass.getSimpleName().toUpperCase()).d(message);
    }

    public void exception(@NonNull final Class<?> sourceClass, @NonNull final Throwable exception) {
        Timber.tag(sourceClass.getSimpleName().toUpperCase()).e(exception, exception.getMessage());
    }

    //</editor-fold>

    //<editor-fold desc="Http Logging">

    private String buildResponseMessage(@NonNull final String url, @NonNull final String requestType,
                                        final int statusCode, final byte[] responseBody) {
        final String separator = "_____________________";
        return String.format("\n%s " +
                "\n- URL:%s " +
                "\n- STATUS_CODE:%d " +
                "\n- BODY:'%s' \n"
                + separator, requestType, url, statusCode, responseBody != null ? new String(responseBody) : "");
    }

    private String buildRequestMessage(@NonNull final String url, @NonNull final String requestType,
                                       @NonNull final String parameters, @NonNull final String headers) {
        final String separator = "_____________________";
        return String.format("\n%s " +
                "\n- URL:%s \n" +
                "\n- PARAMETERS:%s \n" +
                "\n- HEADERS:%s \n"
                + separator, requestType, url, parameters, headers);
    }

    public void debugRequest(@NonNull final Class<?> sourceClass, @NonNull final String url,
                             @NonNull final String requestType, @NonNull final Map paramaters,
                             @NonNull final Map headers) {
        Timber.tag(sourceClass.getSimpleName().toUpperCase()).d(
                buildRequestMessage(url, requestType, paramaters != null ? paramaters.toString() : null,
                        headers != null ? headers.toString() : null));
    }

    public void debugResponse(@NonNull final Class<?> sourceClass, @NonNull final String url,
                              @NonNull final String requestType, final int statusCode, final byte[] responseBody) {
        Timber.tag(sourceClass.getSimpleName().toUpperCase()).d(buildResponseMessage(url, requestType, statusCode, responseBody));
    }

    public void errorResponse(@NonNull final Class<?> sourceClass, @NonNull final String url,
                              @NonNull final String requestType, final int statusCode, final byte[] responseBody) {
        Timber.tag(sourceClass.getSimpleName().toUpperCase()).e(buildResponseMessage(url, requestType, statusCode, responseBody));
    }

    //</editor-fold>

}