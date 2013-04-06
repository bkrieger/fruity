package edu.upenn.cis.fruity;

import java.text.DecimalFormat;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;
import edu.upenn.cis.fruity.database.Purchase;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SalesSummaryActivity extends Activity {
	
public static final int SalesSummaryActivity_ID = 14;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_summary);
		
		DatabaseHandler dht = DatabaseHandler.getInstance(this);
		FruitStand currStand = dht.getCurrentFruitStand();
		Purchase[] purchases = currStand.getPurchases(this);
		
		if (purchases != null && purchases.length != 0) {
			ListView purchaseListView = (ListView) findViewById(R.id.list_view_purchases);
			String[] purchaseStrings = generatePurchaseStrings(purchases);
			
			ArrayAdapter<String> adapter =
			    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, purchaseStrings);
			purchaseListView.setAdapter(adapter);
		}
	}
	
	public void onBackPressed() {
		//back not allowed
	}


	private String[] generatePurchaseStrings(Purchase[] purchases) {
		String[] outArr = new String[purchases.length];
		
		for (int i = 0; i < purchases.length; i++) {
			Purchase purchase = purchases[purchases.length - i - 1];
			String purchaseString = "";
			
			// Convert Database key to pretty-printed version
			String preName = purchase.item_name;
			String postName = preName.replaceAll("([a-z])([A-Z])", "$1 $2");
			postName = Character.toUpperCase(postName.charAt(0)) + postName.substring(1, postName.length());
			
			// Append extra data about transaction
			int count = purchase.count;
			String cash = new DecimalFormat("######0.00").format(purchase.amount_cash);
			int tnum = purchase.num_tradeins;
			int cnum = purchase.num_coupons;
			
			// Build up the string, only adding needed information
			purchaseString = postName + ": ";
			purchaseString = purchaseString + (count > 0 ? count : "") + " for $" + cash;
			purchaseString = purchaseString + (tnum > 0 ? ", " + tnum + "T" : "");
			purchaseString = purchaseString + (cnum > 0 ? ", " + cnum + "C" : "");		
			outArr[i] = purchaseString;
		}
		
		return outArr;
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
		Intent i = new Intent(this, SalesReadyForCalculationsActivity.class);
		startActivityForResult(i, SalesSummaryActivity_ID);
	}
}
