package edu.upenn.cis.fruity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SalesReadyForSalesActivity extends Activity {

	private static final int SalesReadyForSalesActivity_ID = 32;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_ready_to_sell);
	}
	
	public void onBackPressed() {
		//back not allowed
	}

	
	public void onReadyButtonClick(View view) {
		Intent i = new Intent(this, SalesSummaryActivity.class);
		startActivityForResult(i, SalesReadyForSalesActivity_ID);
	}
}
