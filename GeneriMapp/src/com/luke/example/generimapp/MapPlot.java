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

public class MapPlot extends MapActivity {
	
	MapController mc;
	
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.maplayout);
	    MapView mapView = (MapView) findViewById(R.id.mapView);
	    mapView.setBuiltInZoomControls(true);
	    
	    mc = mapView.getController();
	    
	    List<Overlay> mapOverlays = mapView.getOverlays();
	    Drawable drawable = this.getResources().getDrawable(R.drawable.rock);
	    GeneriMappItemizedOverlay itemizedoverlay = new GeneriMappItemizedOverlay(drawable, mapView.getContext());
	    
	    XMLReader xr = new XMLReader();
	    xr.parseURL(getIntent().getStringExtra("PARSE_URL"));
	    //xr.parseURL("http://metpetdb.rpi.edu/metpetweb/searchIPhone.svc?north=42.449964&south=41.550036&east=-72.394514&west=-73.605486");
	    
	    GeoPoint point;
	    OverlayItem overlayitem;
	    for(int i = 0; i < xr.size(); i++){
	    	point = new GeoPoint((int)(xr.getY(i) * 1E6),(int)(xr.getX(i) * 1E6));
	    	overlayitem = new OverlayItem(point, Integer.toString(xr.getID(i)), Double.toString(xr.getX(i)) + ", " + Double.toString(xr.getY(i)));
	    	itemizedoverlay.addOverlay(overlayitem);
	    }
	    
	    //(int)(xr.getAvgX() * 1E6),(int)(xr.getAvgY() * 1E6)
	    GeoPoint avgpoint = new GeoPoint((int)(xr.getAvgY() * 1E6),(int)(xr.getAvgX() * 1E6));
	    mc.animateTo(avgpoint);
	    mc.zoomToSpan((int)(xr.getSpanY() * 1E6), (int)(xr.getSpanX() * 1E6));
	    mapOverlays.add(itemizedoverlay);
	}
}