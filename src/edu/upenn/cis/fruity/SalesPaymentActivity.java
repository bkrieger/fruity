package edu.upenn.cis.fruity;

import edu.upenn.cis.fruity.SalesSelectionActivity.PaymentType;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SalesPaymentActivity extends Activity {

	public enum PaymentType {
		CASH, COUPON, TRADEIN
	};

	public static final int SalesPaymentActivity_ID = 13;
	private PaymentType paymentType;
	private int total;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_payment);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	} 
	
	// TODO: Migrating this to new payment screen
	public void onCashButtonClick(View v) {
		paymentType = PaymentType.CASH;
		submit();
	}

	public void onCouponButtonClick(View v) {
		paymentType = PaymentType.COUPON;
		submit();
	}

	public void onTradeinButtonClick(View v) {
		paymentType = PaymentType.TRADEIN;
		submit();
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
		Intent i = new Intent(this, RevenueCalculationsActivity.class);
		startActivityForResult(i, SalesPaymentActivity_ID);
	}
}
