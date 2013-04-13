package edu.upenn.cis.fruity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EnterExtraStaffActivity extends Activity{
	
	public static final int InventoryPreprocessActivity_ID = 777;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_extra_staff);
	}
	
	// TODO: save staff to database
	public void onInventoryPreprocessButtonClick(View v) {	
		Intent i = new Intent(this,InventoryPreprocessActivity.class);
		startActivityForResult(i, InventoryPreprocessActivity_ID);
	}
}
