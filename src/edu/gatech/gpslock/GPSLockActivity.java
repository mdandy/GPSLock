package edu.gatech.gpslock;

import java.util.ArrayList;

import edu.gatech.gpslock.util.GPSBroadcaster;
import edu.gatech.gpslock.util.GPSCond;
import edu.gatech.gpslock.util.GPSLock;
import edu.gatech.gpslock.util.GPSMockLocation;
import edu.gatech.gpslock.util.GPSUtil;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

public class GPSLockActivity extends Activity
{
	private final String TAG = "GPSLockActivity";
	
	private Intent intent;
	private ScrollView svConsole;
	private TextView tvConsole;
	private GPSBroadcaster broadcaster;
	private boolean isCritical;
	
	private ArrayList<Location> locations;
	private int timer;
	
	long startTime;
	long endTime;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simulator);
		
		this.svConsole = (ScrollView)findViewById(R.id.svConsole);
		this.tvConsole = (TextView)findViewById(R.id.tvConsole);
		this.tvConsole.setText("Initiating...\n");
		
		intent = getIntent();
		String source = intent.getStringExtra("source");
		
		/* Setup */
		this.broadcaster = GPSBroadcaster.getInstance(GPSLockActivity.this);
		this.isCritical = false;
		
		if (source.equals("map"))
		{
			/* Creating test threads */
			GPSTesterFromMap test1 = this.new GPSTesterFromMap();
			test1.execute(new String[] { "map1" });
			
			GPSTesterFromMap test2 = this.new GPSTesterFromMap();
			test2.execute(new String[] { "map2" });
			
			GPSTesterFromMap test3 = this.new GPSTesterFromMap();
			test3.execute(new String[] { "map3" });
		}
		else if (source.equals("kml"))
		{	
			/* Timestamp */
			startTime = System.nanoTime();
			
			/* Parse the locations */
			ArrayList<String> raws = intent.getStringArrayListExtra("locations");
			locations = GPSUtil.parseLocation(raws);
			
			/* Setup the timer */
			timer = intent.getIntExtra("timer", 3000);	//2s
			
			/* Start the Mock Location */
			GPSMockLocation gpsMock = new GPSMockLocation(GPSLockActivity.this, locations, timer);
			gpsMock.start(1);
			
			/* Create random number */
			int index1 = (int)(Math.random() * (locations.size()));
			
			int index2 = 0;
			do{
				index2 = (int)(Math.random() * (locations.size()));
			} while (index2 == index1);
			
			int index3 = 0;
			do{
				index3 = (int)(Math.random() * (locations.size()));
			} while ((index3 == index2) || (index3 == index1));
			
			/* Creating test threads */
			GPSTesterFromKML test1 = this.new GPSTesterFromKML();
			test1.execute(new String[] { "kml1", "" + index1 });
			
			GPSTesterFromKML test2 = this.new GPSTesterFromKML();
			test2.execute(new String[] { "kml2", "" + index2 });
			
			GPSTesterFromKML test3 = this.new GPSTesterFromKML();
			test3.execute(new String[] { "kml3", "" + index3 });
		}
	}
	
	private class GPSTesterFromMap extends AsyncTask<String, String, Void>
	{
		private String name = "init";
		private GPSLock gpsLock;
		
		@Override
		protected void onPreExecute() 
		{
			/* Set up the lock */
			double latitude = ((double)intent.getIntExtra("latitude", 0)) / 1000000;
			double longitude = ((double)intent.getIntExtra("longitude", 0)) / 1000000;
			Log.d(TAG, "latitudeE6: " + intent.getIntExtra("latitude", 0));
			Log.d(TAG, "longitudeE6: " + intent.getIntExtra("longitude", 0));
			
			Location location = new Location("");
			location.setLatitude(latitude);		// unit: degree
			location.setLongitude(longitude);	// unit: degree
			
			echo("Latitude: " + latitude + "\n");
			echo("Longitude: " + longitude + "\n");
			
			gpsLock = new GPSLock(location, 1000, 60 * 1000);
			broadcaster.register(gpsLock);
		}
		
		@Override
		protected Void doInBackground(String... params) 
		{
			name = params[0];
			
			echo("Sleeping...\n");	
			try 
			{				
				gpsLock.lock();
				while (isCritical)
					GPSCond.gpsCondWait(gpsLock);
				isCritical = true;
				
				echo ("Critical Section...\n");
				Thread.sleep(2000);
				echo ("Done with Critical Section...\n");
				
				gpsLock.unlock();
				isCritical = false;
			}
			catch (InterruptedException e) 
			{
				Log.e(TAG, "InterruptedException: " + e.getMessage());
			}
			echo("Waking up...\n");	
			echo("You're there...\n");
			
			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) 
		{
			tvConsole.append(name + " " + values[0]);
			svConsole.fullScroll(ScrollView.FOCUS_DOWN);
		}
		
		/**
		 * Echo message to the console.
		 * @param message the message to be displayed
		 */
		private void echo(String message)
		{
			publishProgress(message);
		}
	}
	
	private class GPSTesterFromKML extends AsyncTask<String, String, Void>
	{
		private String name = "init";
		private GPSLock gpsLock;
		
		@Override
		protected void onPreExecute() 
		{ 
			// NOP
		}
		
		@Override
		protected void onPostExecute(Void result) 
		{
			/* Timestamp */
			endTime = System.nanoTime();
			long timeDifference = endTime - startTime;
			echo("Runtime: " + timeDifference + " ns\n");
		}
		
		@Override
		protected Void doInBackground(String... params) 
		{
			name = params[0];
			
			/* Set up the lock */
			int index = Integer.parseInt(params[1]);
			Log.d(TAG, "latitude: " + locations.get(index).getLatitude());
			Log.d(TAG, "longitude: " + locations.get(index).getLongitude());
			
			echo("Latitude: " + locations.get(index).getLatitude() + "\n");
			echo("Longitude: " + locations.get(index).getLongitude() + "\n");
			
			gpsLock = new GPSLock(locations.get(index), 20, 60 * 1000);
			broadcaster.register(gpsLock);
			
			echo("Sleeping...\n");	
			try 
			{				
				gpsLock.lock();
				while (isCritical)
					GPSCond.gpsCondWait(gpsLock);
				isCritical = true;
				
				echo ("Critical Section...\n");
				Thread.sleep(2000);
				echo ("Done with Critical Section...\n");
				
				gpsLock.unlock();
				isCritical = false;
			} 
			catch (InterruptedException e) 
			{
				Log.e(TAG, "InterruptedException: " + e.getMessage());
			}
			echo("Waking up...\n");	
			echo("You're there...\n");
			
			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) 
		{
			tvConsole.append(name + " " + values[0]);
			svConsole.fullScroll(ScrollView.FOCUS_DOWN);
		}
		
		/**
		 * Echo message to the console.
		 * @param message the message to be displayed
		 */
		private void echo(String message)
		{
			publishProgress(message);
		}
	}
}
