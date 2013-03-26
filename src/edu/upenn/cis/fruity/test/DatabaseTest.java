package edu.upenn.cis.fruity.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import edu.upenn.cis.fruity.MainActivity;
import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.EndInventoryItem;
import edu.upenn.cis.fruity.database.FruitStand;
import edu.upenn.cis.fruity.database.ProcessedInventoryItem;
import edu.upenn.cis.fruity.database.Purchase;
import edu.upenn.cis.fruity.database.StaffMember;
import edu.upenn.cis.fruity.database.StartInventoryItem;
import edu.upenn.cis.fruity.database.Totals;

public class DatabaseTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private Context context;
	private DatabaseHandler dht;

	public DatabaseTest() {
		super("edu.upenn.cis.fruity", MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		context = this.getActivity();
		dht = DatabaseHandler.getInstance(context);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testDatabase() {
		FruitStand stand = new FruitStand("School Name", "3/2/2013", 75, "Sunny", 125.25, 50.0, 20.0, 0.0);
		dht.putFruitStand(stand);
		FruitStand currentStand = dht.getCurrentFruitStand();
		assertEquals(stand.school, currentStand.school);

		// To add totals to our current fruit stand, use:
		currentStand.addTotals(context, 100.0, 200.0, 300.0);
		// To get the totals for our current fruit stand (perhaps somewhere else in the app), use:
		Totals totals = currentStand.getTotals(context);

		assertEquals(100.0, totals.cost);
		assertTrue(currentStand.addStaffMember(context, "Brandon", true));
		assertTrue(currentStand.addStaffMember(context, "Dr. Murphy", false));
		StaffMember[] staffMembers = currentStand.getStaffMembers(context);

		for (StaffMember m : staffMembers) {
			if (m.name.equals("Brandon"))
				assertEquals(m.is_volunteer, true);
			else
				assertEquals(m.is_volunteer, false);
		}

		assertTrue(currentStand.addStartInventoryItem(context, "apple", 4));
		assertFalse(currentStand.addStartInventoryItem(context, "apple", 3)); // should fail
		assertTrue(currentStand.addStartInventoryItem(context, "orange", 7));
		StartInventoryItem[] startItems = currentStand.getStartInventoryItems(context);
		for (StartInventoryItem i : startItems) {
			if (i.item_name.equals("apple"))
				assertEquals(i.count, 4);
			else
				assertEquals(i.count, 7);
		}

		assertTrue(currentStand.addProcessedInventoryItem(context, "apple", 4, .75));
		assertFalse(currentStand.addProcessedInventoryItem(context, "apple", 3, .5)); // should fail
		assertTrue(currentStand.addProcessedInventoryItem(context, "orange", 7, .2));
		ProcessedInventoryItem[] pItems = currentStand.getProcessedInventoryItems(context);
		for (ProcessedInventoryItem i : pItems) {
			if (i.item_name.equals("apple")) {
				assertEquals(i.count, 4);
				assertEquals(i.price, .75);
			} else {
				assertEquals(i.count, 7);
				assertEquals(i.price, .2);
			}
		}

		assertTrue(currentStand.addEndInventoryItem(context, "apple", 4));
		assertFalse(currentStand.addEndInventoryItem(context, "apple", 3)); // should fail
		assertTrue(currentStand.addEndInventoryItem(context, "orange", 7));

		EndInventoryItem[] endItems = currentStand.getEndInventoryItems(context);
		for (EndInventoryItem i : endItems) {
			if (i.item_name.equals("apple"))
				assertEquals(i.count, 4);
			else
				assertEquals(i.count, 7);
		}

		assertTrue(currentStand.addPurchase(context, "apple", 4, 1, 0, 2.25, "Boy in Grade 6"));
		assertTrue(currentStand.addPurchase(context, "apple", 2, 1, 0, .75, "Girl in Grade 6"));
		assertTrue(currentStand.addPurchase(context, "orange", 2, 0, 0, .40, "Girl in Grade 6"));

		Purchase[] purchases = currentStand.getPurchases(context);
		assertEquals(purchases[0].amount_cash, 2.25);
		assertEquals(purchases[1].amount_cash, .75);
		assertEquals(purchases[2].amount_cash, .40);
	}
}