package edu.gatech.gpslock.util;

import java.util.ArrayList;
 
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.Toast;
 
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import edu.gatech.gpslock.LocationSelectionView;
 
public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	 
private ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();
private Context context;
 
public MyItemizedOverlay(Drawable marker, Context c) {
	super(boundCenter(marker));
	// TODO Auto-generated constructor stub
	populate();
	context = c;
}

@Override
protected boolean onTap(int index) {
	// TODO Auto-generated method stub
	//return super.onTap(index);
	 
	Toast.makeText(context,
	 "Touch on marker: \n" + overlayItemList.get(index).getTitle(),
	 Toast.LENGTH_LONG).show();
	 
	return true;
}

@Override
public boolean onTap(GeoPoint point, MapView mapview)
{
	addItem(point, "", "");
	LocationSelectionView lsv = (LocationSelectionView) mapview;
	lsv.setMarkerLocation(point);
	/*Toast t = Toast.makeText(
			context, 
			"lat: "+point.getLatitudeE6() + ", long:" + point.getLongitudeE6(), 
			Toast.LENGTH_SHORT);
	t.show();*/
	
	return true;
}
 
public void addItem(GeoPoint p, String title, String snippet){
	if(overlayItemList.size() == 1)
		overlayItemList.remove(0);
	OverlayItem newItem = new OverlayItem(p, title, snippet);
	overlayItemList.add(newItem);
	populate();
}

public GeoPoint getMarkerLocation(int index){
	
	if(overlayItemList.size() == 1)
		return overlayItemList.get(0).getPoint();
	return null;
}
 
@Override
protected OverlayItem createItem(int i) {
	// TODO Auto-generated method stub
	return overlayItemList.get(i);
}
 
@Override
public int size() {
	// TODO Auto-generated method stub
	return overlayItemList.size();
}
 
@Override
public void draw(Canvas canvas, MapView mapView, boolean shadow) {
	// TODO Auto-generated method stub
	super.draw(canvas, mapView, shadow);
	}
}