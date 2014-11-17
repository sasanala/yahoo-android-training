package com.example.instagramphotoviewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


public class MainActivity extends Activity {
	
	private static final String CLINET_ID = "c7a9a893bcdb4783a667e4e6229bb6fa";
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchPopularPhotos();
    }


    private void fetchPopularPhotos() {
    	photos = new ArrayList<InstagramPhoto>();
    	
    	// create adapter bind it to the data in arraylist
    	aPhotos = new InstagramPhotosAdapter(this, photos);
    	
    	// populate the data into the listview
    	ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
    	
    	// set the adapter to the listview (population of items)
    	lvPhotos.setAdapter(aPhotos);
    	
		// https://api.instagram.com/v1/media/popular?client_id=<client_id>
    	// { "data" => [x] => "images" => "standard_resolution" => "url" }
    	
		// setup popular url endpoints
    	String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLINET_ID;
    	
    	// create the network client
    	AsyncHttpClient client = new AsyncHttpClient();
    	
    	// trigger the network request
    	client.get(popularUrl, new JsonHttpResponseHandler() {
    		// define success and failure callbacks
    		// handle the successful response (popular JSON)
    		@Override
    		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
    			// fired once the successful reaponse back
    			// response is -- popular photos json
    			// we want: url, height, username, caption
    			// { "data" => [x] => "images" => "standard_resolution" => "url" }
    			// { "data" => [x] => "images" => "standard_resolution" => "height" }
    			// { "data" => [x] => "user" => "username" }
    			// { "data" => [x] => "caption" => "text" }
    			JSONArray photosJSON = null;
    			try {
    				photos.clear();
    				photosJSON = response.getJSONArray("data");
    				for (int i = 0; i < photosJSON.length(); ++i) {
    					JSONObject photoJSON = photosJSON.getJSONObject(i);
    					InstagramPhoto photo = new InstagramPhoto();
    					photo.username    = photoJSON.getJSONObject("user").getString("username");
    					if (photoJSON.getJSONObject("caption") != null) {
    						photo.caption     = photoJSON.getJSONObject("caption").getString("text");
    					}
    					photo.imageUrl    = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
    					photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
    					photo.likesCount  = photoJSON.getJSONObject("likes").getInt("count");
    					photos.add(photo);
    				}
    				
    				// notify the adapter that it should populate new change into the listview
    				aPhotos.notifyDataSetChanged();
    			} catch (JSONException e) {
    				// fire if thing fails parsing is invalid
    				e.printStackTrace();
    			}
    		}
    		
    		@Override
    		public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
    			super.onFailure(statusCode, headers, responseString, throwable);
    		}
    	});
    	
    	
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
