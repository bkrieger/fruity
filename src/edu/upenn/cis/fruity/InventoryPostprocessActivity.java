package edu.upenn.cis.fruity;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * User can input quantities for whole fruit, mixed bags, and other food inventory
 * after fruit is processed into mixed bags
 */
public class InventoryPostprocessActivity extends Activity {
	
	private int numApples, numPears, numOranges, numBananas, numGrapes, numKiwis;
	private int numGranola, numMixedBags, numSmoothies, numOther1, numOther2;
	private double applePrice, pearPrice, orangePrice, bananaPrice, grapesPrice, kiwiPrice;
	private double mixedBagPrice, smoothiePrice, granolaPrice, other1Price, other2Price;	
	
	private RadioGroup radioFruitPriceGroup;
	EditText applePriceText;
	
	public static final int InventoryPostprocessActivity_ID = 10;
	
	private ParseInputData parser = new ParseInputData();
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_postprocess);
		
		intent = getIntent();

		if (intent != null && intent.getExtras() != null) {
			numGranola = (Integer) intent.getExtras().get("numGranola");
		}
		
		TextView numGranolaText = (TextView)findViewById(R.id.num_inv_postprocess_granola);
		numGranolaText.setText(String.valueOf(numGranola));
		applePriceText = (EditText)findViewById(R.id.price_apple);
		addListenerOnRadioButton();
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

	
	public void addListenerOnRadioButton(){ 
		radioFruitPriceGroup = (RadioGroup)findViewById(R.id.radioFruitPrices);
		radioFruitPriceGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int selectedId = radioFruitPriceGroup.getCheckedRadioButtonId();
			    if(selectedId == R.id.radioSetDefaultFruitPrice){
			    	Toast toast = Toast.makeText(getApplicationContext(),
							"All fruit prices will be set to the price entered for apples", Toast.LENGTH_SHORT);
					toast.show();
					setDefaultFruitPrices();
					applePriceText.addTextChangedListener(priceWatcher);
			    }
			    else{
			    	applePriceText.removeTextChangedListener(priceWatcher);
			    }
			}
		});
	}
	
	// Monitor for changes to the apple price entered by the user
	TextWatcher priceWatcher = new TextWatcher(){
		@Override
		public void afterTextChanged(Editable arg0) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			setDefaultFruitPrices();
		}
	};
	
	// Set all fruit prices to the price entered for apples
	public void setDefaultFruitPrices(){
		EditText pearPriceText = (EditText)findViewById(R.id.price_pear);
		EditText orangePriceText = (EditText)findViewById(R.id.price_orange);
		EditText bananaPriceText = (EditText)findViewById(R.id.price_banana);
		EditText grapesPriceText = (EditText)findViewById(R.id.price_grapes);
		EditText kiwiPriceText = (EditText)findViewById(R.id.price_kiwi);
		
		pearPriceText.setText(parser.convertToCurrency(parser.parseItemPrice(applePriceText)));
		orangePriceText.setText(parser.convertToCurrency(parser.parseItemPrice(applePriceText)));
		bananaPriceText.setText(parser.convertToCurrency(parser.parseItemPrice(applePriceText)));
		grapesPriceText.setText(parser.convertToCurrency(parser.parseItemPrice(applePriceText)));
		kiwiPriceText.setText(parser.convertToCurrency(parser.parseItemPrice(applePriceText)));
	}
	
	/**
	 * When click on button, parse input and save to database
	 * @param view
	 */
	public void onInventorySalesDemographicsButtonClick(View view){
		parseNumberProcessedInventoryInput();
		parsePricePerProcessedInventoryItemInput();
		saveProcessedInventoryData();
		
		Intent i = new Intent(this,SalesReadyForSalesActivity.class);
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
		numSmoothies = parser.parseItemNum((EditText)findViewById(R.id.num_inv_postprocess_smoothie));
		numOther1 = parser.parseItemNum((EditText)findViewById(R.id.num_inv_postprocess_other1));
		numOther2 = parser.parseItemNum((EditText)findViewById(R.id.num_inv_postprocess_other2));
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
		smoothiePrice = parser.parseItemPrice((EditText)findViewById(R.id.price_smoothie));
		granolaPrice = parser.parseItemPrice((EditText)findViewById(R.id.price_granola));
		other1Price = parser.parseItemPrice((EditText)findViewById(R.id.price_other1));
		other2Price = parser.parseItemPrice((EditText)findViewById(R.id.price_other2));
	}
	
	/**
	 * Save the number and price per processed inventory item to the database
	 */
	public void saveProcessedInventoryData(){
		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		FruitStand currentStand = dh.getCurrentFruitStand();
		
		EditText other1input = (EditText) findViewById(R.id.name_inv_postprocess_other1);
		EditText other2input = (EditText) findViewById(R.id.name_inv_postprocess_other2);
		
		String name_other1 = other1input.getText().toString();
		String name_other2 = other2input.getText().toString();
		
		currentStand.addProcessedInventoryItem(this, "apple", numApples, applePrice);
		currentStand.addProcessedInventoryItem(this, "pear", numPears, pearPrice);
		currentStand.addProcessedInventoryItem(this, "orange", numOranges, orangePrice);
		currentStand.addProcessedInventoryItem(this, "banana", numBananas, bananaPrice);
		currentStand.addProcessedInventoryItem(this, "grapes", numGrapes, grapesPrice);
		currentStand.addProcessedInventoryItem(this, "kiwi", numKiwis, kiwiPrice);
		currentStand.addProcessedInventoryItem(this, "mixedBag", numMixedBags, mixedBagPrice);
		currentStand.addProcessedInventoryItem(this, "smoothie", numSmoothies, smoothiePrice);
		currentStand.addProcessedInventoryItem(this, "granola", numGranola, granolaPrice);
		currentStand.addProcessedInventoryItem(this, "other1:" + name_other1, numOther1, other1Price);
		currentStand.addProcessedInventoryItem(this, "other2:" + name_other2, numOther2, other2Price);
	}
}