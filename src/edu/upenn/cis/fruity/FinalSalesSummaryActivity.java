package edu.upenn.cis.fruity;

import com.parse.Parse;
import com.parse.ParseObject;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.EndInventoryItem;
import edu.upenn.cis.fruity.database.FruitStand;
import edu.upenn.cis.fruity.database.ProcessedInventoryItem;
import edu.upenn.cis.fruity.database.Purchase;
import edu.upenn.cis.fruity.database.StaffMember;
import edu.upenn.cis.fruity.database.StartInventoryItem;
import edu.upenn.cis.fruity.database.Totals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FinalSalesSummaryActivity extends Activity {
	private static final int FinalSalesSummary_ID = 777;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculations_final_summary);
		TextView readyText = (TextView) findViewById(R.id.readyText);
		readyText.setText("Sales and stuff!");

		Parse.initialize(this, "1Pytm1PUnjYg3fiiuFsnwXjVIYK4gXEYcSuK4WA3",
				"4UMJgePt8yhCABvpI0Lnve57CqAXUldBJ3u45VZv");
	}

	public void onBackPressed() {
		// back not allowed
	}

	public void onFinishedButtonClick(View view) {
		saveStandToParse();

		Intent i = new Intent(this, MainActivity.class);
		startActivityForResult(i, FinalSalesSummary_ID);
	}

	private void saveStandToParse() {
		DatabaseHandler dh = DatabaseHandler.getInstance(this);

		FruitStand stand = dh.getCurrentFruitStand();
		ParseObject PFruitStand = new ParseObject("FruitStand");
		PFruitStand.put("school", stand.school);
		PFruitStand.put("date", stand.date);
		PFruitStand.put("temperature", stand.temperature);
		PFruitStand.put("weather", stand.weather);
		PFruitStand.put("initial_cash", stand.initial_cash);
		PFruitStand.put("stand_cost", stand.stand_cost);
		PFruitStand.put("smoothie_cost", stand.smoothie_cost);
		PFruitStand.put("additional_cost", stand.additional_cost);
		PFruitStand.saveInBackground();

		Totals totals = stand.getTotals(this);
		if (totals != null) {
			ParseObject PTotals = new ParseObject("Totals");
			PTotals.put("parent", PFruitStand);
			PTotals.put("cost", totals.cost);
			PTotals.put("revenue", totals.revenue);
			PTotals.put("final_cash", totals.final_cash);
			PTotals.saveInBackground();
		}

		StaffMember[] staff = stand.getStaffMembers(this);
		for (StaffMember s : staff) {
			ParseObject PStaffMember = new ParseObject("StaffMember");
			PStaffMember.put("parent", PFruitStand);
			PStaffMember.put("name", s.name);
			PStaffMember.put("is_volunteer", s.is_volunteer);
			PStaffMember.saveInBackground();
		}

		StartInventoryItem[] startInventory = stand
				.getStartInventoryItems(this);
		for (StartInventoryItem i : startInventory) {
			ParseObject PItem = new ParseObject("StartInventoryItem");
			PItem.put("parent", PFruitStand);
			PItem.put("item_name", i.item_name);
			PItem.put("count", i.count);
			PItem.saveInBackground();
		}

		ProcessedInventoryItem[] processedInventory = stand
				.getProcessedInventoryItems(this);
		for (ProcessedInventoryItem i : processedInventory) {
			ParseObject PItem = new ParseObject("ProcessedInventoryItem");
			PItem.put("parent", PFruitStand);
			PItem.put("item_name", i.item_name);
			PItem.put("count", i.count);
			PItem.put("price", i.price);
			PItem.saveInBackground();
		}

		EndInventoryItem[] endInventory = stand.getEndInventoryItems(this);
		for (EndInventoryItem i : endInventory) {
			ParseObject PItem = new ParseObject("EndInventoryItem");
			PItem.put("parent", PFruitStand);
			PItem.put("item_name", i.item_name);
			PItem.put("count", i.count);
			PItem.saveInBackground();
		}

		Purchase[] purchases = stand.getPurchases(this);
		for (Purchase p : purchases) {
			ParseObject PPurchase = new ParseObject("Purchase");
			PPurchase.put("parent", PFruitStand);
			PPurchase.put("item_name", p.item_name);
			PPurchase.put("count", p.count);
			PPurchase.put("num_coupons", p.num_coupons);
			PPurchase.put("num_tradeins", p.num_tradeins);
			PPurchase.put("amount_cash", p.amount_cash);
			PPurchase.put("customer", p.customer);
			PPurchase.saveInBackground();
		}

	}
}