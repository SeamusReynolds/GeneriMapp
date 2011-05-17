package com.luke.example.generimapp;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

@SuppressWarnings("rawtypes")
public class GeneriMappItemizedOverlay extends ItemizedOverlay {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	
	public GeneriMappItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		
	}
	
	public GeneriMappItemizedOverlay(Drawable defaultMarker, Context context) {
		  super(boundCenterBottom(defaultMarker));
		  mContext = context;
		  populate();
	}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int arg0) {
		return mOverlays.get(arg0);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  if(item.getTitle() != "MyLocation"){
		  Intent intent = new Intent(mContext, BlipPage.class);
		  intent.putExtra("ID", item.getTitle());
		  intent.putExtra("SNIPPET", item.getSnippet());
		  mContext.startActivity(intent);
	  }
	  return true;
	}
	    

}
