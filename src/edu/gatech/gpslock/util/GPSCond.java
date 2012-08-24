package edu.gatech.gpslock.util;

public class GPSCond 
{
	private static GPSCond mInstance;
	
	public static GPSCond getInstance()
	{
		if (mInstance == null)
			mInstance = new GPSCond();
		return mInstance;
	}
	
	public static void gpsCondWait(GPSLock gpsLock) throws InterruptedException
	{
		// Release the lock
		gpsLock.unlock();
		
		// Sleep till the device is in the proximity of the lock's location
		synchronized (gpsLock) 
		{
			//lock.wait(lock.getTimeout());
			gpsLock.wait();
		}
		
		// Acquire the lock
		gpsLock.lock();
	}
}
