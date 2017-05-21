package com.example.android.books;

/**
 * Created by berso on 5/17/17.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;


/**
 * Created by berso on 5/9/17.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    public static final String LOG_TAG = BooksActivity.class.getName();
    private String mUrl;

    public BookLoader(Context context, String Url) {
        super(context);
        mUrl = Url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Book> books = QueryUtils.fetchBookData(mUrl);
        return books;
    }


}
