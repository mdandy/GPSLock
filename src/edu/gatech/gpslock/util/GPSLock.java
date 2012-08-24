package edu.gatech.gpslock.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.location.Location;
import android.util.Log;

public class GPSLock 
{
	private final String TAG = "GPSLock";
	
	private Location mLocation;
	private double mRadius;
	private int mTimeout;
	private Lock mLock;
	
	/**
	 * Constructor
	 * @param location The geo-location of this condition variable
	 */
	public GPSLock (Location location)
	{
		this(location, 1000.0);
	}
	
	/**
	 * Constructor
	 * @param location the geo-location of this condition variable
	 * @param radium the radius of proximity in meters
	 */
	public GPSLock (Location location, double radius)
	{
		this(location, radius, 1000);
	}
	
	/**
	 * Constructor
	 * @param location the geo-location of this condition variable
	 * @param radium the radius of proximity in meters
	 * @param timeout the lock's timeout in milliseconds
	 */
	public GPSLock (Location location, double radius, int timeout)
	{
		this.mLocation = location;
		this.mRadius = radius;
		this.mTimeout = timeout;
		this.mLock = new ReentrantLock();
	}

	public Location getLocation() 
	{
		return mLocation;
	}

	public double getRadius() 
	{
		return mRadius;
	}

	public int getTimeout() 
	{
		return mTimeout;
	}
	
	public void lock() throws InterruptedException
	{
		this.mLock.lock();
	}
	
	public void unlock()
	{
		try
		{
			this.mLock.unlock();
		}
		catch (IllegalMonitorStateException e)
		{
			Log.e(TAG, "IllegalMonitorStateException: " + e.getMessage());
		}
	}

	@Override
	public boolean equals(Object o) 
	{
		GPSLock other = (GPSLock)o;
		return this.mLocation.equals(other.mLocation);
	}
}
