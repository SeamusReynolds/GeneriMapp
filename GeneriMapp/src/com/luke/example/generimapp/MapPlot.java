package com.luke.example.generimapp;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

public class MapPlot extends MapActivity {
	
	MapController mc;
	
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}
	
	private String convertLocation(double _lat, double _long, int _rad){
		String url = "http://metpetdb.rpi.edu/metpetweb/searchIPhone.svc?north="
			+ Double.toString((_lat + ((double)_rad * 0.0145)))  + "&south="
			+ Double.toString((_lat - ((double)_rad * 0.0145))) + "&east="
			+ Double.toString((_long + (((double)_rad * 0.0145) * Math.cos(_lat)))) + "&west="
			+ Double.toString((_long - (((double)_rad * 0.0145) * Math.cos(_lat))));
		return url;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.maplayout);
	    MapView mapView = (MapView) findViewById(R.id.mapView);
	    mapView.setBuiltInZoomControls(true);
	    
	    mc = mapView.getController();
	    
	    
	    List<Overlay> mapOverlays = mapView.getOverlays();
	    Drawable points = this.getResources().getDrawable(R.drawable.blip);
	    GeneriMappItemizedOverlay itemizedoverlay = new GeneriMappItemizedOverlay(points, mapView.getContext());
	    
	    Drawable mylocblip = this.getResources().getDrawable(R.drawable.pin);
	    GeneriMappItemizedOverlay mylocoverlay = new GeneriMappItemizedOverlay(mylocblip, mapView.getContext());
	    
	    Bundle locdata = getIntent().getExtras();
	    double _lat = locdata.getDouble("LAT_VAL");
	    double _long = locdata.getDouble("LONG_VAL");
	    int _rad = locdata.getInt("RAD_VAL");
	    
	    GeoPoint myloc = new GeoPoint((int)(_lat * 1E6), (int)(_long * 1E6));
	    OverlayItem mylocitem = new OverlayItem(myloc, "MyLocation", "You are here.");
    	mylocoverlay.addOverlay(mylocitem);
	    
	    XMLReader xr = new XMLReader();
	    xr.parseURL(convertLocation(_lat,_long,_rad));
	    
	    //xr.parseURL("http://metpetdb.rpi.edu/metpetweb/searchIPhone.svc?north=42.449964&south=41.550036&east=-72.394514&west=-73.605486");
	    if(xr.size() == 0) Toast.makeText(mapView.getContext(), "No points found." , Toast.LENGTH_LONG).show();
	    
    	GeoPoint point;
	    OverlayItem overlayitem;
	    for(int i = 0; i < xr.size(); i++){
	    	point = new GeoPoint((int)(xr.getY(i) * 1E6),(int)(xr.getX(i) * 1E6));
	    	overlayitem = new OverlayItem(point, Integer.toString(xr.getID(i)), Double.toString(xr.getX(i)) + ", " + Double.toString(xr.getY(i)));
	    	itemizedoverlay.addOverlay(overlayitem);
	    }
	    
	    //(int)(xr.getAvgX() * 1E6),(int)(xr.getAvgY() * 1E6)
	    //GeoPoint avgpoint = new GeoPoint((int)(xr.getAvgY() * 1E6),(int)(xr.getAvgX() * 1E6));
	    mc.animateTo(myloc);
	    mc.zoomToSpan((int)(xr.getSpanY() * 1E6), (int)(xr.getSpanX() * 1E6));
	    mapOverlays.add(mylocoverlay);
	    mapOverlays.add(itemizedoverlay);
	}
}