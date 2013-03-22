package edu.upenn.cis.fruity.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import edu.upenn.cis.fruity.InventoryPreprocessActivity;
import edu.upenn.cis.fruity.R;
import edu.upenn.cis.fruity.SetupStandInfoActivity;
import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;

public class StandInfoTester extends ActivityInstrumentationTestCase2<SetupStandInfoActivity> {

	protected Activity activity;
	private Button button;
	private Spinner spinner;
	private SeekBar seekbar;
	private EditText cashBoxText;
	private EditText standCostText;
	private EditText smoothieCostText;
	private EditText additionalCostText;
	private DatabaseHandler dh;
	private FruitStand currentStand;
	
	public static final int InventoryPreprocessActivity_ID = 8;

	public StandInfoTester(){
		super("edu.upenn.cis.fruity", SetupStandInfoActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		
		activity = getActivity();
		button = (Button)activity.findViewById(R.id.inventoryBtn);
		spinner = (Spinner)activity.findViewById(R.id.standInfo_weatherInput);
		seekbar = (SeekBar)activity.findViewById(R.id.standInfo_temperatureInput);
		cashBoxText = (EditText)activity.findViewById(R.id.standInfo_cashBoxInput);
		standCostText = (EditText)activity.findViewById(R.id.standInfo_fruitStandCostInput);
		smoothieCostText = (EditText)activity.findViewById(R.id.standInfo_smoothieCostInput);
		additionalCostText = (EditText)activity.findViewById(R.id.standInfo_additionalCostsInput);
	}

	protected void tearDown() throws Exception{
		super.tearDown();
	}
	
	// User interface tests
	
	public void testSliderClick() throws Exception {
		try{
		activity.runOnUiThread(new Runnable(){
			public void run(){
				seekbar.performLongClick();
			}
		});
		getInstrumentation().waitForIdleSync();
		
		TextView tempView = (TextView)activity.findViewById(R.id.standInfo_temperatureText);
		assertEquals(seekbar.getProgress() + "°F", tempView.getText());
		}
		catch(NullPointerException e){
			System.out.println("NullPointerException caught in StandInfoTester.testSliderClick().");
		}
	}
	
	public void testSpinnerClick() throws Exception {
		try{
		activity.runOnUiThread(new Runnable(){
			public void run(){
			}
		});
		getInstrumentation().waitForIdleSync();
		
		assertTrue(spinner.isClickable());
		
		}
		catch(NullPointerException e){
			System.out.println("NullPointerException caught in StandInfoTester.testSpinnerClick().");
		}
	}
		
	public void testDate(){
		try{
		activity.runOnUiThread(new Runnable(){
			public void run(){
			}
		});
		getInstrumentation().waitForIdleSync();
		
		TextView dateView = (TextView)activity.findViewById(R.id.standInfo_dateField);
		
		String s = (String)dateView.getText();
		String tokens[] = s.split("/");
		assertEquals(tokens.length,3);
		}
		catch(Exception e){
			System.out.println("NullPointerException caught in StandInfoTester.testDate().");
		}
	}

	public void testCashBoxTyping(){
		assertNotNull(cashBoxText);
		
		try{
		activity.runOnUiThread(new Runnable(){
			public void run(){
				cashBoxText.requestFocus();
			}
		});
		getInstrumentation().waitForIdleSync();
		sendKeys("5"); // because already has a 0 in the box

		}
		catch(Exception e){
			System.out.println("NullPointerException caught in StandInfoTester.testCashBoxTyping().");
		}
		assertEquals("50", cashBoxText.getText().toString());
	}
	
	public void testStandCostTyping(){
		assertNotNull(standCostText);
		
		try{
		activity.runOnUiThread(new Runnable(){
			public void run(){
				standCostText.requestFocus();
			}
		});
		getInstrumentation().waitForIdleSync();
		sendKeys("1"); // because already has a 0 in the box

		}
		catch(Exception e){
			System.out.println("NullPointerException caught in StandInfoTester.testStandCostTyping().");
		}
		assertEquals("10", standCostText.getText().toString());
	}
	
	public void testSmoothieCostTyping(){
		assertNotNull(smoothieCostText);
		
		try{
		activity.runOnUiThread(new Runnable(){
			public void run(){
				smoothieCostText.requestFocus();
			}
		});
		getInstrumentation().waitForIdleSync();
		sendKeys("2"); // because already has a 0 in the box

		}
		catch(Exception e){
			System.out.println("NullPointerException caught in StandInfoTester.testsmoothieCostTyping().");
		}
		assertEquals("20", smoothieCostText.getText().toString());
	}
	
	public void testAdditionalCostTyping(){
		assertNotNull(additionalCostText);
		
		try{
		activity.runOnUiThread(new Runnable(){
			public void run(){
				additionalCostText.requestFocus();
			}
		});
		getInstrumentation().waitForIdleSync();
		sendKeys("3"); // because already has a 0 in the box

		}
		catch(Exception e){
			System.out.println("NullPointerException caught in StandInfoTester.testAdditionalCostTyping().");
		}
		assertEquals("30", additionalCostText.getText().toString());
	}

	public void testZPreprocessButtonClickAndSaving() {	
		ActivityMonitor activityMonitor = 
				getInstrumentation().addMonitor(InventoryPreprocessActivity.class.getName(), null, false);

		try{
			activity.runOnUiThread(new Runnable(){
				public void run(){
					cashBoxText.setText("50");
					standCostText.setText("10");
					smoothieCostText.setText("20");
					additionalCostText.setText("30");
					button.performClick();
				}
			});
			getInstrumentation().waitForIdleSync();
			
			assertFalse(activity.hasWindowFocus());
			
			final Context context = this.getActivity();
			dh = DatabaseHandler.getInstance(context);
			currentStand = dh.getCurrentFruitStand();
			
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy", Locale.US);
			
			assertNotNull(currentStand);
			assertEquals(currentStand.school, "Filler Text");
			assertEquals(currentStand.temperature, 0);
			assertEquals(currentStand.weather, "Sunny");
			assertEquals(currentStand.date, dateFormat.format(date));
			assertEquals(currentStand.initial_cash, 50.0);
			assertEquals(currentStand.stand_cost, 10.0);
			assertEquals(currentStand.smoothie_cost, 20.0);
			assertEquals(currentStand.additional_cost, 30.0);
			
			 // next activity is opened and captured.
			InventoryPreprocessActivity nextActivity = 
					(InventoryPreprocessActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
			assertNotNull(nextActivity);
		}
		catch (NullPointerException e){
			System.out.println("NullPointerException caught in StandInfoTester testPreprocessButtonClick().");
		}
	}
}