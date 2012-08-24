package edu.gatech.gpslock.util;

import java.util.ArrayList;

import android.content.Context;
import android.location.Location;
import android.util.Log;

public class GPSMockLocation 
{
	private final String TAG = "GPSLockActivity";

	private Context context;
	private GPSBroadcaster broadcaster;
	private ArrayList<Location> locations;
	private int timer;

	public GPSMockLocation(Context context, ArrayList<Location> locations, int timer)
	{
		this.context = context;
		this.locations = locations;
		this.timer = timer;
	}

	public void start(int numLoop)
	{
		broadcaster = GPSBroadcaster.getInstance(context);
		GPSSetterThread gpsSetter = this.new GPSSetterThread();
		gpsSetter.runForest(numLoop);
	}

	private class GPSSetterThread implements Runnable
	{
		private Thread thread;
		private int numLoop;

		public GPSSetterThread()
		{
			thread = new Thread(this);
		}

		public void runForest(int numLoop)
		{
			this.numLoop = numLoop;
			thread.start();
		}

		@Override
		public void run()
		{
			for (int i = 0; i < numLoop; i++)
			{
				for (Location location : locations)
				{
					try 
					{
						//					Location mock = new Location ("KML_MOCK_LOCATION");
						//					mock.setLatitude(location.getLatitude());
						//					mock.setLongitude(location.getLongitude());
						//
						//					lm.setTestProviderLocation("KML_MOCK_LOCATION", mock); 
						//					lm.setTestProviderEnabled("KML_MOCK_LOCATION", true); 

						broadcaster.broadcast(location);
						Thread.sleep(timer);
					} 
					catch (InterruptedException e) 
					{
						Log.e(TAG, "InterruptedException: " + e.getMessage());
					}
				}
			}
		}

	}
}
