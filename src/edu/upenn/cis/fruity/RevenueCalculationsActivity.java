package edu.upenn.cis.fruity;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Allows the user to provide input for calculated revenue per item and total revenue
 */
public class RevenueCalculationsActivity extends Activity {
	public static final int RevenueCalculationsActivity_ID = 13;
	
	//TODO: include other
	private int numApples, numPears, numOranges, numBananas, numGrapes, numKiwis = 0;
	private int numMixedBags, numSmoothies, numGranola = 0;
	private double applePrice, pearPrice, orangePrice, bananaPrice, grapesPrice, kiwiPrice;
	private double mixedBagPrice, smoothiePrice, granolaPrice;
	
	int numItems = 9;
	int numInputItems = numItems + 1; // 1 more for total revenue
	
	// apple = 0, pear = 1, orange = 2, banana = 3, grapes = 4, kiwi = 5, mixedBag = 6, smoothie = 7, granola = 8
	private double revenueInput [] = new double [numItems];
	private double expectedRevenue [] = new double [numItems];
	private boolean [] correct = new boolean [numItems];
	private double totalExpectedRevenue = 0.0;
	
	private ParseInputData parser = new ParseInputData();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculations_revenue);
		
		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		FruitStand currentStand = dh.getCurrentFruitStand();
		long id = currentStand.id;
		
		SQLiteDatabase db = dh.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT item_name, sum(count) FROM Purchase " +
				"WHERE fruit_stand_id ="+id + " GROUP BY item_name", null);
		
		getNumItemsSold(c);
		setNumItemsSold();
		
		Cursor c2 = db.rawQuery("SELECT item_name, price FROM ProcessedInventoryItem " +
				"WHERE fruit_stand_id ="+id + " GROUP BY item_name", null);
		getItemPrices(c2);
		/* TODO: maybe can make a join between Purchase and ProcessedInventoryItem database
		 to get prices and the number and parse at same time - need to make sure the fruit_stand-id is the same
		 once transaction data s saved
		*/
		setItemPrices();
		
		calculateExpectedRevenue();
		
		TextView numCorrectDisplay = (TextView)findViewById(R.id.num_correct_revenue_calculations);
		numCorrectDisplay.setText("0/"+numInputItems);
	}
	
	public void getNumItemsSold(Cursor c){		
		if(c.moveToFirst()){
			for (int i = 0; i < c.getCount(); i++) {			  
				String itemName = c.getString(0);
	 			int num = Integer.parseInt(c.getString(1));

	 			if(itemName.equals("apple")){
	 				numApples = num;
	 			}
	 			else if(itemName.equals("pear")){
	 				numPears = num;
	 			}
	 			else if(itemName.equals("orange")){
	 				numOranges = num; 
	 			}
	 			else if(itemName.equals("banana")){
	 				numBananas = num;
	 			}
	 			else if(itemName.equals("grapes")){
	 				numGrapes = num;
	 			}
	 			else if(itemName.equals("kiwi")){
	 				numKiwis = num;
	 			}
	 			else if(itemName.equals("mixedBag")){
	 				numMixedBags = num;
	 			}
	 			else if(itemName.equals("smoothie")){
	 				numSmoothies = num;
	 			}
	 			else if(itemName.equals("granola")){
	 				numGranola = num;
	 			}
	 			else{ //TODO: other
	 				
	 			}
	 			
				c.moveToNext();
			}
		}
		c.close();
	}
	
	public void setNumItemsSold(){
		TextView numApplesText = (TextView)findViewById(R.id.num_bought_apple);
		TextView numPearsText = (TextView)findViewById(R.id.num_bought_pear);
		TextView numOrangesText = (TextView)findViewById(R.id.num_bought_orange);
		TextView numBananasText = (TextView)findViewById(R.id.num_bought_banana);
		TextView numGrapesText = (TextView)findViewById(R.id.num_bought_grapes);
		TextView numKiwisText = (TextView)findViewById(R.id.num_bought_kiwi);
		TextView numMixedBagsText = (TextView)findViewById(R.id.num_bought_mixedBag);
		TextView numSmoothiesText = (TextView)findViewById(R.id.num_bought_smoothie);
		TextView numGranolaText = (TextView)findViewById(R.id.num_bought_granola);
		
		numApplesText.setText("" + numApples);
		numPearsText.setText("" + numPears);
		numOrangesText.setText("" + numOranges);
		numBananasText.setText("" + numBananas);
		numGrapesText.setText("" + numGrapes);
		numKiwisText.setText("" + numKiwis);
		numMixedBagsText.setText("" + numMixedBags);
		numSmoothiesText.setText("" + numSmoothies);
		numGranolaText.setText("" + numGranola);
	}
	
	// TODO: later can combine this method with getNumberItemsSold method once transaction data is saved
	public void getItemPrices(Cursor c){
		if(c.moveToFirst()){
			for (int i = 0; i < c.getCount(); i++) {			  
				String itemName = c.getString(0);
	 			double price = Double.parseDouble(c.getString(1));

	 			if(itemName.equals("apple")){
	 				applePrice = price;
	 			}
	 			else if(itemName.equals("pear")){
	 				pearPrice = price;
	 			}
	 			else if(itemName.equals("orange")){
	 				orangePrice = price; 
	 			}
	 			else if(itemName.equals("banana")){
	 				bananaPrice = price;
	 			}
	 			else if(itemName.equals("grapes")){
	 				grapesPrice = price;
	 			}
	 			else if(itemName.equals("kiwi")){
	 				kiwiPrice = price;
	 			}
	 			else if(itemName.equals("mixedBag")){
	 				mixedBagPrice = price;
	 			}
	 			else if(itemName.equals("smoothie")){
	 				smoothiePrice = price;
	 			}
	 			else if(itemName.equals("granola")){
	 				granolaPrice = price;
	 			}
	 			else{ //TODO: other
	 				
	 			}
	 			
				c.moveToNext();
			}
		}
	}
	
	public void setItemPrices(){
		TextView applePriceText = (TextView)findViewById(R.id.price_calc_apple);
		TextView pearPriceText = (TextView)findViewById(R.id.price_calc_pear);
		TextView orangePriceText = (TextView)findViewById(R.id.price_calc_orange);
		TextView bananaPriceText = (TextView)findViewById(R.id.price_calc_banana);
		TextView grapesPriceText = (TextView)findViewById(R.id.price_calc_grapes);
		TextView kiwiPriceText = (TextView)findViewById(R.id.price_calc_kiwi);
		TextView mixedBagPriceText = (TextView)findViewById(R.id.price_calc_mixedBag);
		TextView smoothiePriceText = (TextView)findViewById(R.id.price_calc_smoothie);
		TextView granolaPriceText = (TextView)findViewById(R.id.price_calc_granola);
		
		applePriceText.setText("$" + applePrice);
		pearPriceText.setText("$" + pearPrice);
		orangePriceText.setText("$" + orangePrice);
		bananaPriceText.setText("$" + bananaPrice);
		grapesPriceText.setText("$" + grapesPrice);
		kiwiPriceText.setText("$" + kiwiPrice);
		mixedBagPriceText.setText("$" + mixedBagPrice);
		smoothiePriceText.setText("$" + smoothiePrice);
		granolaPriceText.setText("$" + granolaPrice);
	}
	
	public void calculateExpectedRevenue(){
		expectedRevenue[0] = numApples*applePrice;
		expectedRevenue[1] = numPears*pearPrice;
		expectedRevenue[2] = numOranges*orangePrice;
		expectedRevenue[3] = numBananas*bananaPrice;
		expectedRevenue[4] = numGrapes*grapesPrice;
		expectedRevenue[5] = numKiwis*kiwiPrice;
		expectedRevenue[6] = numMixedBags*mixedBagPrice;
		expectedRevenue[7] = numSmoothies*smoothiePrice;
		expectedRevenue[8] = numGranola*granolaPrice;
		
		for(int i = 0; i < numItems; i++){
			totalExpectedRevenue +=expectedRevenue[i]; 
		}
	}
	
	public void onCheckRevenueCalculationsButtonClick(View v){
		// actual revenue input
		revenueInput[0] = parser.parseItemPrice((EditText)findViewById(R.id.revenue_apple));
		revenueInput[1] = parser.parseItemPrice((EditText)findViewById(R.id.revenue_pear));
		revenueInput[2] = parser.parseItemPrice((EditText)findViewById(R.id.revenue_orange));
		revenueInput[3] = parser.parseItemPrice((EditText)findViewById(R.id.revenue_banana));
		revenueInput[4] = parser.parseItemPrice((EditText)findViewById(R.id.revenue_grapes));
		revenueInput[5] = parser.parseItemPrice((EditText)findViewById(R.id.revenue_kiwi));
		revenueInput[6] = parser.parseItemPrice((EditText)findViewById(R.id.revenue_mixedBag));
		revenueInput[7] = parser.parseItemPrice((EditText)findViewById(R.id.revenue_smoothie));
		revenueInput[8] = parser.parseItemPrice((EditText)findViewById(R.id.revenue_granola));
		
		int numCorrect = 0;
		// compare actual to expected revenue input
		for(int i = 0; i < numItems; i++){
			if(revenueInput[i] == expectedRevenue[i]){
				correct[i] = true;
				numCorrect++;
			}
			else{
				correct[i] = false;
			}
		}
		// compare actual to expected total revenue
		double totalRevenueInput = parser.parseItemPrice((EditText)findViewById(R.id.totalRevenue));
		if(totalRevenueInput == totalExpectedRevenue){
			numCorrect++;
		}

		TextView numCorrectDisplay = (TextView)findViewById(R.id.num_correct_revenue_calculations);
		numCorrectDisplay.setText("" + numCorrect +"/"+numInputItems);
	}

	public void onGoToProfitCalculationsButtonClick(View v){
		Intent i = new Intent(this, ProfitCalculationsActivity.class);
		startActivityForResult(i, RevenueCalculationsActivity_ID);
	}
}