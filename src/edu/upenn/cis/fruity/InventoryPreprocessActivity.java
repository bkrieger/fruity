package edu.upenn.cis.fruity;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

/**
 * User can input quantities for whole fruit inventory before fruit is processed and sold
 */
public class InventoryPreprocessActivity extends Activity {
	private int numApples, numPears, numOranges, numBananas, numGrapes, numKiwis;
	private int numGranola, numFrozenFruitBags;
	public static final int InventoryPreprocessActivity_ID = 9;

	private ParseInputData parser = new ParseInputData();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_preprocess);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	} 
	
	public void onBackPressed() {
		//back not allowed
	}

	
	public void onInventoryPostprocessButtonClick(View view){
		parseNumberStartInventoryInput();
		saveStartInventoryData();

		Intent i = new Intent(this,InventoryPostprocessActivity.class);
		i.putExtra("numGranola", numGranola);
		startActivityForResult(i, InventoryPreprocessActivity_ID);
	}
	
	/**
	 * Parse the number of start inventory items from input, before processing
	 */
	public void parseNumberStartInventoryInput(){
		numApples = parser.parseItemNum((EditText)findViewById(R.id.num_inv_preprocess_apple));
		numPears = parser.parseItemNum((EditText)findViewById(R.id.num_inv_preprocess_pear));
		numOranges = parser.parseItemNum((EditText)findViewById(R.id.num_inv_preprocess_orange));
		numBananas = parser.parseItemNum((EditText)findViewById(R.id.num_inv_preprocess_banana));
		numGrapes = parser.parseItemNum((EditText)findViewById(R.id.num_inv_preprocess_grapes));
		numKiwis = parser.parseItemNum((EditText)findViewById(R.id.num_inv_preprocess_kiwi));
		numGranola = parser.parseItemNum((EditText)findViewById(R.id.num_inv_preprocess_granola));
		numFrozenFruitBags = parser.parseItemNum((EditText)findViewById(R.id.num_inv_preprocess_frozenFruitBag));
	}
	
	/**
	 * Save the number of each start inventory item to the database
	 */
	public void saveStartInventoryData(){
		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		FruitStand currentStand = dh.getCurrentFruitStand();
		
		currentStand.addStartInventoryItem(this, "apple", numApples);
		currentStand.addStartInventoryItem(this, "pear", numPears);
		currentStand.addStartInventoryItem(this, "orange", numOranges);
		currentStand.addStartInventoryItem(this, "banana", numBananas);
		currentStand.addStartInventoryItem(this, "grapes", numGrapes);
		currentStand.addStartInventoryItem(this, "kiwi", numKiwis);
		currentStand.addStartInventoryItem(this, "granola", numGranola);
		currentStand.addStartInventoryItem(this, "frozenFruitBag", numFrozenFruitBags);	
	}
}