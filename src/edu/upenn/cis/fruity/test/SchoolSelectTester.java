package edu.upenn.cis.fruity.test;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import edu.upenn.cis.fruity.EnterStaffActivity;
import edu.upenn.cis.fruity.R;
import edu.upenn.cis.fruity.SetupInputSchoolActivity;
import edu.upenn.cis.fruity.SetupSchoolSelectActivity;

public class SchoolSelectTester extends ActivityInstrumentationTestCase2<SetupSchoolSelectActivity> {

	protected Activity activity;
	private ListView schoolListView;

	public SchoolSelectTester(){
		super("edu.upenn.cis.fruity", SetupSchoolSelectActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
		schoolListView = (ListView)activity.findViewById(R.id.list_view_schools);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
    }
	
	public void testSchoolNameSelectClick() {
		final String schoolName = "Locke Elementary";
		final int pos = 1;
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(EnterStaffActivity.class.getName(), null, false);

		activity.runOnUiThread(new Runnable(){
			public void run(){
				Object item = schoolListView.getItemAtPosition(pos);
				assertEquals(item.toString(), schoolName);
				boolean clicked = schoolListView.performItemClick(schoolListView.getAdapter().getView(pos, null, null), pos, pos);
				assertTrue(clicked);
			}
		});
		getInstrumentation().waitForIdleSync();

		EnterStaffActivity nextActivity = (EnterStaffActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
		assertNotNull(nextActivity);
		nextActivity.finish();
	}
	
	public void testSchoolOtherSelectClick() {
		final String schoolName = "Other";
		int numItems = schoolListView.getCount();
		final int pos = numItems - 1;
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(SetupInputSchoolActivity.class.getName(), null, false);

		activity.runOnUiThread(new Runnable(){
			public void run(){
				Object item = schoolListView.getItemAtPosition(pos);
				assertEquals(item.toString(), schoolName);
				boolean clicked = schoolListView.performItemClick(schoolListView.getAdapter().getView(pos, null, null), pos, pos);
				assertTrue(clicked);
			}
		});
		getInstrumentation().waitForIdleSync();

		SetupInputSchoolActivity nextActivity = (SetupInputSchoolActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
		assertNotNull(nextActivity);
		nextActivity.finish();
	}
}