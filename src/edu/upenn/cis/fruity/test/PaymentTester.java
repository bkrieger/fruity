package edu.upenn.cis.fruity.test;

import edu.upenn.cis.fruity.InventoryPreprocessActivity;
import edu.upenn.cis.fruity.R;
import edu.upenn.cis.fruity.SalesPaymentActivity;
import edu.upenn.cis.fruity.SalesSelectionActivity;
import edu.upenn.cis.fruity.SalesSummaryActivity;
import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;
import edu.upenn.cis.fruity.database.Purchase;
import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import junit.framework.TestCase;

public class PaymentTester extends ActivityInstrumentationTestCase2<SalesPaymentActivity> {

	protected Activity activity;
	private Context context;
	private DatabaseHandler dh;
	private FruitStand stand, currentStand;
	private Button plusCoupon;
	private Button plusTradeIn;
	private EditText donationInput;
	private TextView cashDisplay;
	private TextView couponDisplay;
	private TextView tradeInDisplay;
	
	
	public PaymentTester(){
		super("edu.upenn.cis.fruity", SalesPaymentActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		activity = getActivity();
		context = this.getActivity();
		dh = DatabaseHandler.getInstance(context);
		stand = new FruitStand("test", "3/25/2013", 75, "Sunny", 50.0, 10.0, 20.0, 30.0);
		stand.addProcessedInventoryItem(context, "apple", 0, 1.00);
		stand.addProcessedInventoryItem(context, "orange", 0, 2.00);
		dh.putFruitStand(stand);
		currentStand = dh.getCurrentFruitStand();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testNoPaymentNoPurchase(){
		
		assertEquals(50.0, currentStand.initial_cash);
		assertEquals(10.0, currentStand.stand_cost);
		assertEquals(20.0, currentStand.smoothie_cost);
		assertEquals(30.0, currentStand.additional_cost);
		
		Intent intent = new Intent();
		activity.setIntent(intent);
		
		try{
			activity.runOnUiThread(new Runnable(){
				public void run(){
					plusCoupon = (Button)activity.findViewById(R.id.plusCouponButton);
				    plusTradeIn = (Button)activity.findViewById(R.id.plusTradeInButton);
				    donationInput = (EditText)activity.findViewById(R.id.donationInput);
				    cashDisplay = (TextView)activity.findViewById(R.id.ASPcashCounter);
				    couponDisplay = (TextView)activity.findViewById(R.id.ASPcouponCounter);
				    tradeInDisplay = (TextView)activity.findViewById(R.id.ASPtradeInCounter);
				}
			});
			getInstrumentation().waitForIdleSync();
			final Context context = this.getActivity();
			dh = DatabaseHandler.getInstance(context);
			currentStand = dh.getCurrentFruitStand();
			assertNotNull(currentStand);
		}
		catch (NullPointerException e){
			fail();
		}
	
					
		activity.finish();
	}
	
	public void testOneItemOnePaymentType(){
		
		/*
		assertEquals(50.0, currentStand.initial_cash);
		assertEquals(10.0, currentStand.stand_cost);
		assertEquals(20.0, currentStand.smoothie_cost);
		assertEquals(30.0, currentStand.additional_cost);
		
		Intent intent = new Intent();
		intent.putExtra("apple", 3);
		activity.setIntent(intent);
		
		try{
			activity.runOnUiThread(new Runnable(){
				public void run(){
					plusCoupon = (Button)activity.findViewById(R.id.plusCouponButton);
				    plusTradeIn = (Button)activity.findViewById(R.id.plusTradeInButton);
				    donationInput = (EditText)activity.findViewById(R.id.donationInput);
				    cashDisplay = (TextView)activity.findViewById(R.id.ASPcashCounter);
				    couponDisplay = (TextView)activity.findViewById(R.id.ASPcouponCounter);
				    tradeInDisplay = (TextView)activity.findViewById(R.id.ASPtradeInCounter);
				    plusCoupon.performClick();
				}
			});
			getInstrumentation().waitForIdleSync();
			assertEquals(couponDisplay.getText().toString(), "0");
			assertEquals(tradeInDisplay.getText().toString(), "0");
			assertNotNull(currentStand);
		}
		catch (NullPointerException e){
			fail();
		}
		
		activity.finish();*/
	}
	
}
