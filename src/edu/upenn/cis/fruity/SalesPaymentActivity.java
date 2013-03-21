package edu.upenn.cis.fruity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.EndInventoryItem;
import edu.upenn.cis.fruity.database.FruitStand;
import edu.upenn.cis.fruity.database.ProcessedInventoryItem;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SalesPaymentActivity extends Activity {

	public static final int SalesPaymentActivity_ID = 13;
	private double total;
	private FruitTuple[] purchasedItems;
	private int numCoupons;
	private int numTradeIns;
	private Intent intent;
	
	public enum PaymentType {
		CASH, COUPON, TRADEIN
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent = getIntent();
		DatabaseHandler dht = DatabaseHandler.getInstance(this);
		FruitStand currStand = dht.getCurrentFruitStand();
		
		ArrayList<FruitTuple> itemBuffer = new ArrayList<FruitTuple>();
		ProcessedInventoryItem[] possItems = currStand.getProcessedInventoryItems(this);
		
		for (ProcessedInventoryItem item : possItems) {
			Log.v("derp", "We have as a possibility: " + item.item_name);
			for (int i = 0; i < intent.getIntExtra(item.item_name, 0); i++) {
				Log.v("derp", "adding: " + item.item_name);
				itemBuffer.add(new FruitTuple(item.item_name, item.price));
				total += item.price;
			}
		}
		
		Collections.sort(itemBuffer, new Comparator<FruitTuple>() {
			public int compare(FruitTuple a, FruitTuple b) {
				return (a.price > b.price ?  1  : 
						a.price < b.price ? -1  : 0);
			}
		});
		
		purchasedItems = (FruitTuple[]) itemBuffer.toArray(new FruitTuple[itemBuffer.size()]);
		setContentView(R.layout.activity_sales_payment);
		updateViews();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	} 
	
	public void onPlusCouponButtonClick(View v) {
		if (numTradeIns + numCoupons < purchasedItems.length && numCoupons < purchasedItems.length) {
			total -= purchasedItems[numCoupons + numTradeIns].price;
			numCoupons++;
			updateViews();
		}
	}

	public void onMinusCouponButtonClick(View v) {
		if (numCoupons > 0) {
			numCoupons--;
			total += purchasedItems[numCoupons + numTradeIns].price;
			updateViews();
		}
	}

	public void onPlusTradeInButtonClick(View v) {
		if (numTradeIns + numCoupons < purchasedItems.length && numTradeIns < purchasedItems.length) {
			total -= purchasedItems[numCoupons + numTradeIns].price;
			numTradeIns++;
			updateViews();
		}		
	}
	
	public void onMinusTradeInButtonClick(View v) {
		if (numTradeIns > 0) { 
			numTradeIns--;
			total += purchasedItems[numCoupons + numTradeIns].price;
			updateViews();
		}		
	}
	
	private void updateViews() {
		TextView tradeCounter = (TextView) findViewById(R.id.ASPtradeInCounter);
		tradeCounter.setText("Trade-Ins: " + numTradeIns);
		TextView couponCounter = (TextView) findViewById(R.id.ASPcouponCounter);
		couponCounter.setText("Coupons: " + numCoupons);
		TextView cashCounter = (TextView) findViewById(R.id.ASPcashCounter);
		cashCounter.setText("Payment: " + total);
	}

	private void submit() {
		// TODO: store all data in SQL
		Toast toast;
		if (total > 0) {
			Intent i = new Intent();
			setResult(RESULT_OK, i);
			
			toast = Toast.makeText(getApplicationContext(),
					"Purchase Successful!", Toast.LENGTH_SHORT);
			toast.show();
			finish();
		} else {
			toast = Toast.makeText(getApplicationContext(),
					"Purchase must contain at least one item.",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
	public void onFinishTransactionButtonClick(View view){

	}
	
	private class FruitTuple {
		private String fruit;
		private double price;
		
		public FruitTuple(String f, double p) {
			fruit = f;
			price = p;
		}
	}

}
