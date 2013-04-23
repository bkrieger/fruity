package edu.upenn.cis.fruity;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;
import edu.upenn.cis.fruity.database.ProcessedInventoryItem;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Allows the user to provide input for calculated revenue per item and total revenue
 */
public class RevenueCalculationsActivity extends Activity {
	public static final int RevenueCalculationsActivity_ID = 13;
	
	//TODO: include other	
	private int numItems = 11;
	private int numInputItems = numItems + 1; // 1 more for total revenue
	
	// apple = 0, pear = 1, orange = 2, banana = 3, grapes = 4, kiwi = 5, mixedBag = 6, smoothie = 7, granola = 8, other1 = 9, other2 = 10
	private double revenueInput[] = new double[numItems];
	private double expectedRevenue[] = new double[numItems];
	private double totalExpectedRevenue = 0.0;
	private double precision = 0.001;
	
	private int[] itemIds = {R.id.revenue_apple, R.id.revenue_pear, R.id.revenue_orange,
			R.id.revenue_banana, R.id.revenue_grapes, R.id.revenue_kiwi, R.id.revenue_mixedBag,
			R.id.revenue_smoothie, R.id.revenue_granola, R.id.revenue_other1, R.id.revenue_other2};
	private int[] numItemsPurchased = new int[numItems];
	private double[] itemPrices = new double[numItems];
	
	private int correctColor = android.R.attr.editTextBackground;
	private int incorrectColor = Color.YELLOW;
	private int numAttempts = 0;
	private int attemptLimit = 3;
	
	private String other1name;
	private String other2name;
	
	private FruitStand currentStand;
	
	private Button profitCalcButton;
	
