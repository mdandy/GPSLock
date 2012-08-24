package edu.gatech.gpslock.util;

import java.util.LinkedList;

import android.content.Context;
import android.location.Location;
import android.util.Log;

public class GPSBroadcaster 
{
	private final static String TAG = "GPSScheduler";
	private static GPSBroadcaster mInstance;

	private LinkedList<GPSLock> mGPSPool;

	public static GPSBroadcaster getInstance(Context context)
	{
		if (mInstance == null)
			mInstance = new GPSBroadcaster(context);
		return mInstance;
	}

	private GPSBroadcaster(Context context)
	{
		this.mGPSPool = new LinkedList<GPSLock>();
	}

	public void register(GPSLock lock)
	{
		synchronized (mGPSPool)
		{
			if (!mGPSPool.contains(lock))
				mGPSPool.add(lock);
		}
	}

	public void broadcast(Location location)
	{
		synchronized (mGPSPool) 
		{
			for (GPSLock gpsLock : mGPSPool)
			{
				double delta = gpsLock.getLocation().distanceTo(location);
				Log.d(TAG, "delta = " + delta + " ; radius: " + gpsLock.getRadius());
				if (Double.compare(delta, gpsLock.getRadius()) < 0)
				{
					synchronized (gpsLock) 
					{
						gpsLock.notifyAll();
					}
				}
			}
		}
	}
}
