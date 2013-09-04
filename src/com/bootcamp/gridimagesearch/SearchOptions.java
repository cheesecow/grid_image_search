package com.bootcamp.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SearchOptions extends Activity implements OnItemSelectedListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_options);
		createSpinner(R.id.image_size, R.array.size);
		createSpinner(R.id.color_filter, R.array.color_filter);
	}
	
	public void createSpinner(int spinner_id, int type){
		Spinner spinner = (Spinner) findViewById(spinner_id);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, type, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		  Intent data = new Intent();
		  data.putExtra("name", parent.getItemAtPosition(pos).toString());
		  setResult(RESULT_OK, data); 
		  super.finish(); 

    }
   public void onNothingSelected(AdapterView<?> parent) {}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_options, menu);
		return true;
	}
	
}
