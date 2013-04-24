package edu.upenn.cis.fruity;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.School;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * User can input quantities for whole fruit inventory before fruit is processed and sold
 */
public class SetupInputSchoolActivity extends Activity {
	
	public static final int EnterStaffActivity_ID = 19;
	
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
	
	private void dontLetMoveOn() {
		Toast toast = Toast.makeText(getApplicationContext(),
				"Fill in the blank fields to move on.", Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public void onStandInfoButtonPress(View v) {
		
		EditText schoolName = (EditText) findViewById(R.id.schoolInput_schoolName);
		String newSchoolName = schoolName.getText().toString();
		
		if(newSchoolName.isEmpty()) {
			dontLetMoveOn();
			return;
		}
		Intent i = new Intent(this,EnterStaffActivity.class);

		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		dh.putSchool(new School(newSchoolName));
		
		i.putExtra("schoolName", newSchoolName);
		
		startActivityForResult(i, EnterStaffActivity_ID);
	}
	
}