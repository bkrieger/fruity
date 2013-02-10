package edu.upenn.cis.fruity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class SetupStandInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_stand_info);
		Intent intent = getIntent();
		
		// Pulls selected school name from parent SetupSchoolSelectActivity
		String schoolName = (String) intent.getExtras().get("schoolName");
	
		Log.v("SetupStandInfo", "Received School Name: " + schoolName);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}