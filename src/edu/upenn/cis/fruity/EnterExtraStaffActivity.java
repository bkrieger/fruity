package edu.upenn.cis.fruity;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EnterExtraStaffActivity extends Activity{
	
	public static final int InventoryPreprocessActivity_ID = 777;
	
	public void onBackPressed() {
		//back not allowed
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_extra_staff);
	}
	
	// TODO: save staff to database
	public void onInventoryPreprocessButtonClick(View v) {	
		
		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		FruitStand stand = dh.getCurrentFruitStand();
		
		String volunteer1 = ((EditText) findViewById(R.id.enterExtraStaff_volunteer1)).getText().toString();
		if(!volunteer1.isEmpty()) {
			stand.addStaffMember(this, volunteer1, true);
		}

		String volunteer2 = ((EditText) findViewById(R.id.enterExtraStaff_volunteer2)).getText().toString();
		if(!volunteer2.isEmpty()) {
			stand.addStaffMember(this, volunteer2, true);
		}

		String volunteer3 = ((EditText) findViewById(R.id.enterExtraStaff_volunteer3)).getText().toString();
		if(!volunteer3.isEmpty()) {
			stand.addStaffMember(this, volunteer3, true);
		}
		
		String staff1 = ((EditText) findViewById(R.id.enterExtraStaff_staff1)).getText().toString();
		if(!staff1.isEmpty()) {
			stand.addStaffMember(this, staff1, false);
		}
		
		String staff2 = ((EditText) findViewById(R.id.enterExtraStaff_staff2)).getText().toString();
		if(!staff2.isEmpty()) {
			stand.addStaffMember(this, staff2, false);
		}
		
		String staff3 = ((EditText) findViewById(R.id.enterExtraStaff_staff3)).getText().toString();
		if(!staff3.isEmpty()) {
			stand.addStaffMember(this, staff3, true);
		}


		Intent i = new Intent(this,InventoryPreprocessActivity.class);
		startActivityForResult(i, InventoryPreprocessActivity_ID);
	}
}
