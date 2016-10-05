package com.example.sbhumika274.mynews;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hemezh on 18/09/16.
 */
public class SportsFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    private static final int EARTHQUAKE_LOADER_ID = 1;
    public static final String REQUEST_URL = "http://content.guardianapis.com/search?api-key=test&section=sport";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        View rootView = inflater.inflate(R.layout.news_list, container, false);
        return  rootView;
    }

    /**
     * Update the screen to display information from the given {@link News}.

     */
    private void updateUi(ArrayList<News> newsArray) {

        // Find a reference to the {@link ListView} in the layout
        ListView librariesListView = (ListView) getView().findViewById(R.id.list);

        // Create a new adapter that takes the list of earthquakes as input
        final NewsAdapter adapter = new NewsAdapter(getActivity(), newsArray);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        librariesListView.setAdapter(adapter);

        librariesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                News currentNews = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {

        NewsAsyncTaskLoader loader = new NewsAsyncTaskLoader(getActivity(), REQUEST_URL);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> newsArray) {
        updateUi(newsArray);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {

    }

}