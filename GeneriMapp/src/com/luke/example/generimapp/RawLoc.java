package com.luke.example.generimapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RawLoc extends Activity{
	
	EditText edittextLat;
	EditText edittextLong;
	EditText edittextRad;
	
	private class ButtonHandler implements View.OnClickListener
    {
		public void onClick(View v)
		{
			handleButtonClick();
		}	
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.rawloclayout);
	    final Button submit = (Button) findViewById(R.id.button);
	    submit.setOnClickListener(new ButtonHandler());
	    edittextLat = (EditText) findViewById(R.id.entryLat);
	    edittextLong = (EditText) findViewById(R.id.entryLong);
	    edittextRad = (EditText) findViewById(R.id.entryRad);
	    edittextLat.setText("42.009964");
	    edittextLong.setText("-72.903231");
	    edittextRad.setText("100");
	}
	
	private void handleButtonClick()
    {
		/*url = "http://metpetdb.rpi.edu/metpetweb/searchIPhone.svc?north="
			+ edittextN.getText() + "&south="
			+ edittextS.getText() + "&east="
			+ edittextE.getText() + "&west="
			+ edittextW.getText();
		//url = "";
		 * 
		 */
		Intent intent = new Intent(getBaseContext(), MapPlot.class);
		intent.putExtra("LAT_VAL", Double.parseDouble(edittextLat.getText().toString()));
		intent.putExtra("LONG_VAL", Double.parseDouble(edittextLong.getText().toString()));
		intent.putExtra("RAD_VAL", Integer.parseInt(edittextRad.getText().toString()));
		startActivity(intent);
    }
}
