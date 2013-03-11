package edu.upenn.cis.fruity;

import edu.upenn.cis.fruity.database.DatabaseHandler;

import edu.upenn.cis.fruity.database.FruitStand;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class EnterStaffActivity extends Activity{
	public static final int StandInfoActivity_ID = 777;
	String schoolName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_staff);
		
		Intent intent = getIntent();

		// send school title from past screen to next screen
		if (intent != null && intent.getExtras() != null) {
			schoolName = (String) intent.getExtras().get("schoolName");
		} else {
			schoolName = "Filler Text";
		}
		
	}
	
	private void onStandInfoButtonClick(View v) {	
		Intent i = new Intent(this,SetupStandInfoActivity.class);
		i.putExtra("schoolName", schoolName);
		startActivityForResult(i, StandInfoActivity_ID);
	}
}