	private ParseInputData parser = new ParseInputData();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculations_revenue);
		
		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		currentStand = dh.getCurrentFruitStand();
		long id = currentStand.id;
		
		SQLiteDatabase db = dh.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT item_name, sum(count)- sum(num_coupons) - sum(num_tradeins) " +
				"as numBoughtWithCash FROM Purchase WHERE fruit_stand_id ="+id + " GROUP BY item_name", null);
		getNumItemsSold(c);
		setNumItemsSold();
		c.close();
		
		Cursor c2 = db.rawQuery("SELECT item_name, price FROM ProcessedInventoryItem " +
				"WHERE fruit_stand_id ="+id + " GROUP BY item_name", null);
		getItemPrices(c2);
		setItemPrices();
		c2.close();
		
		setOtherNames();
		calculateExpectedRevenue();
		
		TextView numCorrectDisplay = (TextView)findViewById(R.id.num_correct_revenue_calculations);
		numCorrectDisplay.setText("0/"+numInputItems);
		
		profitCalcButton = (Button)findViewById(R.id.gotoProfitCalculationsBtn);
		profitCalcButton.setEnabled(false);	
	}

	// Derive the names for the two "other" choices
	private void setOtherNames() {
		ProcessedInventoryItem[] availableItems = currentStand.getProcessedInventoryItems(this);
		
		for (ProcessedInventoryItem i : availableItems) {
			if (i.item_name.startsWith("other1:")) other1name = i.item_name.substring(7);
			if (i.item_name.startsWith("other2:")) other2name = i.item_name.substring(7);
		}
		
		if (other1name != null && !other1name.equals("")) {
			TextView textView = (TextView) findViewById(R.id.rev_other1_text);
			textView.setText(other1name);
		} 
		
		if (other2name != null && !other2name.equals("")) {
			TextView textView = (TextView) findViewById(R.id.rev_other2_text);
			textView.setText(other2name);
		} 
	}

	public void getNumItemsSold(Cursor c){		
		if(c.moveToFirst()){
			for (int i = 0; i < c.getCount(); i++) {			  
				String itemName = c.getString(0);
				Log.v("ITEM NAME: ", itemName);
	 			int num = Integer.parseInt(c.getString(1));

	 			if(itemName.equals("apple")){
	 				numItemsPurchased[0] = num;
	 				LinearLayout layout = (LinearLayout) findViewById(R.id.rev_apple_row);
	 				layout.setVisibility(View.VISIBLE);
	 				
	 			}
	 			else if(itemName.equals("pear")){
	 				numItemsPurchased[1] = num;
	 				LinearLayout layout = (LinearLayout) findViewById(R.id.rev_pear_row);
	 				layout.setVisibility(View.VISIBLE);
	 			}
	 			else if(itemName.equals("orange")){
	 				numItemsPurchased[2] = num;
	 				LinearLayout layout = (LinearLayout) findViewById(R.id.rev_orange_row);
	 				layout.setVisibility(View.VISIBLE);
	 				
	 			}
	 			else if(itemName.equals("banana")){
	 				numItemsPurchased[3] = num;
	 				LinearLayout layout = (LinearLayout) findViewById(R.id.rev_banana_row);
	 				layout.setVisibility(View.VISIBLE);
	 				
	 			}
	 			else if(itemName.equals("grapes")){
	 				numItemsPurchased[4] = num;
	 				LinearLayout layout = (LinearLayout) findViewById(R.id.rev_grapes_row);
	 				layout.setVisibility(View.VISIBLE);
	 				
	 			}
	 			else if(itemName.equals("kiwi")){
	 				numItemsPurchased[5] = num;
	 				LinearLayout layout = (LinearLayout) findViewById(R.id.rev_kiwi_row);
	 				layout.setVisibility(View.VISIBLE);
	 				
	 			}
	 			else if(itemName.equals("mixedBag")){
	 				numItemsPurchased[6] = num;
	 				LinearLayout layout = (LinearLayout) findViewById(R.id.rev_mixedBag_row);
	 				layout.setVisibility(View.VISIBLE);
	 				
	 			}
	 			else if(itemName.equals("smoothie")){
	 				numItemsPurchased[7] = num;
	 				LinearLayout layout = (LinearLayout) findViewById(R.id.rev_smoothie_row);
	 				layout.setVisibility(View.VISIBLE);
	 				
	 			}
	 			else if(itemName.equals("granola")){
	 				numItemsPurchased[8] = num;
	 				LinearLayout layout = (LinearLayout) findViewById(R.id.rev_granola_row);
	 				layout.setVisibility(View.VISIBLE);
	 				
	 			}
	 			else if(itemName.startsWith("other1:")){
	 				numItemsPurchased[9] = num;
	 				LinearLayout layout = (LinearLayout) findViewById(R.id.rev_other1_row);
	 				layout.setVisibility(View.VISIBLE);
	 				
	 			} 
	 			else if(itemName.startsWith("other2:")){
	 				numItemsPurchased[10] = num;
	 				LinearLayout layout = (LinearLayout) findViewById(R.id.rev_other1_row);
	 				layout.setVisibility(View.VISIBLE);
	 				
	 			}
	 			
				c.moveToNext();
			}
		}
		c.close();
	}
	
	public void setNumItemsSold(){
		int[] numItemIds = {R.id.num_bought_apple, R.id.num_bought_pear, R.id.num_bought_orange,
				R.id.num_bought_banana, R.id.num_bought_grapes, R.id.num_bought_kiwi, R.id.num_bought_mixedBag,
				R.id.num_bought_smoothie, R.id.num_bought_granola, R.id.num_bought_other1, R.id.num_bought_other2};
		
		TextView numItemsText;
		for(int i = 0; i < numItems; i++){
			numItemsText = (TextView)findViewById(numItemIds[i]);
			numItemsText.setText("" + numItemsPurchased[i]);
		}
	}

	public void getItemPrices(Cursor c){
		if(c.moveToFirst()){
			for (int i = 0; i < c.getCount(); i++) {			  
				String itemName = c.getString(0);
	 			double price = Double.parseDouble(c.getString(1));

	 			if(itemName.equals("apple")){
	 				itemPrices[0] = price;
	 			}
	 			else if(itemName.equals("pear")){
	 				itemPrices[1] = price;
	 			}
	 			else if(itemName.equals("orange")){
	 				itemPrices[2] = price;
	 			}
	 			else if(itemName.equals("banana")){
	 				itemPrices[3] = price;
	 			}
	 			else if(itemName.equals("grapes")){
	 				itemPrices[4] = price;
	 			}
	 			else if(itemName.equals("kiwi")){
	 				itemPrices[5] = price;
	 			}
	 			else if(itemName.equals("mixedBag")){
	 				itemPrices[6] = price;
	 			}
	 			else if(itemName.equals("smoothie")){
	 				itemPrices[7] = price;
	 			}
	 			else if(itemName.equals("granola")){
	 				itemPrices[8] = price;
	 			}
	 			else if(itemName.startsWith("other1:")){
	 				itemPrices[9] = price;
	 			} 
	 			else if(itemName.startsWith("other2:")){
	 				itemPrices[10] = price;
	 			}
	 			
				c.moveToNext();
			}
		}
	}
	
	public void setItemPrices(){
		int[] priceIds = {R.id.price_calc_apple, R.id.price_calc_pear, R.id.price_calc_orange,
				R.id.price_calc_banana, R.id.price_calc_grapes, R.id.price_calc_kiwi, R.id.price_calc_mixedBag,
				R.id.price_calc_smoothie, R.id.price_calc_granola, R.id.price_calc_other1, R.id.price_calc_other2};
		
		TextView priceText;
		for(int i = 0; i < numItems; i++){
			priceText = (TextView)findViewById(priceIds[i]);
			priceText.setText(parser.convertToCurrency(itemPrices[i]));
		}
	}
	
	public void calculateExpectedRevenue(){
		for(int i = 0; i < numItems; i++){
			expectedRevenue[i] = numItemsPurchased[i] * itemPrices[i];
			totalExpectedRevenue +=expectedRevenue[i]; 
		}
	}
	
	public void onCheckRevenueCalculationsButtonClick(View v){
		int numCorrect = 0;
		EditText itemRevenueText;
		// compare actual to expected revenue input
		for(int i = 0; i < numItems; i++){
			itemRevenueText = (EditText)findViewById(itemIds[i]);
			revenueInput[i] = parser.parseItemPrice(itemRevenueText); // actual revenue input

			if(Math.abs(revenueInput[i] - expectedRevenue[i]) < precision){
				numCorrect++;
				itemRevenueText.setBackgroundColor(correctColor);
			}
			else{		
				itemRevenueText.setBackgroundColor(incorrectColor);
			}
		}
		// compare actual to expected total revenue
		EditText totalRevenueText = (EditText)findViewById(R.id.totalRevenue);
		double totalRevenueInput = parser.parseItemPrice(totalRevenueText);
		if(Math.abs(totalRevenueInput - totalExpectedRevenue) < precision){
			numCorrect++;
			totalRevenueText.setBackgroundColor(correctColor);
		}
		else{
			totalRevenueText.setBackgroundColor(incorrectColor);
		}
		
		TextView numCorrectDisplay = (TextView)findViewById(R.id.num_correct_revenue_calculations);
		numCorrectDisplay.setText("" + numCorrect +"/"+numInputItems);
		
		numAttempts++;
		
		if(numCorrect < numInputItems){
			Toast toast = Toast.makeText(getApplicationContext(),
					"Incorrect revenue calculations are highlighted in yellow.", Toast.LENGTH_SHORT);
			toast.show();
			
			if(numAttempts >= attemptLimit){
				toast = Toast.makeText(getApplicationContext(),
						"Please seek assistance to obtain the correct revenue calculations.", Toast.LENGTH_SHORT);
				toast.show();
				profitCalcButton.setEnabled(true);	
			}
		}
		else{
			Toast toast = Toast.makeText(getApplicationContext(),
					"Congratulations! You may now move on to the profit calculations activity.", Toast.LENGTH_SHORT);
			toast.show();
			profitCalcButton.setEnabled(true);
		}
	}

	public void onGoToProfitCalculationsButtonClick(View v){
		Intent i = new Intent(this, ProfitCalculationsActivity.class);
		i.putExtra("totalRevenue", ((Double)totalExpectedRevenue).toString());
		startActivityForResult(i, RevenueCalculationsActivity_ID);
	}
}