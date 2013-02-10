package edu.upenn.cis.fruity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

/**
 * User can input quantities for whole fruit inventory before fruit is processed and sold
 */
public class InventoryPreprocessActivity extends Activity {
	
	public static final int InventoryPreprocessActivity_ID = 9;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_preprocess);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	} 
	
	public void onInventoryPostprocessButtonClick(View view){
		Intent i = new Intent(this,InventoryPostprocessActivity.class);
		startActivityForResult(i, InventoryPreprocessActivity_ID);
	}
}