package com.example.android.books;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    Context context;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    //RecyclerView.Adapter recyclerViewAdapter;
    BookAdapter bookAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    private String GOOGLE_BOOKS_REQUEST_URL="https://www.googleapis.com/books/v1/volumes?q=";
    private final int BOOK_LOADER_ID = 1;
    private static final int VERTICAL_ITEM_SPACE = 48;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        Intent intent = getIntent();
        String searchItem = intent.getStringExtra("SearchString");

        GOOGLE_BOOKS_REQUEST_URL = GOOGLE_BOOKS_REQUEST_URL+searchItem+"&maxResults=20";

        Log.v("BooksActivity",GOOGLE_BOOKS_REQUEST_URL);

        final List<Book> books = new ArrayList<Book>();

        setContentView(R.layout.books_activity);

        context = getApplicationContext();

        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout1);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recylerViewLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(recylerViewLayoutManager);

        bookAdapter = new BookAdapter(context, books);

        recyclerView.setAdapter(bookAdapter);

      //  recyclerView.addItemDecoration(new DividerItemDecoration(context));

            // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(BOOK_LOADER_ID, null, this);

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookLoader(this, GOOGLE_BOOKS_REQUEST_URL);
    }


    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        // Hide loading indicator because the data has been loaded
       // View loadingIndicator = findViewById(R.id.loading_indicator);
       // loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
       // mEmptyStateTextView.setText(R.string.no_earthquakes);

        // Clear the adapter of previous earthquake data

        //recyclerViewAdapter.clear();


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
