package com.example.sbhumika274.mynews;

import java.util.Date;

/**
 * {@link News}represents vocabulary words that  the user wants to learn.
 * It contains a default  translation and a MyMusic translation  for that words
 *
 *
 *
 */
public class News {

    private Date mPublicationDate;

    private String mType;

    private String mTitle;
    private String mUrl;


    public  News(Date publicationDate, String type , String url, String title ) {
        mPublicationDate = publicationDate;
        mType = type;
        mUrl = url;
        mTitle = title;
    }

    public Date getPublicationDate() {
        return mPublicationDate;
    }

    public String getType() {
        return mType;
    }
    public String getUrl(){
        return mUrl;
    }

    public String getTitle() {
        return mTitle;
    }

}