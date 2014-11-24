package com.codepath.gridimagesearch.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.codepath.gridimagesearch.adapters.ImageResultsAdapter;
import com.codepath.gridimagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


public class SearchActivity extends Activity {
	private EditText etQuery;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupView();
        
        // create the data source
        imageResults = new ArrayList<ImageResult>();
        
        // attach the data source to an adapter
        aImageResults = new  ImageResultsAdapter(this, imageResults);
        
        // link the adapter to the adapterview (gridview)
        gvResults.setAdapter(aImageResults);
    }


    private void setupView() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			// launch the image display activity
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				// create an intent
				Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
				
				// get the image result to display
				ImageResult result = imageResults.get(position);
				
				// pass the image result into the intent
				// `result` needs to be either serializable or parcelable
				i.putExtra("result", result);
				
				// launch the new activity
				startActivity(i);
			}
		});
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }
    
    // Fire whenever the button is pressed (android:onclick property)
    public void onImageSearch(View v) {
    	String query = etQuery.getText().toString();
    	
    	Toast.makeText(this, "Search for " + query, Toast.LENGTH_SHORT).show();
    	
        AsyncHttpClient client = new AsyncHttpClient();
        // https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=andrioid&rsz=8
        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";
        client.get(searchUrl, new JsonHttpResponseHandler() {
        	@Override
        	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        		Log.d("DBUG", response.toString());
        		
        		JSONArray imageResultsJson = null;
        		try {
					imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
					imageResults.clear(); // clear the existing images from the array (in case where its a new search)
					
					// when you make to the adapter, it does modify the underlying data
					imageResults.addAll(ImageResult.fromJSONArray(imageResultsJson)); 
					
					aImageResults.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
        		Log.d("INFO", imageResults.toString());
        	}
        });
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
