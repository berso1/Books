package com.example.android.books;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by berso on 5/16/17.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the search button
        Button my_search_button = (Button) findViewById(R.id.search_button);

        final EditText editText = (EditText) findViewById(R.id.search_src_text);

        // Set a click listener on that View
        my_search_button.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the BooksActivity
                Intent myBookIntent = new Intent(MainActivity.this, BooksActivity.class);
                myBookIntent.putExtra ( "TextBox", editText.getText().toString() );
                startActivity(myBookIntent);
            }
        });
    }
}
