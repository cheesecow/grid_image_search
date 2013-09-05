package com.bootcamp.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	public static final String SEARCH_OPTIONS = "SearchOptions";
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	String currentQuery = "";
	Integer current_page = 0;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	
	public void searchOptions(MenuItem mi) {
		Intent i = new Intent(getApplicationContext(), SearchOptions.class);
		int requestCode = 0;
		startActivityForResult(i, requestCode);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
			gvResults.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId){
					Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
					ImageResult imageResult = imageResults.get(position);
					i.putExtra("result", imageResult);
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
	
	public void setupViews(){
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}
	
	public String bulidQueryString(String query){
		SharedPreferences settings = getSharedPreferences(SEARCH_OPTIONS, 0);
		String built_query = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"+"start="+
		current_page +
		"&v=1.0&q=" +
		Uri.encode(query) +
		"&imgcolor="+
		settings.getString("color_filter", "''") +
		"&imgsz="+
		settings.getString("image_size", "'small'") +
		"&imgtype="+
		settings.getString("image_type", "''") +
		"&as_sitesearch="+
		settings.getString("etSiteFilter", "''");

		return built_query;

	}

	public void loadMore(View v){
		String query = etQuery.getText().toString();
		SharedPreferences settings = getSharedPreferences(SEARCH_OPTIONS, 0);
		current_page += 8;
		queryGoogle(query);
	}

	public void queryGoogle(String query){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(bulidQueryString(query),
				   new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(JSONObject response){
						JSONArray imageJsonResults = null;
						try{
							imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
//							imageResults.clear();
							imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
							Log.d("DEBUG", imageJsonResults.toString());
						} catch (JSONException e){
							e.printStackTrace();
						}
					}
					}
		);

	}

	public void onImageSearch(View v){
		String query = etQuery.getText().toString();
		if (query != currentQuery){
			current_page = 0;
			currentQuery = query;
		}
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_LONG).show();
		queryGoogle(query);
	}

}
