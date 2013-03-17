package edu.upenn.cis.fruity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class SalesDemographicActivity extends Activity {
	
	int age_category;
	boolean isMale;
	
	public static final int SalesPaymentActivity_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_demographic);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sales_demographic, menu);
		return true;
	} 
	
	public void onB1ButtonClick(View view) {
		age_category = 1;
		isMale = true;
		goToNext();
	}
	
	public void onG1ButtonClick(View view) {
		age_category = 1;
		isMale = false;
		goToNext();
	}
	
	public void onB2ButtonClick(View view) {
		age_category = 2;
		isMale = true;
		goToNext();
	}
	
	public void onG2ButtonClick(View view) {
		age_category = 2;
		isMale = false;
		goToNext();
	}
	
	public void onB3ButtonClick(View view) {
		age_category = 3;
		isMale = true;
		goToNext();
	}
	
	public void onG3ButtonClick(View view) {
		age_category = 3;
		isMale = false;
		goToNext();
	}
	
	public void onB4ButtonClick(View view) {
		age_category = 4;
		isMale = true;
		goToNext();
	}
	
	public void onG4ButtonClick(View view) {
		age_category = 4;
		isMale = false;
		goToNext();
	}
	
	public void onB5ButtonClick(View view) {
		age_category = 5;
		isMale = true;
		goToNext();
	}
	
	public void onG5ButtonClick(View view) {
		age_category = 5;
		isMale = false;
		goToNext();
	}
	
	public void onB6ButtonClick(View view) {
		age_category = 6;
		isMale = true;
		goToNext();
	}
	
	public void onG6ButtonClick(View view) {
		age_category = 6;
		isMale = false;
		goToNext();
	}
	
	private void goToNext() {
		Intent i = new Intent(this,SalesSelectionActivity.class);
		i.putExtra("age_category", age_category);
		i.putExtra("isMale", isMale);
		startActivityForResult(i, SalesPaymentActivity_ID);
	}


}
