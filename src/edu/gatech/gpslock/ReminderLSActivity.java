package edu.gatech.gpslock;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MyLocationOverlay;

import edu.gatech.gpslock.util.MyItemizedOverlay;

public class ReminderLSActivity extends MapActivity implements OnDoubleTapListener
{
	private LocationSelectionView mapView;
	private Button submitCoords;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		mapView = (LocationSelectionView)findViewById(R.id.mapview);
		submitCoords = (Button)findViewById(R.id.btnSubmitCoords);
		
		mapView.setBuiltInZoomControls(true);
		
		Drawable marker = getResources().getDrawable(android.R.drawable.star_on);
		MyItemizedOverlay io = new MyItemizedOverlay(marker, ReminderLSActivity.this);
		/*positionOverlay = new */
		mapView.getOverlays().add(io);
		
		MyLocationOverlay mLocationOverlay = new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(mLocationOverlay);
		mLocationOverlay.enableMyLocation();
		mLocationOverlay.enableCompass();
		
		submitCoords.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				runOnUiThread(new Runnable() {
					public void run() {
						GeoPoint gp = mapView.getMarkerLocation();
						Intent returnIntent = new Intent();
						returnIntent.putExtra("latitude", gp.getLatitudeE6());	// unit: microdegree
						returnIntent.putExtra("longitude", gp.getLongitudeE6());	// unit: microdegree
						setResult(RESULT_OK, returnIntent);
						finish();
		            }
				});
				
			}
		});
	}		

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean onDoubleTap(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean onDoubleTapEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
			
			/*MyItemizedOverlay io = (MyItemizedOverlay) mapView.getOverlays();
			int lat = (int)p.getLatitudeE6();
			int lng = (int)p.getLongitudeE6();
		
			GeoPoint point = new GeoPoint(lat, lng);
			
			io.addItem(point, "", "");*/
			

			/*Projection proj = mapView.getProjection();
			
			mapView.setMapCenter(proj.fromPixels((int)e.getX(), (int)e.getY()));
			Toast t = Toast.makeText(
					mapView.getContext(), 
					"lat: "+mapView.getMapCenter().getLatitudeE6() + ", long:" + mapView.getMapCenter().getLongitudeE6(), 
					Toast.LENGTH_SHORT);
			t.show();*/
			
			return true;
	}

}
