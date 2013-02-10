package edu.upenn.cis.fruity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class SetupSchoolSelectActivity extends Activity {

	public static final int StandInfoActivity_ID = 777;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_select_school);
		
		ListView schoolListView = (ListView) findViewById(R.id.list_view_schools);

		// TODO: Access database store of schools 
	    final String[] items = new String[] {"S1", "S2", "S3", "S4", "S5",};
	    ArrayAdapter<String> adapter =
	      new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

	    schoolListView.setAdapter(adapter);
	    schoolListView.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
				Log.v("SchoolSelectActivity_SelectedItemID", "ID: " + id + " Item: " + items[(int) id]);
				goToNext(items[(int) id]);
			} 	
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	// Advances to stand information within SetupStandInfoActivity.
	private void goToNext(String schoolName) {
		Intent i = new Intent(this,SetupStandInfoActivity.class);
		i.putExtra("schoolName", schoolName);
		startActivityForResult(i, StandInfoActivity_ID);
	}
}