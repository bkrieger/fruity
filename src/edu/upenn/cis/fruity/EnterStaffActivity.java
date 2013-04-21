package edu.upenn.cis.fruity;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EnterStaffActivity extends Activity{
	public static final int InventoryPreprocessActivity_ID = 777;
	public static final int ExtraStaffActivity_ID = 199;
	String schoolName;
	
	public void onBackPressed() {
		//back not allowed
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_staff);		
	}
	
	public void onInventoryPreprocessButtonClick(View v) {
		enterToDatabase();
		Intent i1 = new Intent(this,InventoryPreprocessActivity.class);
		startActivityForResult(i1, InventoryPreprocessActivity_ID);
	}
	
	public void onExtraStaffButtonClick(View v){
		enterToDatabase();
		Intent i2 = new Intent(this,EnterExtraStaffActivity.class);
		startActivityForResult(i2, ExtraStaffActivity_ID);
	}
	
	private void enterToDatabase(){
		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		FruitStand stand = dh.getCurrentFruitStand();
		
		String volunteer1 = ((EditText) findViewById(R.id.enterStaff_volunteer1)).getText().toString();
		String volunteer2 = ((EditText) findViewById(R.id.enterStaff_volunteer2)).getText().toString();
		String volunteer3 = ((EditText) findViewById(R.id.enterStaff_volunteer3)).getText().toString();
		String volunteer4 = ((EditText) findViewById(R.id.enterStaff_volunteer4)).getText().toString();
		String staff1 = ((EditText) findViewById(R.id.enterStaff_staff1)).getText().toString();
		String staff2 = ((EditText) findViewById(R.id.enterStaff_staff2)).getText().toString();

		if(volunteer1.isEmpty() && volunteer2.isEmpty() && volunteer3.isEmpty() && volunteer4.isEmpty() && staff1.isEmpty() && staff2.isEmpty()) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Fill in at least one staff member to move on.", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}

		if(!volunteer1.isEmpty()) {
			stand.addStaffMember(this, volunteer1, true);
		}

		if(!volunteer2.isEmpty()) {
			stand.addStaffMember(this, volunteer2, true);
		}

		if(!volunteer3.isEmpty()) {
			stand.addStaffMember(this, volunteer3, true);
		}

		if(!volunteer4.isEmpty()) {
			stand.addStaffMember(this, volunteer4, true);
		}
		
		if(!staff1.isEmpty()) {
			stand.addStaffMember(this, staff1, false);
		}
		
		if(!staff2.isEmpty()) {
			stand.addStaffMember(this, staff2, false);
		}



		
	}
}
