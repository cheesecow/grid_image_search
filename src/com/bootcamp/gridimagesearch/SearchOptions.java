package com.bootcamp.gridimagesearch;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchOptions extends Activity {
	public static final String SEARCH_OPTIONS = "SearchOptions";
	Spinner image_size;
	Spinner color_filter;
	Spinner image_type;
	EditText etSiteFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_options);
		setupViews();
	}
	
	public void setupViews(){
		image_size = (Spinner) findViewById(R.id.image_size);
		color_filter = (Spinner) findViewById(R.id.color_filter);
		image_type = (Spinner) findViewById(R.id.image_type);
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
	}

	@Override
	public void onBackPressed() {
		SharedPreferences settings = getSharedPreferences(SEARCH_OPTIONS, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putString("image_size", image_size.getSelectedItem().toString());
	      editor.putString("color_filter", color_filter.getSelectedItem().toString());
	      editor.putString("image_type", image_type.getSelectedItem().toString());
	      editor.putString("etSiteFilter", etSiteFilter.getText().toString());
	      editor.commit();
	      SearchOptions.super.onBackPressed();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_options, menu);
		return true;
	}
	
}
