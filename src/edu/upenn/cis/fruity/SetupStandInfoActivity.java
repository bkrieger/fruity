package edu.upenn.cis.fruity;

import java.util.Calendar;

import edu.upenn.cis.fruity.database.DatabaseHandler;
import edu.upenn.cis.fruity.database.FruitStand;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class SetupStandInfoActivity extends Activity {

	public static final int InventoryPreprocessActivity_ID = 8;
	public static Intent intent;
	private String schoolName;
	private int month, day, year;
	private int temperature;
	private String weather;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_stand_info);
		intent = getIntent();

		// Set school title from prior screen
		if (intent != null && intent.getExtras() != null) {
			schoolName = (String) intent.getExtras().get("schoolName");
		} else {
			schoolName = "Filler Text";
		}

		TextView schoolNameView = (TextView) findViewById(R.id.standInfo_schoolName);
		schoolNameView.setText(schoolName);

		// Set date
		TextView dateView = (TextView) findViewById(R.id.standInfo_dateField);
		Calendar calendar = Calendar.getInstance();
		//TODO: day of week not stored in database
		String dayOfWeek;
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			dayOfWeek = "Sun";
			break;
		case 2:
			dayOfWeek = "Mon";
			break;
		case 3:
			dayOfWeek = "Tues";
			break;
		case 4:
			dayOfWeek = "Wed";
			break;
		case 5:
			dayOfWeek = "Thurs";
			break;
		case 6:
			dayOfWeek = "Fri";
			break;
		case 7:
			dayOfWeek = "Sat";
			break;
		default:
			dayOfWeek = "Fruitday";
			break;
		}
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DAY_OF_MONTH);
		year = calendar.get(Calendar.YEAR);
		dateView.setText(dayOfWeek + ", " + month + "/" + day + "/" + year);

		// Populate weather choice spinner
		Spinner weatherInput = (Spinner) findViewById(R.id.standInfo_weatherInput);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.weather_options,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		weatherInput.setAdapter(adapter);
		
		//get the weather condition input from the spinner
		weatherInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos,
					long id) {
				weather = (String)parent.getItemAtPosition(pos);
				/**
				TextView weatherView = (TextView) findViewById(R.id.standInfo_weatherText);
				weatherView.setText(weather);
				**/
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// if nothing is selected, what should be the default?
				weather = "Nothing selected.";
			}
		});
		

		// Register listener to update temperature
		SeekBar temperatureInput = (SeekBar) findViewById(R.id.standInfo_temperatureInput);
		temperatureInput
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onProgressChanged(SeekBar sb, int arg1,
							boolean arg2) {
						TextView temp = (TextView) findViewById(R.id.standInfo_temperatureText);
						temperature = sb.getProgress();
						temp.setText(temperature + "°F");

					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
						// Obligatory implementation by class inheritance
					}

					@Override
					public void onStopTrackingTouch(SeekBar arg0) {
						// Obligatory implementation by class inheritance
					}
				});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onInventoryPreprocessButtonClick(View v) {
		EditText cashBoxInput = (EditText) findViewById(R.id.standInfo_cashBoxInput);
		int cashBox = Integer.parseInt(cashBoxInput.getText().toString());
		
		EditText fruitStandCostInput = (EditText) findViewById(R.id.standInfo_fruitStandCostInput);
		double fruitStandCost = Integer.parseInt(fruitStandCostInput.getText().toString());
		
		EditText smoothieCostInput = (EditText) findViewById(R.id.standInfo_smoothieCostInput);
		double smoothieCost = Integer.parseInt(smoothieCostInput.getText().toString());
		
		EditText additionalCostsInput = (EditText) findViewById(R.id.standInfo_additionalCostsInput);
		double additionalCosts= Integer.parseInt(additionalCostsInput.getText().toString());
		
		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		// Send the Fruit Stand info to the database
		FruitStand stand = new FruitStand(schoolName, (month + "/" + day + "/" + year), temperature, 
				weather, cashBox, fruitStandCost, smoothieCost, additionalCosts);
		//Then in order to save the fruit stand to the database, use the handler as shown below.
		dh.putFruitStand(stand);
		
		Intent i = new Intent(this, InventoryPreprocessActivity.class);
		startActivityForResult(i, InventoryPreprocessActivity_ID);
	}
}
