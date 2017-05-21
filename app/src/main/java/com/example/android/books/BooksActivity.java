package com.example.android.books;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private BookAdapter bookAdapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private String googleBookRequestUrl="https://www.googleapis.com/books/v1/volumes?q=";
    private final int BOOK_LOADER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        //catch user input and fit in the Url
        Intent intent = getIntent();
        String searchItem = intent.getStringExtra("SearchString");
        googleBookRequestUrl = googleBookRequestUrl+searchItem+"&maxResults=20";

        final List<Book> books = new ArrayList<Book>();

        setContentView(R.layout.books_activity);
        //prepare the RecyclerView
        Context context = getApplicationContext();
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(context);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        TextView emptyView = (TextView) findViewById(R.id.empty_view);

        bookAdapter = new BookAdapter(context, books, emptyView);

        recyclerView.setAdapter(bookAdapter);



        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            // Hide loading indicator because the data has been loaded
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            //set text for no connection
            emptyView.setText(R.string.no_connection);
        }




    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookLoader(this, googleBookRequestUrl);
    }


    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        //Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

      //  TextView emptyView = (TextView) findViewById(R.id.empty_view);
        // Set empty state text to display "No books found."
       // emptyView.setText(R.string.no_books);


        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            bookAdapter.updateItems(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
    }
}
