package edu.upenn.cis.fruity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SalesPaymentActivity extends Activity {
	
	public enum PaymentType {CASH, COUPON, TRADEIN};

	int age_category = -1;
	boolean isMale = true;
	PaymentType paymentType;
	int mixedBags = 0;
	int smoothies = 0;
	int granola = 0;
	int apples = 0;
	int oranges = 0;
	int grapes = 0;
	int pears = 0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_payment);
		
		Intent intent = getIntent();
		age_category = intent.getIntExtra("age_category", -1);
		isMale = intent.getBooleanExtra("isMale", true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sales_payment, menu);
		return true;
	}
	
	public void onWholeFruitButtonClick(View v) {
		
	}
	
	public void onMixedMinusButtonClick(View v) {
		if(mixedBags>0) {
			mixedBags--;
		}
		TextView count = (TextView)findViewById(R.id.ASPmixedCounter);
		count.setText(""+mixedBags);
	}
	
	public void onMixedPlusButtonClick(View v) {
		if(mixedBags<99) {
			mixedBags++;
		}
		TextView count = (TextView)findViewById(R.id.ASPmixedCounter);
		count.setText(""+mixedBags);
	}
	
	public void onSmoothieMinusButtonClick(View v) {
		if(smoothies>0) {
			smoothies--;
		}
		TextView count = (TextView)findViewById(R.id.ASPsmoothieCounter);
		count.setText(""+smoothies);
	}
	
	public void onSmoothiePlusButtonClick(View v) {
		if(smoothies<99) {
			smoothies++;
		}
		TextView count = (TextView)findViewById(R.id.ASPsmoothieCounter);
		count.setText(""+smoothies);
	}
	
	public void onGranolaMinusButtonClick(View v) {
		if(granola>0) {
			granola--;
		}
		TextView count = (TextView)findViewById(R.id.ASPgranolaCounter);
		count.setText(""+granola);
	}
	
	public void onGranolaPlusButtonClick(View v) {
		if(granola<99) {
			granola++;
		}
		TextView count = (TextView)findViewById(R.id.ASPgranolaCounter);
		count.setText(""+granola);
	}
	
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
		//store all data in SQL
		
		Intent i = new Intent();
		setResult(RESULT_OK,i);
		finish();
	}

}
