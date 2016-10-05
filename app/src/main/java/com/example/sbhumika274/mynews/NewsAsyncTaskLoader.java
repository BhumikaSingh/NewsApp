package com.example.sbhumika274.mynews;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hemezh on 04/10/16.
 */

public class NewsAsyncTaskLoader extends AsyncTaskLoader<ArrayList<News>> {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private String mUrl;

    ArrayList<News> newsArray = new ArrayList<>();

    public NewsAsyncTaskLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public ArrayList<News> loadInBackground() {
        // Create URL object

        URL url = createUrl(mUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            // TODO Handle the IOException
        }

        Log.d(LOG_TAG, jsonResponse);
        // Extract relevant fields from the JSON response and create an {@link Event} object

        extractFeatureFromJson(jsonResponse);
        // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
        return newsArray;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return an {@link News} object by parsing out information
     * about the first library from the input libraryJSON string.
     */
    private void extractFeatureFromJson(String newsJSON) {
        try {

            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject responseObject = baseJsonResponse.getJSONObject("response");
            JSONArray resultsArray = responseObject.getJSONArray("results");

            // For each earthquake in the earthquakeArray, create an {@link Library} object
            for (int i = 0; i < resultsArray.length(); i++) {

                // Get a single earthquake at position i within the list of books
                JSONObject currentNews = resultsArray.getJSONObject(i);

                // key called "properties", which represents a list of all properties
                // for that library


                Date publicationDate = null;
                if (currentNews.has("webPublicationDate")) {
                    String tempDate = currentNews.getString("webPublicationDate");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    try {
                        publicationDate = format.parse(tempDate);
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                String type = "";
                if (currentNews.has("type")) {
                    type = currentNews.getString("type");
                }
                String  url = "";
                if (currentNews.has("webUrl")) {
                    url = currentNews.getString("webUrl");
                }

                String title = "";
                if (currentNews.has("webTitle")) {
                    title = currentNews.getString("webTitle");
                }
                // Create a new {@link Library} object with the price, location, time,
                // and url from the JSON response.

                News news = new News(publicationDate, type, url, title);

                // Add the new {@link Library} to the list of earthquakes.
                newsArray.add(news);

            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the library JSON results", e);
        }
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}