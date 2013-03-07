package edu.upenn.cis.fruity;

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
}
