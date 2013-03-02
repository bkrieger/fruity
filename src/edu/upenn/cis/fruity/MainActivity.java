package edu.upenn.cis.fruity;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.EndInventoryItem;
import edu.upenn.cis.fruity.database.FruitStand;
import edu.upenn.cis.fruity.database.ProcessedInventoryItem;
import edu.upenn.cis.fruity.database.Purchase;
import edu.upenn.cis.fruity.database.StaffMember;
import edu.upenn.cis.fruity.database.StartInventoryItem;
import edu.upenn.cis.fruity.database.Totals;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onStartButtonClick(View v) {
		Intent i = new Intent(this, SetupSchoolSelectActivity.class);
		startActivityForResult(i, 1);
	}

	public void onTestButtonClick(View v) {
		//Notes and Instruction on Database Usage from Brandon:
		
		/*
		 * The way I made the database, we never need to do a SQL query (YAY!).
		 * Just follow the instructions below, and we should be able to do anything we need.
		 */
		
		//In order to do anything with the database, you need the instance of DatabaseHandler
		// (I made it a singleton, so we can only ever have one.)
		//Get it as shown below. The argument is a Context, which is a superclass of Activity,
		//so just pass in "this"
		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		
		//In order to create a FruitStand, use this constructor. It takes the following arguments:
		//School, Date, Temperature, Weather, Initial Cash, Stand Cost, Smoothie Cost, Additional Cost
		FruitStand stand = new FruitStand("School Name", "3/2/2013", 75, "Sunny", 125.25, 50.0, 20.0, 0.0);
		//Then in order to save the fruit stand to the database, use the handler as shown below.
		dh.putFruitStand(stand);
		
		//Now, if we ever want to do anything with the database (in a different activity, for example)
		//we need the instance of FruitStand. We can get this from anywhere in the app using the following:
		FruitStand currentStand = dh.getCurrentFruitStand();
		
		//If we ever want to get a fruit stand for a specific id, we can use:
		FruitStand specificStand = dh.getFruitStand(1);
		
		//If we want an array of all fruit stands, we can use:
		FruitStand[] allStands = dh.getAllFruitStands();
		

		
		//These are pretty much the only times we need to use the DatabaseHandler directly.
		//Everything else is handled for you within the FruitStand class.
		
		/*
		 * The database schema includes the following tables:
		 * FruitStand
		 * 	id : integer
		 * 	school : text
		 * 	date : text
		 * 	temperature : integer
		 * 	weather : text
		 * 	initial_cash : real
		 * 	stand_cost : real
		 * 	smoothie_cost : real
		 * 	additional_cost : real
		 * 
		 * Totals
		 * 	id : integer
		 * 	fruit_stand_id : integer (unique)
		 * 	cost : real
		 * 	revenue : real
		 * 	final_cash : real
		 * 
		 * StaffMember
		 * 	id : integer
		 * 	fruit_stand_id : integer
		 * 	name : text
		 * 	is_volunteer : int (SQLite doesn't have bools, so this is always 0 for false or 1 for true)
		 * 
		 * StartInventoryItem
		 * 	id : integer
		 * 	fruit_stand_id : integer
		 * 	item_name : text (unique for a given fruit_stand_id)
		 * 	count : integer
		 * 
		 * ProcessedInventoryItem
		 * 	id : integer
		 * 	fruit_stand_id : integer
		 * 	item_name : text (unique for a given fruit_stand_id)
		 * 	count : integer
		 *  price : real
		 *  
		 * EndInventoryItem
		 *  id : integer
		 * 	fruit_stand_id : integer
		 * 	item_name : text (unique for a given fruit_stand_id)
		 * 	count : integer
		 * 
		 * Purchase
		 * 	id : integer
		 *  fruit_stand_id : integer
		 *  item_name : text
		 *  count : integer
		 *  num_coupons : integer
		 *  num_tradeins : integer
		 *  amount_cash : real
		 *  customer : text
		 * 
		 * 
		 * Everything in all of the tables must be associated with a FruitStand.
		 * So, you can always put things in these tables, or get things out of these tables
		 * 	using the FruitStand class.
		 * For example:
		 */
		
		//To add totals to our current fruit stand, use:
		currentStand.addTotals(this, 100.0, 200.0, 300.0);
		//To get the totals for our current fruit stand (perhaps somewhere else in the app), use:
		Totals totals = currentStand.getTotals(this);
		
		//Test to make sure it works
		System.out.println("100.0 = " + totals.cost);

		
		/*
		 * All the other tables can have multiple values for a single fruit stand.
		 * 
		 * Each row in the StaffMember table corresponds to a single staff member or volunteer,
		 * 	so a fruit stand can have many.
		 * 
		 * Each row in the StartInventoryItem table corresponds to a single item (i.e. an apple, or a smoothie).
		 * 	This easily allows addition of new fruit types, as the fruit type is just a string here.
		 * 
		 * The ProcessedInventoryItem and EndInventoryItem tables are very similar. 
		 * 	Start is used at the very beginning.
		 * 	Processed is after preprocessing.
		 * 	End is at the end of the day, if there is fruit leftover.
		 * 
		 * Each row in the purchase table is a single purchase for a single item.
		 * 	This means if a customer buys 1 apple and 4 bananas, we classify that as 2 separate
		 * 	purchases. (This will make it easier to analyze).
		 */
		
		
		/*
		 * The other tables can be accessed as follows.
		 * The System.out.printlns are there for my own testing purposes.
		 * We can easily turn those into JUnit tests.
		 */
		
		//test StaffMember
		System.out.println("Staff Member Tests");
		System.out.println(currentStand.addStaffMember(this, "Brandon", true));
		System.out.println(currentStand.addStaffMember(this, "Dr. Murphy", false));
		StaffMember[] staffMembers = currentStand.getStaffMembers(this);

		for (StaffMember m : staffMembers) {
			System.out.println(m.name);
			if(m.is_volunteer) 
				System.out.println("Volunteer");
			else 
				System.out.println("Staff");
		}
		
		//test StartInventoryItem
		System.out.println("Start Inventory Item Tests");
		System.out.println(currentStand.addStartInventoryItem(this, "apple", 4));
		System.out.println(!currentStand.addStartInventoryItem(this, "apple", 3)); //should fail
		System.out.println(currentStand.addStartInventoryItem(this, "orange", 7));
		StartInventoryItem[] startItems = currentStand.getStartInventoryItems(this);
		for(StartInventoryItem i : startItems) {
			System.out.println(i.item_name);
			System.out.println(i.count);
		}

		//test ProcessedInventoryItem
		System.out.println("Processed Inventory Item Tests");
		System.out.println(currentStand.addProcessedInventoryItem(this, "apple", 4, .75));
		System.out.println(!currentStand.addProcessedInventoryItem(this, "apple", 3, .5)); //should fail
		System.out.println(currentStand.addProcessedInventoryItem(this, "orange", 7, .2));
		ProcessedInventoryItem[] pItems = currentStand.getProcessedInventoryItems(this);
		for(ProcessedInventoryItem i : pItems) {
			System.out.println(i.item_name);
			System.out.println(i.count);
		}
		
		//test EndInventoryItem
		System.out.println("End Inventory Item Tests");
		System.out.println(currentStand.addEndInventoryItem(this, "apple", 4));
		System.out.println(!currentStand.addEndInventoryItem(this, "apple", 3)); //should fail
		System.out.println(currentStand.addEndInventoryItem(this, "orange", 7));
				
		EndInventoryItem[] endItems = currentStand.getEndInventoryItems(this);
		for(EndInventoryItem i : endItems) {
			System.out.println(i.item_name);
			System.out.println(i.count);
		}
		
		//test Purchase
		System.out.println("Purchase Tests");
		System.out.println(currentStand.addPurchase(this, "apple", 4, 1, 0, 2.25, "Boy in Grade 6"));
		System.out.println(currentStand.addPurchase(this, "apple", 2, 1, 0, .75, "Girl in Grade 6"));
		System.out.println(currentStand.addPurchase(this, "orange", 2, 0, 0, .40, "Girl in Grade 6"));
		
		Purchase[] purchases = currentStand.getPurchases(this);
		for(Purchase p : purchases) {
			System.out.println(p.item_name);
			System.out.println(p.count);
			System.out.println(p.num_coupons);
			System.out.println(p.num_tradeins);
			System.out.println(p.amount_cash);
			System.out.println(p.customer);
		}
	}
}
