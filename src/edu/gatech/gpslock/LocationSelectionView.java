package edu.gatech.gpslock;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

public class LocationSelectionView extends MapView {
	private GeoPoint markerPos;
	
	public LocationSelectionView(Context context, String apiKey) {
		super(context, apiKey);
		
		//gd = new GestureDetector(gestureListener);
	}

	public LocationSelectionView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LocationSelectionView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void setMarkerLocation(GeoPoint gp){
		markerPos = gp;
	}
	
	public GeoPoint getMarkerLocation()
	{
		return markerPos;
	}
	

	/**
	 * This method is called every time user touches the map, drags a finger on
	 * the map, or removes finger from the map.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//gd.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	/*OnGestureListener gestureListener = new OnGestureListener()
	{
		public boolean onSingleTapUp(MotionEvent e)
		{
			Projection proj = LocationSelectionView.this.getProjection();
			
			mapCenter = proj.fromPixels((int)e.getX(), (int)e.getY());
			Toast t = Toast.makeText(
					LocationSelectionView.this.getContext(), 
					"lat: "+mapCenter.getLatitudeE6() + ", long:" + mapCenter.getLongitudeE6(), 
					Toast.LENGTH_SHORT);
			return true;
		}

		@Override
		public boolean onDown(MotionEvent arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
				float arg3) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onLongPress(MotionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
				float arg3) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onShowPress(MotionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	};*/

}
