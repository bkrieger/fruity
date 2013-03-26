package edu.upenn.cis.fruity.test;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.upenn.cis.fruity.ProfitCalculationsActivity;
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
		activity = getActivity();

		context = this.getActivity();
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

	public void testRevenueMultipleTransactions(){		
		// enter purchase information into database
		currentStand.addPurchase(context, "apple", 4, 0, 0, .40, "Boy Grades 6-8");
		currentStand.addPurchase(context, "apple", 2, 0, 0, .20, "Girl Grades 6-8");
		currentStand.addPurchase(context, "orange", 2, 0, 0, .60, "Girl Grades 9-12");
		currentStand.addPurchase(context, "orange", 2, 0, 0, .60, "Girl Grades K-5");
		currentStand.addPurchase(context, "frozenFruitBag", 1, 0, 0, .80, "Male Parent");
		currentStand.addPurchase(context, "kiwi", 4, 2, 2, 0, "Boy Grades 6-8");
		currentStand.addPurchase(context, "pear", 2, 1, 0, .20, "Girl Grades 6-8");
		currentStand.addPurchase(context, "grapes", 2, 0, 1, .50, "Girl Grades 9-12");
		currentStand.addPurchase(context, "banana", 1, 0, 0, .40, "Girl Grades K-5");
		currentStand.addPurchase(context, "mixedBag", 2, 0, 2, 0, "Male Parent");
		currentStand.addPurchase(context, "granola", 3, 1, 1, .90, "Female Staff");
		currentStand.addPurchase(context, "apple", 1, 1, 0, 0, "Girl Grades 6-8");
		
		checkPrices();
		
		final TextView appleNum = (TextView)activity.findViewById(R.id.num_bought_apple);
		final TextView pearNum = (TextView)activity.findViewById(R.id.num_bought_pear);
		final TextView orangeNum = (TextView)activity.findViewById(R.id.num_bought_orange);
		final TextView bananaNum = (TextView)activity.findViewById(R.id.num_bought_banana);
		final TextView grapesNum = (TextView)activity.findViewById(R.id.num_bought_grapes);
		final TextView kiwiNum = (TextView)activity.findViewById(R.id.num_bought_kiwi);
		final TextView mixedBagNum = (TextView)activity.findViewById(R.id.num_bought_mixedBag);
		final TextView smoothieNum = (TextView)activity.findViewById(R.id.num_bought_smoothie);
		final TextView granolaNum = (TextView)activity.findViewById(R.id.num_bought_granola);
		
		try{
			activity.runOnUiThread(new Runnable(){
				public void run(){
					assertEquals("6", appleNum.getText());
					assertEquals("1", pearNum.getText());
					assertEquals("4", orangeNum.getText());
					assertEquals("1", bananaNum.getText());
					assertEquals("1", grapesNum.getText());
					assertEquals("0", kiwiNum.getText());
					assertEquals("0", mixedBagNum.getText());
					assertEquals("1", smoothieNum.getText());
					assertEquals("1", granolaNum.getText());
					
					checkRevenueButton = (Button)activity.findViewById(R.id.checkRevenueCalculationsBtn);
					checkRevenueButton.performClick();
				}
			});
			getInstrumentation().waitForIdleSync();	
			
			numCorrectDisplay = (TextView)activity.findViewById(R.id.num_correct_revenue_calculations);
			assertEquals("2/10", numCorrectDisplay.getText());
		}
		catch (NullPointerException e){
			System.out.println("NullPointerException caught in RevenueCalculationsTester " +
					"onCheckRevenueCalculationsButtonClick().");
		}

		final EditText appleRevenue =(EditText)activity.findViewById(R.id.revenue_apple);
		final EditText pearRevenue =(EditText)activity.findViewById(R.id.revenue_pear);
		final EditText orangeRevenue =(EditText)activity.findViewById(R.id.revenue_orange);
		final EditText bananaRevenue =(EditText)activity.findViewById(R.id.revenue_banana);
		final EditText grapesRevenue =(EditText)activity.findViewById(R.id.revenue_grapes);
		final EditText smoothieRevenue =(EditText)activity.findViewById(R.id.revenue_smoothie);
		final EditText granolaRevenue =(EditText)activity.findViewById(R.id.revenue_granola);
		final EditText totalRevenue =(EditText)activity.findViewById(R.id.totalRevenue);
		
		try{
			activity.runOnUiThread(new Runnable(){
				public void run(){
					appleRevenue.setText("$0.60");
					pearRevenue.setText("$0.20");
					orangeRevenue.setText("$1.20");
					bananaRevenue.setText("$0.40");
					grapesRevenue.setText("$0.50");
					smoothieRevenue.setText("$0.80");
					granolaRevenue.setText("$0.90");
					totalRevenue.setText("$4.60");
					
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
		
		activity.finish();
	}

	public void testZProfitCalculationsButtonClick() {
		ActivityMonitor activityMonitor = 
				getInstrumentation().addMonitor(ProfitCalculationsActivity.class.getName(), null, false);

		activity = getActivity();
		
		try{
			activity.runOnUiThread(new Runnable(){
				public void run(){
					Button profitButton = (Button)activity.findViewById(R.id.gotoProfitCalculationsBtn);
					profitButton.performClick();
				}
			});
			getInstrumentation().waitForIdleSync();
			
			// next activity is opened and captured.
			ProfitCalculationsActivity nextActivity = 
					(ProfitCalculationsActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
			assertNotNull(nextActivity);
			nextActivity.finish();
		}
		catch (NullPointerException e){
			System.out.println("NullPointerException caught in RevenueCalculationsTester " +
					"onGoToProfitCalculationsButtonClick().");
		}
		activity.finish();
	}
	
	public void checkPrices(){
		TextView applePrice = (TextView)activity.findViewById(R.id.price_calc_apple);
		TextView pearPrice = (TextView)activity.findViewById(R.id.price_calc_pear);
		TextView orangePrice = (TextView)activity.findViewById(R.id.price_calc_orange);
		TextView bananaPrice = (TextView)activity.findViewById(R.id.price_calc_banana);
		TextView grapesPrice = (TextView)activity.findViewById(R.id.price_calc_grapes);
		TextView kiwiPrice = (TextView)activity.findViewById(R.id.price_calc_kiwi);
		TextView mixedBagPrice = (TextView)activity.findViewById(R.id.price_calc_mixedBag);
		TextView smoothiePrice = (TextView)activity.findViewById(R.id.price_calc_smoothie);
		TextView granolaPrice = (TextView)activity.findViewById(R.id.price_calc_granola);
		
		assertEquals("$0.10", applePrice.getText());
		assertEquals("$0.20", pearPrice.getText());
		assertEquals("$0.30", orangePrice.getText());
		assertEquals("$0.40", bananaPrice.getText());
		assertEquals("$0.50", grapesPrice.getText());
		assertEquals("$0.60", kiwiPrice.getText());
		assertEquals("$0.70", mixedBagPrice.getText());
		assertEquals("$0.80", smoothiePrice.getText());
		assertEquals("$0.90", granolaPrice.getText());
	}
}