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
 * User can input quantities for whole fruit, mixed bags, and other food inventory
 * after fruit is processed into mixed bags
 */
public class InventoryPostprocessActivity extends Activity {
	
	private int numApples, numPears, numOranges, numBananas, numGrapes, numKiwis;
	private int numMixedBags, numFrozenFruitBags, numGranola;
	private double applePrice, pearPrice, orangePrice, bananaPrice, grapesPrice, kiwiPrice;
	private double mixedBagPrice, frozenFruitBagPrice, granolaPrice;	
	public static final int InventoryPostprocessActivity_ID = 10;
	
	private ParseInputData parser = new ParseInputData();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_postprocess);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	} 
	
	// TODO: implement
	public void onFruitPricesButtonClick(View view){

	}
	
	/**
	 * When click on button, parse input and save to database
	 * @param view
	 */
	public void onInventorySalesDemographicsButtonClick(View view){
		parseNumberProcessedInventoryInput();
		parsePricePerProcessedInventoryItemInput();
		saveProcessedInventoryData();
		
		Intent i = new Intent(this,SalesSummaryActivity.class);
		startActivityForResult(i, InventoryPostprocessActivity_ID);
	}
	
	/**
	 * Parse the number of processed inventory items from input
	 */
	public void parseNumberProcessedInventoryInput(){
		numApples = parser.parseItemNum((EditText)findViewById(R.id.num_inv_postprocess_apple));
		numPears = parser.parseItemNum((EditText)findViewById(R.id.num_inv_postprocess_pear));
		numOranges = parser.parseItemNum((EditText)findViewById(R.id.num_inv_postprocess_orange));
		numBananas = parser.parseItemNum((EditText)findViewById(R.id.num_inv_postprocess_banana));
		numGrapes = parser.parseItemNum((EditText)findViewById(R.id.num_inv_postprocess_grapes));
		numKiwis = parser.parseItemNum((EditText)findViewById(R.id.num_inv_postprocess_kiwi));
		numMixedBags = parser.parseItemNum((EditText)findViewById(R.id.num_inv_postprocess_mixedBag));
		numFrozenFruitBags = parser.parseItemNum((EditText)findViewById(R.id.num_inv_postprocess_frozenFruitBag));
		numGranola = parser.parseItemNum((EditText) findViewById(R.id.num_inv_postprocess_granola));
	}
	
	/**
	 * Parse the price per inventory item from input
	 */
	public void parsePricePerProcessedInventoryItemInput(){
		applePrice = parser.parseItemPrice((EditText)findViewById(R.id.price_apple));
		pearPrice = parser.parseItemPrice((EditText)findViewById(R.id.price_pear));
		orangePrice = parser.parseItemPrice((EditText)findViewById(R.id.price_orange));
		bananaPrice = parser.parseItemPrice((EditText)findViewById(R.id.price_banana));
		grapesPrice = parser.parseItemPrice((EditText)findViewById(R.id.price_grapes));
		kiwiPrice = parser.parseItemPrice((EditText)findViewById(R.id.price_kiwi));
		mixedBagPrice = parser.parseItemPrice((EditText)findViewById(R.id.price_mixedBag));
		frozenFruitBagPrice = parser.parseItemPrice((EditText)findViewById(R.id.price_frozenFruitBag));
		granolaPrice = parser.parseItemPrice((EditText)findViewById(R.id.price_granola));
	}
	
	/**
	 * Save the number and price per processed inventory item to the database
	 */
	public void saveProcessedInventoryData(){
		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		FruitStand currentStand = dh.getCurrentFruitStand();
		
		currentStand.addProcessedInventoryItem(this, "apple", numApples, applePrice);
		currentStand.addProcessedInventoryItem(this, "pear", numPears, pearPrice);
		currentStand.addProcessedInventoryItem(this, "orange", numOranges, orangePrice);
		currentStand.addProcessedInventoryItem(this, "banana", numBananas, bananaPrice);
		currentStand.addProcessedInventoryItem(this, "grapes", numGrapes, grapesPrice);
		currentStand.addProcessedInventoryItem(this, "kiwi", numKiwis, kiwiPrice);
		currentStand.addProcessedInventoryItem(this, "mixedBag", numMixedBags, mixedBagPrice);
		currentStand.addProcessedInventoryItem(this, "frozenFruitBag", numFrozenFruitBags, frozenFruitBagPrice);
		currentStand.addProcessedInventoryItem(this, "granola", numGranola, granolaPrice);
	}
}