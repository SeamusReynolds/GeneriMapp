package com.luke.example.generimapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RawLoc extends Activity{
	
	String url;
	EditText edittextN;
	EditText edittextS;
	EditText edittextE;
	EditText edittextW;
	
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
	    edittextN = (EditText) findViewById(R.id.entryN);
	    edittextS = (EditText) findViewById(R.id.entryS);
	    edittextE = (EditText) findViewById(R.id.entryE);
	    edittextW = (EditText) findViewById(R.id.entryW);
	    edittextN.setText("42.449964");
	    edittextS.setText("41.550036");
	    edittextE.setText("-72.104514");
	    edittextW.setText("-73.605486");
	}
	
	private void handleButtonClick()
    {
		url = "http://metpetdb.rpi.edu/metpetweb/searchIPhone.svc?north="
			+ edittextN.getText() + "&south="
			+ edittextS.getText() + "&east="
			+ edittextE.getText() + "&west="
			+ edittextW.getText();
		//url = "";
		Intent intent = new Intent(getBaseContext(), MapPlot.class);
		intent.putExtra("PARSE_URL", url);
		startActivity(intent);
    }
}
