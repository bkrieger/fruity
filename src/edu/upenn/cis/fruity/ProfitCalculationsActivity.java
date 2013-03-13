package edu.upenn.cis.fruity;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class ProfitCalculationsActivity extends Activity{
	
	public static final int ProfitCalculationsActivity_ID = 14;
	
	ParseInputData parser = new ParseInputData();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculations_profit);
		
		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		FruitStand currentStand = dh.getCurrentFruitStand();
		
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
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				TextView totalCostsInProfitEq = (TextView)findViewById(R.id.calc_totalCostProfitEq);
				totalCostsInProfitEq.setText(totalCostsInput.getText().toString());	
			}
			});
		
		//TODO: set revenue textview
	}
}