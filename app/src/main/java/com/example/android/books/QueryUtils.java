package com.example.android.books;

/**
 * Created by berso on 5/16/17.
 */

import android.text.TextUtils;
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
import java.util.ArrayList;
import java.util.List;



/**
 * Created by berso on 5/14/17.
 */

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    public static final String LOG_TAG = BooksActivity.class.getName();

    private QueryUtils() {
    }


    /**
     * Query the Google books dataset and return an {@link Book} object to represent a single earthquake.
     */


    public static List<Book> fetchEarthquakeData(String requestUrl) {


        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<Book> books = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return books;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if( url == null ){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if( urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.v(LOG_TAG,""+urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG+" Invalid Url",e.toString());
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
     * Returns new URL object from the given string URL.
     */

    private static URL createUrl(String stringUrl) {
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
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
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
     * Return an {book} object by parsing out information
     * about the first earthquake from the input earthquakeJSON string.
     */
    private static List<Book> extractFeatureFromJson(String bookJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }
        List<Book> books = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(bookJSON);
            JSONArray featureArray = baseJsonResponse.getJSONArray("items");

            for( int i = 0; i < featureArray.length(); i++){
                JSONObject book = featureArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                String title = "";
                String subtitle = "";
                String authors = " Unknow";
                String description = "";

                if(volumeInfo.has("title")){
                    title = book.getJSONObject("volumeInfo").getString("title");
                }
                if(volumeInfo.has("subtitle")) {
                    subtitle = volumeInfo.getString("subtitle");
                }
                if(volumeInfo.has("authors")) {
                    JSONArray jSauthors = book.getJSONObject("volumeInfo").getJSONArray("authors");
                    authors = getAutors(jSauthors);
                }    
                if(volumeInfo.has("description")){
                    description = book.getJSONObject("volumeInfo").getString("description");
                }

               // Log.v(LOG_TAG + "title,subt,authors,des",title+","+subtitle+","+authors+","+description);

                books.add(new Book(title,subtitle,authors,description));
            }
            return books;
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils....", "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }


    private static String getAutors(JSONArray jSauthors) {

        String[] authors = null;
        if (jSauthors != null) {
            int length = jSauthors.length();
            authors = new String[length];
            for (int i = 0; i < length; i++) {
                authors[i] = jSauthors.optString(i);
            }
        }

        String result = "";
        if (authors.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String s : authors) {
                sb.append(s).append(",");
            }
            int i = sb.length();
            result = sb.deleteCharAt(sb.length() - 1).toString();
        }
        return result;
    }

}
