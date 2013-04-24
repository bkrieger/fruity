package edu.upenn.cis.fruity;

import java.util.HashSet;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;
import edu.upenn.cis.fruity.database.ProcessedInventoryItem;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SalesSelectionActivity extends Activity {
	public static final int SalesSelectionActivity_ID = 12;
	private HashSet<String> availableItems;
	
	// TODO: Make it so we can pull the "other" entered at beginning
	// from fruitstand instance and put here.
	int age_category;
	boolean isMale;
	int mixedBags;
	int smoothies;
	int granola;
	int apples;
	int oranges;
	int grapes;
	int pears;
	int bananas;
	int kiwis;
	int other1;
	int other2;
	int total;
	boolean onPopup;
	
	String other1name;
	String other2name;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_item_selection);

		// Pre-process which whole fruits are available. 
		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		FruitStand currentStand = dh.getCurrentFruitStand();
		ProcessedInventoryItem[] currentItems = currentStand.getProcessedInventoryItems(this);
		availableItems = new HashSet<String>();
		for (ProcessedInventoryItem i : currentItems) {
			if (i.count > 0) { 
				availableItems.add(i.item_name);
				if (i.item_name.startsWith("other1:")) other1name = i.item_name.substring(7);
				if (i.item_name.startsWith("other2:")) other2name = i.item_name.substring(7);
			}
		}
		
		Intent intent = getIntent();
		age_category = intent.getIntExtra("age_category", -1);
		isMale = intent.getBooleanExtra("isMale", true);

		mixedBags = 0;
		smoothies = 0;
		granola = 0;
		apples = 0;
		oranges = 0;
		grapes = 0;
		pears = 0;
		bananas = 0;
		kiwis = 0;
		total = 0;
		other1 = 0;
		other2 = 0;
		onPopup = false;
		
		putBackCounts();
	}
	
	

	public void onBackPressed() {
		if (onPopup) {
			setContentView(R.layout.activity_sales_item_selection);
			putBackCounts();
			onPopup = false;
		} else {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Purchase Cancelled", Toast.LENGTH_SHORT);
			toast.show();
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sales_payment, menu);
		return true;
	}

	public void onWholeFruitButtonClick(View v) {
		setContentView(R.layout.sales_whole_fruit_popup);
		onPopup = true;
		LinearLayout layout;
		TextView count;
		
		// Initialize Fruit Counters
		if (availableItems.contains("apple")) {
			count = (TextView) findViewById(R.id.SWFPAppleCounter);
			count.setText("" + apples);
		} else {
			layout = (LinearLayout) findViewById(R.id.AppleRow);
			layout.setVisibility(View.GONE);
		}
		
		if (availableItems.contains("orange")) {
			count = (TextView) findViewById(R.id.SWFPOrangeCounter);
			count.setText("" + oranges);
		} else {
			layout = (LinearLayout) findViewById(R.id.OrangeRow);
			layout.setVisibility(View.GONE);
		}
		
		if (availableItems.contains("banana")) {
			count = (TextView) findViewById(R.id.SWFPBananaCounter);
			count.setText("" + bananas);
		} else {
			layout = (LinearLayout) findViewById(R.id.BananaRow);
			layout.setVisibility(View.GONE);
		}
		
		if (availableItems.contains("kiwi")) {
			count = (TextView) findViewById(R.id.SWFPKiwiCounter);
			count.setText("" + kiwis);
		} else {
			layout = (LinearLayout) findViewById(R.id.KiwiRow);
			layout.setVisibility(View.GONE);
		}
		
		if (availableItems.contains("pear")) {
			count = (TextView) findViewById(R.id.SWFPPearCounter);
			count.setText("" + pears);
		} else {
			layout = (LinearLayout) findViewById(R.id.PearRow);
			layout.setVisibility(View.GONE);
		}
		
		if (availableItems.contains("grapes")) {
			count = (TextView) findViewById(R.id.SWFPGrapeCounter);
			count.setText("" + grapes);
		} else {
			layout = (LinearLayout) findViewById(R.id.GrapeRow);
			layout.setVisibility(View.GONE);
		}
	}

	public void onApplesMinusButtonClick(View v) {
		if (apples > 0) {
			apples--;
			total--;
		}
		TextView count = (TextView) findViewById(R.id.SWFPAppleCounter);
		count.setText("" + apples);
	}

	public void onApplesPlusButtonClick(View v) {
		if (apples < 99) {
			apples++;
			total++;
		}
		TextView count = (TextView) findViewById(R.id.SWFPAppleCounter);
		count.setText("" + apples);
	}

	public void onOrangesMinusButtonClick(View v) {
		if (oranges > 0) {
			oranges--;
			total--;
		}
		TextView count = (TextView) findViewById(R.id.SWFPOrangeCounter);
		count.setText("" + oranges);
	}

	public void onOrangesPlusButtonClick(View v) {
		if (oranges < 99) {
			oranges++;
			total++;
		}
		TextView count = (TextView) findViewById(R.id.SWFPOrangeCounter);
		count.setText("" + oranges);
	}

	public void onBananasMinusButtonClick(View v) {
		if (bananas > 0) {
			bananas--;
			total--;
		}
		TextView count = (TextView) findViewById(R.id.SWFPBananaCounter);
		count.setText("" + bananas);
	}

	public void onBananasPlusButtonClick(View v) {
		if (bananas < 99) {
			bananas++;
			total++;
		}
		TextView count = (TextView) findViewById(R.id.SWFPBananaCounter);
		count.setText("" + bananas);
	}

	public void onKiwisMinusButtonClick(View v) {
		if (kiwis > 0) {
			kiwis--;
			total--;
		}
		TextView count = (TextView) findViewById(R.id.SWFPKiwiCounter);
		count.setText("" + kiwis);
	}

	public void onKiwisPlusButtonClick(View v) {
		if (kiwis < 99) {
			kiwis++;
			total++;
		}
		TextView count = (TextView) findViewById(R.id.SWFPKiwiCounter);
		count.setText("" + kiwis);
	}

	public void onPearsMinusButtonClick(View v) {
		if (pears > 0) {
			pears--;
			total--;
		}
		TextView count = (TextView) findViewById(R.id.SWFPPearCounter);
		count.setText("" + pears);
	}

	public void onPearsPlusButtonClick(View v) {
		if (pears < 99) {
			pears++;
			total++;
		}
		TextView count = (TextView) findViewById(R.id.SWFPPearCounter);
		count.setText("" + pears);
	}

	public void onGrapesMinusButtonClick(View v) {
		if (grapes > 0) {
			grapes--;
			total--;
		}
		TextView count = (TextView) findViewById(R.id.SWFPGrapeCounter);
		count.setText("" + grapes);
	}

	public void onGrapesPlusButtonClick(View v) {
		if (grapes < 99) {
			grapes++;
			total++;
		}
		TextView count = (TextView) findViewById(R.id.SWFPGrapeCounter);
		count.setText("" + grapes);
	}

	public void onMixedMinusButtonClick(View v) {
		if (mixedBags > 0) {
			mixedBags--;
			total--;
		}
		TextView count = (TextView) findViewById(R.id.ASPmixedCounter);
		count.setText("" + mixedBags);
	}

	public void onMixedPlusButtonClick(View v) {
		if (mixedBags < 99) {
			mixedBags++;
			total++;
		}
		TextView count = (TextView) findViewById(R.id.ASPmixedCounter);
		count.setText("" + mixedBags);
	}

	public void onSmoothieMinusButtonClick(View v) {
		if (smoothies > 0) {
			smoothies--;
			total--;
		}
		TextView count = (TextView) findViewById(R.id.ASPsmoothieCounter);
		count.setText("" + smoothies);
	}

	public void onSmoothiePlusButtonClick(View v) {
		if (smoothies < 99) {
			smoothies++;
			total++;
		}
		TextView count = (TextView) findViewById(R.id.ASPsmoothieCounter);
		count.setText("" + smoothies);
	}
	
	public void onGranolaMinusButtonClick(View v) {
		if (granola > 0) {
			granola--;
			total--;
		}
		TextView count = (TextView) findViewById(R.id.ASPgranolaCounter);
		count.setText("" + granola);
	}

	public void onGranolaPlusButtonClick(View v) {
		if (granola < 99) {
			granola++;
			total++;
		}
		TextView count = (TextView) findViewById(R.id.ASPgranolaCounter);
		count.setText("" + granola);
	}
	
	public void onOther1MinusButtonClick(View v) {
		if (other1 > 0) {
			other1--;
			total--;
		}
		TextView count = (TextView) findViewById(R.id.ASPOther1Counter);
		count.setText("" + other1);
	}

	public void onOther1PlusButtonClick(View v) {
		if (other1 < 99) {
			other1++;
			total++;
		}
		TextView count = (TextView) findViewById(R.id.ASPOther1Counter);
		count.setText("" + other1);
	}
	
	public void onOther2MinusButtonClick(View v) {
		if (other2 > 0) {
			other2--;
			total--;
		}
		TextView count = (TextView) findViewById(R.id.ASPOther2Counter);
		count.setText("" + other2);
	}

	public void onOther2PlusButtonClick(View v) {
		if (other1 < 99) {
			other2++;
			total++;
		}
		TextView count = (TextView) findViewById(R.id.ASPOther2Counter);
		count.setText("" + other2);
	}

	public void onWholeFruitDoneButtonClick(View v) {
		setContentView(R.layout.activity_sales_item_selection);
		putBackCounts();
		onPopup = false;
	}
	

	private void putBackCounts() {
		LinearLayout layout;
		Button labelButton;
		TextView count;
		
		if (availableItems.contains("mixedBag")) {
			count = (TextView) findViewById(R.id.ASPmixedCounter);
			count.setText("" + mixedBags);
		} else {
			layout = (LinearLayout) findViewById(R.id.ASPMixedRow);
			layout.setVisibility(View.GONE);
		}
		
		if (availableItems.contains("smoothie")) {
			count = (TextView) findViewById(R.id.ASPsmoothieCounter);
			count.setText("" + smoothies);
		} else {
			layout = (LinearLayout) findViewById(R.id.ASPSmoothieRow);
			layout.setVisibility(View.GONE);
		}
		
		if (availableItems.contains("granola")) {
			count = (TextView) findViewById(R.id.ASPgranolaCounter);
			count.setText("" + smoothies);
		} else {
			layout = (LinearLayout) findViewById(R.id.ASPGranolaRow);
			layout.setVisibility(View.GONE);
		}
		
		if (other1name != null && availableItems.contains("other1:" + other1name)) {
			count = (TextView) findViewById(R.id.ASPOther1Counter);
			labelButton = (Button) findViewById(R.id.ASPbtnOther1);
			labelButton.setText(other1name);
			count.setText("" + other1);
		} else {
			layout = (LinearLayout) findViewById(R.id.ASPOther1Row);
			layout.setVisibility(View.GONE);
		}
		
		if (other2name != null && availableItems.contains("other2:" + other2name)) {
			count = (TextView) findViewById(R.id.ASPOther2Counter);
			labelButton = (Button) findViewById(R.id.ASPbtnOther2);
			labelButton.setText(other2name);
			count.setText("" + other2);
		} else {
			layout = (LinearLayout) findViewById(R.id.ASPOther2Row);
			layout.setVisibility(View.GONE);
		}
	}
	
	
	// TODO: Transfer information about "other" categories for stand
	public void onPaymentClick(View view){
		Intent i = new Intent(this, SalesPaymentActivity.class);
		
		i.putExtra("ageCategory", age_category);
		i.putExtra("isMale", isMale);
		i.putExtra("mixedBag", mixedBags);
		i.putExtra("smoothie", smoothies);
		i.putExtra("granola", granola);
		i.putExtra("apple", apples);
		i.putExtra("orange", oranges);
		i.putExtra("grapes", grapes);
		i.putExtra("pear", pears);
		i.putExtra("banana", bananas);
		i.putExtra("kiwi", kiwis);
		i.putExtra("other1:" + other1name, other1);
		i.putExtra("other2:" + other2name, other2);
		i.putExtra("total", total);
		
		startActivityForResult(i, SalesSelectionActivity_ID);
	}
}
