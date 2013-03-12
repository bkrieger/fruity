package edu.upenn.cis.fruity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EnterExtraStaffActivity extends Activity{
	public static final int StandInfoActivity_ID = 777;
	String schoolName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_extra_staff);
		
		Intent intent = getIntent();

		// send school title from past screen to next screen
		if (intent != null && intent.getExtras() != null) {
			schoolName = (String) intent.getExtras().get("schoolName");
		} else {
			schoolName = "Filler Text";
		}
		
	}
	
	public void onStandInfoButtonClick(View v) {	
		Intent i = new Intent(this,SetupStandInfoActivity.class);
		i.putExtra("schoolName", schoolName);
		startActivityForResult(i, StandInfoActivity_ID);
	}
	
	
}
