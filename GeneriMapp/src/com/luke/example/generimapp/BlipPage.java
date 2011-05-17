package com.luke.example.generimapp;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BlipPage extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.blippagelayout);
	    
	    Bundle info = getIntent().getExtras();
	    String id = info.getString("ID");
	    String snip = info.getString("SNIPPET");
	    
	    TextView IDLab = (TextView) findViewById(R.id.labelID);
	    TextView SnipLab = (TextView) findViewById(R.id.labelSnippet);
	    
	    IDLab.setText("ID: " + id);
	    SnipLab.setText("Location: " + snip);
	    
	}
}
