package edu.upenn.cis.fruity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

/**
 * User can input quantities for whole fruit inventory before fruit is processed and sold
 */
public class SetupInputSchoolActivity extends Activity {
	
	public static final int SetupStandInfoActivity_ID = 777;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_input_school);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	} 
	
	// TODO: Add logging of new schools inputted, to be displayed on
	// prior screen at later uses of app?
	public void onStandInfoButtonPress(View v) {
		Intent i = new Intent(this,SetupStandInfoActivity.class);
		
		EditText schoolName = (EditText) findViewById(R.id.schoolInput_schoolName);
		i.putExtra("schoolName", schoolName.getText().toString());
		
		startActivityForResult(i, SetupStandInfoActivity_ID);
	}
	
}