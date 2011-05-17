package com.luke.example.generimapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GeneriMapp extends Activity{
	
	double latitude;
	double longitude;
	LocationManager locationManager;
	LocationListener locationListener;
	AlertDialog.Builder builder;
	AlertDialog radpick;
	
	private class ButtonHandler implements View.OnClickListener
    {
		public void onClick(View v)
		{
			switch(v.getId()){
			case R.id.mylocb:
				handleMyLoc();
				break;
			case R.id.maplocb:
				handleMapLoc();
				break;
			case R.id.rawlocb:
				handleRawLoc();
				break;
			}
		}	
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.loctypelayout);
	    
	    //attempt to get a location fix early
	    locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

	    // Define a listener that responds to location updates
	    locationListener = new LocationListener() {
	        public void onLocationChanged(Location location) {
	          updateLocation(location);
	        }

			@Override
			public void onProviderDisabled(String provider) {}

			@Override
			public void onProviderEnabled(String provider) {}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
	      };

	    // Register the listener with the Location Manager to receive location updates
	    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
	    
	    final CharSequence[] radii = {"20", "50", "100", "150", "200", "300", "400", "500", "1000"};
	    
	    //set up the radius picker
	    builder = new AlertDialog.Builder(this);
	    builder.setTitle("Pick a radius");
	    builder.setCancelable(false);
	    builder.setItems(radii, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
	            int radius = Integer.parseInt(radii[item].toString());
	            plotmyloc(radius);
	        }
	    });
	    
	    radpick = builder.create();
	    
	    final Button mylb = (Button) findViewById(R.id.mylocb);
	    final Button maplb = (Button) findViewById(R.id.maplocb);
	    final Button rawlb = (Button) findViewById(R.id.rawlocb);
	    
	    ButtonHandler mybh = new ButtonHandler();
	    mylb.setOnClickListener(mybh);
	    maplb.setOnClickListener(mybh);
	    rawlb.setOnClickListener(mybh);
	}
	
	private void updateLocation(Location l){
		latitude = l.getLatitude();
		longitude = l.getLongitude();
	}
	
	private void handleMyLoc(){
		locationManager.removeUpdates(locationListener);
		radpick.show();
	}
	
	private void plotmyloc(int radius){
		/*
		double north = (latitude + ((double)radius * 0.0145)),
		south = (latitude - ((double)radius * 0.0145)),
		east = (longitude + ((double)radius * 0.0145)),
		west = (longitude - ((double)radius * 0.0145));
		String url = "http://metpetdb.rpi.edu/metpetweb/searchIPhone.svc?north="
			+ Double.toString(north) + "&south="
			+ Double.toString(south) + "&east="
			+ Double.toString(east) + "&west="
			+ Double.toString(west);
			*/
		Intent intent = new Intent(this, MapPlot.class);
		intent.putExtra("LAT_VAL", latitude);
		intent.putExtra("LONG_VAL", longitude);
		intent.putExtra("RAD_VAL", radius);
		startActivity(intent); 
	}
	
	private void handleMapLoc(){
		
	}
	
	private void handleRawLoc(){
		startActivity(new Intent(this, RawLoc.class));
	}
	
	/*
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
    */
}
