package com.example.sbhumika274.mynews;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hemezh on 02/08/16.
 */
public class NewsAdapter extends ArrayAdapter<com.example.sbhumika274.mynews.News> {
    /**Resources ID for the background color of this list of words**/

    private Context context;

    public NewsAdapter(Activity context, List<com.example.sbhumika274.mynews.News> news) {
        super(context, 0, news);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        com.example.sbhumika274.mynews.News currentNews = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.MyNews_text_view);
        nameTextView.setText(currentNews.getTitle());

        //Set the color theme of the list item
        View textContainer = listItemView.findViewById(R.id.text_container);

        return listItemView;

    }
}