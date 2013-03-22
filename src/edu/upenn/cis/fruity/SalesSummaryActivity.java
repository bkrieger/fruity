package edu.upenn.cis.fruity;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class SalesSummaryActivity extends Activity {
	
public static final int SalesSummaryActivity_ID = 14;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_summary);
		
		// TODO: Use today's data to generate summary of recent transactions
		DatabaseHandler dht = DatabaseHandler.getInstance(this);
		FruitStand currStand = dht.getCurrentFruitStand();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	} 
	
	public void onTransactionButtonClick(View v) {
		Intent i = new Intent(this, SalesDemographicActivity.class);
		startActivityForResult(i, SalesSummaryActivity_ID);
	}
	
	// TODO: Probably want to have some sort of confirmation here
	public void onEndTrackingButtonClick(View view){
		Intent i = new Intent(this, RevenueCalculationsActivity.class);
		startActivityForResult(i, SalesSummaryActivity_ID);
	}
}
