package edu.upenn.cis.fruity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

/**
 * User can input quantities for whole fruit, mixed bags, and other food inventory
 * after fruit is processed into mixed bags
 */
public class InventoryPostprocessActivity extends Activity {
	
	public static final int InventoryPostprocessActivity_ID = 10;
	
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
	
	public void onInventorySalesDemographicsButtonClick(View view){
		Intent i = new Intent(this,SalesDemographicActivity.class);
		startActivityForResult(i, InventoryPostprocessActivity_ID);
	}
}