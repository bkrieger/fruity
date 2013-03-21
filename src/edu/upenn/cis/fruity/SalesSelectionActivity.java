package edu.upenn.cis.fruity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SalesSelectionActivity extends Activity {
	public static final int SalesSelectionActivity_ID = 12;

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
	int total;
	boolean onPopup;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_item_selection);

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
		onPopup = false;
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
		TextView count = (TextView) findViewById(R.id.SWFPAppleCounter);
		count.setText("" + apples);
		count = (TextView) findViewById(R.id.SWFPOrangeCounter);
		count.setText("" + oranges);
		count = (TextView) findViewById(R.id.SWFPBananaCounter);
		count.setText("" + bananas);
		count = (TextView) findViewById(R.id.SWFPKiwiCounter);
		count.setText("" + kiwis);
		count = (TextView) findViewById(R.id.SWFPPearCounter);
		count.setText("" + pears);
		count = (TextView) findViewById(R.id.SWFPGrapeCounter);
		count.setText("" + grapes);
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

	public void onWholeFruitDoneButtonClick(View v) {
		setContentView(R.layout.activity_sales_item_selection);
		putBackCounts();
		onPopup = false;
	}

	private void putBackCounts() {
		TextView count = (TextView) findViewById(R.id.ASPmixedCounter);
		count.setText("" + mixedBags);
		count = (TextView) findViewById(R.id.ASPsmoothieCounter);
		count.setText("" + smoothies);
		count = (TextView) findViewById(R.id.ASPgranolaCounter);
		count.setText("" + granola);
	}
	
	// TODO: Transfer information about "other" categories for stand
	public void onPaymentClick(View view){
		Intent i = new Intent(this, SalesPaymentActivity.class);
		
		i.putExtra("ageCategory", age_category);
		i.putExtra("isMale", isMale);
		i.putExtra("mixedBag", mixedBags);
		i.putExtra("frozenFruitBag", smoothies);
		i.putExtra("granola", granola);
		i.putExtra("apple", apples);
		i.putExtra("orange", oranges);
		i.putExtra("grapes", grapes);
		i.putExtra("pear", pears);
		i.putExtra("banana", bananas);
		i.putExtra("kiwi", kiwis);
		i.putExtra("total", total);
		
		startActivityForResult(i, SalesSelectionActivity_ID);
	}
}
