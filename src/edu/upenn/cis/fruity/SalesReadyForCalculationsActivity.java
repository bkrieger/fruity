package edu.upenn.cis.fruity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SalesReadyForCalculationsActivity extends Activity {

	private static final int SalesReadyForCalculationsActivity_ID = 33;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_ready_to_calculate);
	}
	
	public void onReadyButtonClick(View view) {
		Intent i = new Intent(this, RevenueCalculationsActivity.class);
		startActivityForResult(i, SalesReadyForCalculationsActivity_ID);
	}
}