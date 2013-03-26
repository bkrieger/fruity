package edu.upenn.cis.fruity.test;

import edu.upenn.cis.fruity.R;
import edu.upenn.cis.fruity.SalesPaymentActivity;
import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;
import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;
import junit.framework.TestCase;

public class PaymentTester extends ActivityInstrumentationTestCase2<SalesPaymentActivity> {

	protected Activity activity;
	private Context context;
	private DatabaseHandler dh;
	private FruitStand stand, currentStand;
	private Button checkTransactionEnd;
	
	
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
		
		try{
			activity.runOnUiThread(new Runnable(){
				public void run(){
					checkTransactionEnd = (Button)activity.findViewById(R.id.endTransactionButton);
					checkTransactionEnd.performClick();
				}
			});
			getInstrumentation().waitForIdleSync();

			final Context context = this.getActivity();
			dh = DatabaseHandler.getInstance(context);
			currentStand = dh.getCurrentFruitStand();
			
			assertNotNull(currentStand);
		}
		catch (NullPointerException e){
			System.out.println("NullPointerException caught in PaymentTester " +
					"onFinishTransactionButtonClick().");
		}
		
		
	}
	
}
