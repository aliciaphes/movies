package com.example.android.movies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.movies.R;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Utilities {

    // poster size
    // it can be "w92", "w154", "w185", "w342", "w500", "w780", or "original"
    public final static String POSTER_SIZE = "w342";

    public final static String POSTER_BASE_URL = "https://image.tmdb.org/t/p";

    public final static String TOP_RATED = "top_rated";
    public final static String MOST_POPULAR = "popular";

    private final static String DEFAULT_LANGUAGE = "en-US";

    // the API returns dates in the form yyyy-mm-dd
    private final static String DATE_FORMAT = "yyyy-mm-dd";

    public static String formatDate(String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date;
        String dateString = null;
        try {
            date = dateFormat.parse(stringDate);
            dateFormat.applyPattern("EEEE, d MMM yyyy");
            dateString = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static String getLanguage() {
        return DEFAULT_LANGUAGE;
    }

    public static String getAPIkey(Context context) {
        return context.getString(R.string.api_key);
    }

    public static String getDefaultCriterion() {
        return MOST_POPULAR;
    }

    public static String getCriterionLabel(String criterion, Context context) {
        String label = "";
        switch (criterion){
            case TOP_RATED:
                label = context.getString(R.string.top_rated);
                break;
            case MOST_POPULAR:
                label = context.getString(R.string.most_popular);
                break;
        }
        return label;
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

}
