package edu.upenn.cis.fruity;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class SetupSchoolSelectActivity extends Activity {

	public static final int EnterStaffActivity_ID = 19;
	public static final int SchoolInputActivity_ID = 15;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_select_school);
		
		ListView schoolListView = (ListView) findViewById(R.id.list_view_schools);

		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		final String[] items = dh.getSchoolsWithOther();
		
	    ArrayAdapter<String> adapter =
	      new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

	    schoolListView.setAdapter(adapter);
	    schoolListView.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
				if ((int) id != items.length - 1) {
					goToSchoolInfo(items[(int) id]);
				} else {
					goToSchoolInput();
				}
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
	private void goToSchoolInfo(String schoolName) {
		Intent i = new Intent(this,EnterStaffActivity.class);
		i.putExtra("schoolName", schoolName);
		startActivityForResult(i, EnterStaffActivity_ID);
	}
	
	// Advances to school input if "Other" is selected
	private void goToSchoolInput() {
		Intent i = new Intent(this,SetupInputSchoolActivity.class);
		startActivityForResult(i, SchoolInputActivity_ID);
	}
}