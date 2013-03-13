package edu.upenn.cis.fruity;

import java.text.DecimalFormat;

import android.widget.EditText;

/**
 * Parses input from the user
 */
public class ParseInputData {
	/**
	 * Parse the number of an inventory item from input
	 * @param numInput
	 * @return num
	 */
	public int parseItemNum(EditText numInput){
		String numStr = numInput.getText().toString();
		int num;
		if(numStr.equals("")){
			num = 0;
		}
		else{
			num = Integer.parseInt(numStr);
		}
		return num;
	}
	
	/**
	 * Convert item price to a valid price value
	 * @param priceInput
	 * @return price
	 */
	public double parseItemPrice(EditText priceInput){
		String priceStr = (priceInput.getText().toString()).replaceAll("\\$","");
		double price;
		if(priceStr.equals("")){
			price = 0;
		}
		else{
			price = Double.parseDouble(priceStr);
		}		
		
		return price;
	}
	
	/**
	 * Convert a double value into currency format
	 * @param value: input currency as a double
	 * @return value as a formated currency string
	 */
	public String convertToCurrency(double value){
		DecimalFormat df = new DecimalFormat("$0.00");
		return df.format(value);
	}
}
