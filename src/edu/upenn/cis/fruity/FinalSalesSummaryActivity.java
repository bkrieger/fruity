package edu.upenn.cis.fruity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FinalSalesSummaryActivity extends Activity {
	private static final int FinalSalesSummary_ID = 777;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculations_final_summary);
		TextView readyText = (TextView)findViewById(R.id.readyText);
		readyText.setText("Sales and stuff!");
	}
	
	public void onBackPressed() {
		//back not allowed
	}

	
	public void onFinishedButtonClick(View view) {
		Intent i = new Intent(this, MainActivity.class);
		startActivityForResult(i, FinalSalesSummary_ID);
	}
}