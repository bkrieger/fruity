package edu.upenn.cis.fruity.test;



import android.app.Activity;
import android.content.Intent;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import edu.upenn.cis.fruity.R;
import edu.upenn.cis.fruity.SetupStandInfoActivity;

public class StandInfoTester extends
		ActivityInstrumentationTestCase2<SetupStandInfoActivity> {

	protected Activity activity;
	private Button button;
	private Spinner spinner;
	private SeekBar seekbar;
	private EditText cashBoxText;
	private EditText volunteerText;
	public static final int InventoryPreprocessActivity_ID = 8;
	
	// no argument constructor
	public StandInfoTester(){
		super("edu.upenn.cis.fruity", SetupStandInfoActivity.class);
	}
	
	//@Before
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		
		activity = getActivity();
		button = (Button)activity.findViewById(R.id.inventoryBtn);
		spinner = (Spinner)activity.findViewById(R.id.standInfo_weatherInput);
		seekbar = (SeekBar)activity.findViewById(R.id.standInfo_temperatureInput);
		cashBoxText = (EditText)activity.findViewById(R.id.standInfo_cashBoxInput);
	}

	protected void tearDown() throws Exception{
		super.tearDown();
	}
	
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
			System.out.println("NullPointerException caught in SchoolSelectTester.testSliderClick().");
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
			System.out.println("NullPointerException caught in SchoolSelectTester.testSpinnerClick().");
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
			System.out.println("NullPointerException caught in SchoolSelectTester.testDate().");
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
		sendKeys("5 0");

		}
		catch(Exception e){
			System.out.println("NullPointerException caught in SchoolSelectTester.testCashBoxTyping().");
		}
		assertEquals("50", cashBoxText.getText().toString());
	}

	public void testVolunteerTyping(){
		assertNotNull(cashBoxText);
		try{
		activity.runOnUiThread(new Runnable(){
			public void run(){
				volunteerText.requestFocus();
			}
		});
		getInstrumentation().waitForIdleSync();
		sendKeys("5");
		
		
		assertEquals("5", volunteerText.getText().toString());
		}
		catch(Exception e){
			System.out.println("NullPointerException caught in SchoolSelectTester.testVolunteerTyping().");
		}
	}
	
	public void testZPreprocessButtonClick() {
		
		try{
		activity.runOnUiThread(new Runnable(){
			public void run(){
				button.performClick();
			}
		});
		getInstrumentation().waitForIdleSync();
		
		assertFalse(activity.hasWindowFocus());
		
		}
		catch (NullPointerException e){
			System.out.println("NullPointerException caught in StandInfoTestertestPreprocessButtonClick().");
		}
	}
}
