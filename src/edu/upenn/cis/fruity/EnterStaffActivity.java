package edu.upenn.cis.fruity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EnterStaffActivity extends Activity{
	public static final int InventoryPreprocessActivity_ID = 777;
	public static final int ExtraStaffActivity_ID = 199;
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
	
	public void onInventoryPreprocessButtonClick(View v) {
		Intent i1 = new Intent(this,InventoryPreprocessActivity.class);
		startActivityForResult(i1, InventoryPreprocessActivity_ID);
	}
	
	public void onExtraStaffButtonClick(View v){
		enterToDatabase();
		Intent i2 = new Intent(this,EnterExtraStaffActivity.class);
		startActivityForResult(i2, ExtraStaffActivity_ID);
	}
	
	//TODO: save staff to database
	private void enterToDatabase(){
	}
}
