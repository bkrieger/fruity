package edu.upenn.cis.fruity.test;



import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import edu.upenn.cis.fruity.R;
import edu.upenn.cis.fruity.SetupSchoolSelectActivity;

public class SchoolSelectTester extends
		ActivityInstrumentationTestCase2<SetupSchoolSelectActivity> {

	protected Activity activity;
	private Button button;
	
	// no argument constructor
	public SchoolSelectTester(){
		super("edu.upenn.cis.fruity", SetupSchoolSelectActivity.class);
	}
	
	//@Before
	protected void setUp() throws Exception {
		super.setUp();
		activity = this.getActivity();
		button = (Button)activity.findViewById(R.id.inventoryBtn);
	}

	//@Test
	public void testPreprocessButtonClick() {
		try{
		activity.runOnUiThread(new Runnable(){
			public void run(){
				button.performClick();
			}
		});
		getInstrumentation().waitForIdleSync();
		
		assertFalse(activity.hasWindowFocus());
		}
		catch(NullPointerException e){
			System.out.println("NullPointerException caught in SchoolSelectTester.testPreprocessButtonClick().");
		}
	}

}
