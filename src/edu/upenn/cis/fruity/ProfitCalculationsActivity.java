package edu.upenn.cis.fruity;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Allows the user to provide input for calculated costs, net profit, and final cash box and to
 * check answers
 */
public class ProfitCalculationsActivity extends Activity{
	
	public static final int ProfitCalculationsActivity_ID = 14;
	private static Intent intent;
	private double totalRev;
	private double donations = 0.0; //TODO: change this to actual donations amount
	private double expectedTotalCosts, expectedNetProfit, expectedFinalCashBox;
	private FruitStand currentStand;

	private int numEquations = 3;
	
	ParseInputData parser = new ParseInputData();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculations_profit);
		
		intent = getIntent();

		// Set total revenue from prior screen
		if (intent != null && intent.getExtras() != null) {
			totalRev = Double.valueOf((String)intent.getExtras().get("totalRevenue"));
		} else {
			totalRev= 0.0;
		}
		
		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		currentStand = dh.getCurrentFruitStand();
		
		TextView fruitStandCost = (TextView)findViewById(R.id.calc_fruitStandCost);
		fruitStandCost.setText(parser.convertToCurrency(currentStand.stand_cost));
		
		TextView smoothieCost = (TextView)findViewById(R.id.calc_smoothieCost);
		smoothieCost.setText(parser.convertToCurrency(currentStand.smoothie_cost));
		
		TextView additionalCosts = (TextView)findViewById(R.id.calc_additionalCosts);
		additionalCosts.setText(parser.convertToCurrency(currentStand.additional_cost));
		
		final EditText totalCostsInput= (EditText)findViewById(R.id.calc_totalCost);
		totalCostsInput.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				TextView totalCostsInProfitEq = (TextView)findViewById(R.id.calc_totalCostProfitEq);
				totalCostsInProfitEq.setText(totalCostsInput.getText().toString());	
			}
			});

		TextView totalRevenue = (TextView)findViewById(R.id.calc_totalRevenueProfitEq);
		totalRevenue.setText(parser.convertToCurrency(totalRev));
		
		TextView initCashBox = (TextView)findViewById(R.id.calc_initCashBox);
		initCashBox.setText(parser.convertToCurrency(currentStand.initial_cash));
		
		// TODO: donations
		TextView donationsText = (TextView)findViewById(R.id.calc_donations);
		
		final EditText netProfitInput= (EditText)findViewById(R.id.calc_profit);
		netProfitInput.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				TextView netProfit = (TextView)findViewById(R.id.calc_profitFromEq);
				netProfit.setText(netProfitInput.getText().toString());	
			}
			});
		
		TextView numCorrectDisplay = (TextView)findViewById(R.id.num_correct_profit_calculations);
		numCorrectDisplay.setText("0/"+numEquations);
	}
	
	public void onCheckProfitCalculationsButtonClick(View v){
		expectedTotalCosts = currentStand.stand_cost + currentStand.smoothie_cost + currentStand.additional_cost;
		expectedNetProfit = totalRev - expectedTotalCosts;
		expectedFinalCashBox = currentStand.initial_cash + expectedNetProfit + donations;
		
		int numCorrect = 0;
		
		if(expectedTotalCosts == parser.parseItemPrice((EditText)findViewById(R.id.calc_totalCost))){
			numCorrect++;
		}
		double actualNetProfit = parser.parseItemPrice((EditText)findViewById(R.id.calc_profit));
		if(expectedNetProfit == actualNetProfit){
			numCorrect++;
		}
		double actualFinalCashBox = parser.parseItemPrice((EditText)findViewById(R.id.calc_finalCashBox));
		if(expectedFinalCashBox == actualFinalCashBox){
			numCorrect++;
		}
		
		System.out.println("expected net profit: " + expectedNetProfit);
		System.out.println("expected FinalCashBox: " + expectedFinalCashBox);
		System.out.println("actual net profit: " + actualNetProfit);
		System.out.println("actual final cash box: " + actualFinalCashBox);
		
		TextView numCorrectDisplay = (TextView)findViewById(R.id.num_correct_profit_calculations);
		numCorrectDisplay.setText("" + numCorrect +"/"+numEquations);
	}
}