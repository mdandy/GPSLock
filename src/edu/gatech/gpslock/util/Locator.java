package edu.gatech.gpslock.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class Locator implements LocationListener
{
	private static Locator mInstance;
	private LocationManager mLocationManager;
	private GPSBroadcaster mBroadcaster;
	
	public static Locator getInstance(Context context) 
	{
		if(mInstance == null)
			mInstance = new Locator(context);
		return mInstance;
	}
	
	private Locator(Context context) 
	{
		this.mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		this.mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		this.mBroadcaster = GPSBroadcaster.getInstance(context);
	}
	
	@Override
	public void onLocationChanged(Location location) 
	{
		//Log.d("Locator", "onLocationChanged");
		Locator.this.mBroadcaster.broadcast(location);
	}

	@Override
	public void onProviderDisabled(String provider) 
	{
		// NOP
	}

	@Override
	public void onProviderEnabled(String provider) 
	{
		// NOP
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		// NOP
	}

}
