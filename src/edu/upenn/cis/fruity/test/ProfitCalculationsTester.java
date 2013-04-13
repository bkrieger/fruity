package edu.upenn.cis.fruity.test;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.upenn.cis.fruity.FinalSalesSummaryActivity;
import edu.upenn.cis.fruity.ProfitCalculationsActivity;
import edu.upenn.cis.fruity.R;
import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;
import edu.upenn.cis.fruity.database.Totals;

public class ProfitCalculationsTester extends ActivityInstrumentationTestCase2<ProfitCalculationsActivity> {
	protected Activity activity;
	private Context context;
	private DatabaseHandler dh;
	private FruitStand stand, currentStand;

	private Button checkProfitButton;
	private TextView numCorrectDisplay;
	
	public ProfitCalculationsTester(){
		super("edu.upenn.cis.fruity", ProfitCalculationsActivity.class);
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

	protected void tearDown() throws Exception{
		super.tearDown();
	}

	public void testProfitCalculations(){
		assertEquals(50.0, currentStand.initial_cash);
		assertEquals(10.0, currentStand.stand_cost);
		assertEquals(20.0, currentStand.smoothie_cost);
		assertEquals(30.0, currentStand.additional_cost);
		
		try{
			activity.runOnUiThread(new Runnable(){
				public void run(){
					checkProfitButton = (Button)activity.findViewById(R.id.checkProfitCalculationsBtn);
					checkProfitButton.performClick();
				}
			});
			getInstrumentation().waitForIdleSync();

			numCorrectDisplay = (TextView)activity.findViewById(R.id.num_correct_profit_calculations);
			assertEquals("0/3", numCorrectDisplay.getText());
		}
		catch (NullPointerException e){
			System.out.println("NullPointerException caught in ProfitCalculationsTester " +
					"onCheckProfitCalculationsButtonClick().");
		}

		final EditText totalCosts = (EditText)activity.findViewById(R.id.calc_totalCost);
		final TextView totalRevenue = (TextView)activity.findViewById(R.id.calc_totalRevenueProfitEq);
		final EditText netProfit = (EditText)activity.findViewById(R.id.calc_profit);
		final TextView profitFromEq = (TextView)activity.findViewById(R.id.calc_profitFromEq);
		final EditText finalCashBox = (EditText)activity.findViewById(R.id.calc_finalCashBox);
		
		TextView totalCostProfitEq = (TextView)activity.findViewById(R.id.calc_totalCostProfitEq);
		assertEquals(totalCostProfitEq.getText(), totalCosts.getText().toString());
		assertEquals("$0.00", totalRevenue.getText());
		
		try{
			activity.runOnUiThread(new Runnable(){
				public void run(){
					totalCosts.setText("$60.00");
					netProfit.setText("-$60.00");
					finalCashBox.setText("-$10.00");
					checkProfitButton = (Button)activity.findViewById(R.id.checkProfitCalculationsBtn);
					checkProfitButton.performClick();
				}
			});
			getInstrumentation().waitForIdleSync();

			assertEquals(profitFromEq.getText(), netProfit.getText().toString());
			
			numCorrectDisplay = (TextView)activity.findViewById(R.id.num_correct_profit_calculations);
			assertEquals("3/3", numCorrectDisplay.getText());
		}
		catch (NullPointerException e){
			System.out.println("NullPointerException caught in ProfitCalculationsTester " +
					"onCheckProfitCalculationsButtonClick().");
		}

		activity.finish();
	}
	
	public void testProfitCalculationsButtonClick() {
		ActivityMonitor activityMonitor = 
				getInstrumentation().addMonitor(FinalSalesSummaryActivity.class.getName(), null, false);
		
		try{
			activity.runOnUiThread(new Runnable(){
				public void run(){
					Button finishButton = (Button)activity.findViewById(R.id.finishBtn);
					finishButton.performClick();
				}
			});
			getInstrumentation().waitForIdleSync();
			
			final Context context = this.getActivity();
			dh = DatabaseHandler.getInstance(context);
			currentStand = dh.getCurrentFruitStand();
			
			assertNotNull(currentStand);
			
			Totals totals = currentStand.getTotals(context);
			assertEquals(60.0, totals.cost);
			assertEquals(0.0, totals.revenue);
			assertEquals(-10.0, totals.final_cash);
			
			// next activity is opened and captured.
			FinalSalesSummaryActivity nextActivity = 
					(FinalSalesSummaryActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
			assertNotNull(nextActivity);
			nextActivity.finish();
		}
		catch (NullPointerException e){
			System.out.println("NullPointerException caught in ProfitCalculationsTester " +
					"onCheckFinishButtonClick().");
		}
		activity.finish();
	}
}