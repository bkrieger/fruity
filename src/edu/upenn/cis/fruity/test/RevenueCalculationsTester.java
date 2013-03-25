package edu.upenn.cis.fruity.test;

import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.upenn.cis.fruity.R;
import edu.upenn.cis.fruity.RevenueCalculationsActivity;
import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;

public class RevenueCalculationsTester extends ActivityInstrumentationTestCase2<RevenueCalculationsActivity> {
	protected Activity activity;
	private Context context;
	private DatabaseHandler dh;
	private FruitStand stand, currentStand;

	private Button checkRevenueButton;
	private TextView numCorrectDisplay;
	
	public RevenueCalculationsTester(){
		super("edu.upenn.cis.fruity", RevenueCalculationsActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		context = this.getActivity();
		// input data into the tables for transaction, price,
		dh = DatabaseHandler.getInstance(context);
		stand = new FruitStand("test", "3/2/2013", 75, "Sunny", 125.25, 50.0, 20.0, 0.0);
		dh.putFruitStand(stand);
		currentStand = dh.getCurrentFruitStand();
		
		int numItems = 10;
		// enter postprocess inventory and prices into database
		currentStand.addProcessedInventoryItem(context, "apple", numItems, 0.1);
		currentStand.addProcessedInventoryItem(context, "pear", numItems, 0.2);
		currentStand.addProcessedInventoryItem(context, "orange", numItems, 0.3);
		currentStand.addProcessedInventoryItem(context, "banana", numItems, 0.4);
		currentStand.addProcessedInventoryItem(context, "grapes", numItems, 0.5);
		currentStand.addProcessedInventoryItem(context, "kiwi", numItems, 0.6);
		currentStand.addProcessedInventoryItem(context, "mixedBag", numItems, 0.7);
		currentStand.addProcessedInventoryItem(context, "frozenFruitBag", numItems, 0.8);
		currentStand.addProcessedInventoryItem(context, "granola", numItems, 0.9);
	}

	protected void tearDown() throws Exception{
		super.tearDown();
	}

	public void testRevenueMultipleTransactionsAllCash(){		
		// enter purchase information into database
		currentStand.addPurchase(context, "apple", 4, 0, 0, .40, "Boy Grades 6-8");
		currentStand.addPurchase(context, "apple", 2, 0, 0, .20, "Girl Grades 6-8");
		currentStand.addPurchase(context, "orange", 2, 0, 0, .60, "Girl Grades 9-12");
		currentStand.addPurchase(context, "orange", 2, 0, 0, .60, "Girl Grades K-5");
		currentStand.addPurchase(context, "frozenFruitBag", 1, 0, 0, .80, "Male Parent");
		
		activity = getActivity();
		
		TextView applePrice = (TextView)activity.findViewById(R.id.price_calc_apple);
		TextView orangePrice = (TextView)activity.findViewById(R.id.price_calc_orange);
		TextView smoothiePrice = (TextView)activity.findViewById(R.id.price_calc_smoothie);

		assertEquals("$0.10", applePrice.getText());
		assertEquals("$0.30", orangePrice.getText());
		assertEquals("$0.80", smoothiePrice.getText());
		
		TextView appleNum = (TextView)activity.findViewById(R.id.num_bought_apple);
		TextView orangeNum = (TextView)activity.findViewById(R.id.num_bought_orange);
		TextView smoothieNum = (TextView)activity.findViewById(R.id.num_bought_smoothie);
		
		assertEquals("6", appleNum.getText());
		assertEquals("4", orangeNum.getText());
		assertEquals("1", smoothieNum.getText());
		
		try{
			activity.runOnUiThread(new Runnable(){
				public void run(){
					checkRevenueButton = (Button)activity.findViewById(R.id.checkRevenueCalculationsBtn);
					checkRevenueButton.performClick();
				}
			});
			getInstrumentation().waitForIdleSync();	
			
			numCorrectDisplay = (TextView)activity.findViewById(R.id.num_correct_revenue_calculations);
			assertEquals("6/10", numCorrectDisplay.getText());
		}
		catch (NullPointerException e){
			System.out.println("NullPointerException caught in RevenueCalculationsTester " +
					"onCheckRevenueCalculationsButtonClick().");
		}

		final EditText appleRevenue =(EditText)activity.findViewById(R.id.revenue_apple);
		final EditText orangeRevenue =(EditText)activity.findViewById(R.id.revenue_orange);
		final EditText smoothieRevenue =(EditText)activity.findViewById(R.id.revenue_smoothie);
		final EditText totalRevenue =(EditText)activity.findViewById(R.id.totalRevenue);
		
		try{
			activity.runOnUiThread(new Runnable(){
				public void run(){
					appleRevenue.setText("$0.60");
					orangeRevenue.setText("$1.20");
					smoothieRevenue.setText("$0.80");
					totalRevenue.setText("$2.60");
					
					checkRevenueButton = (Button)activity.findViewById(R.id.checkRevenueCalculationsBtn);
					checkRevenueButton.performClick();
				}
			});
			getInstrumentation().waitForIdleSync();
			
			numCorrectDisplay = (TextView)activity.findViewById(R.id.num_correct_revenue_calculations);
			assertEquals("10/10", numCorrectDisplay.getText());
		}
		catch (NullPointerException e){
			System.out.println("NullPointerException caught in RevenueCalculationsTester " +
					"onCheckRevenueCalculationsButtonClick().");
		}
	}
}