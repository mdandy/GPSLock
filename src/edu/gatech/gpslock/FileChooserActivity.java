package edu.gatech.gpslock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.gatech.gpslock.util.GPSUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FileChooserActivity extends Activity implements OnClickListener
{
	private String TAG = "FileChooserActivity";

	private TextView txtFilepath;
	private TextView txtTimer;
	private TextView txtStatus;

	@Override 
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filechooser);

		this.txtFilepath = (TextView)findViewById(R.id.TxtFilePath);
		this.txtTimer = (TextView)findViewById(R.id.TxtTimer);
		this.txtStatus = (TextView)findViewById(R.id.TxtStatus);

		this.txtFilepath.setText("/mnt/sdcard/trip.kml");
		this.txtTimer.setText("3000");

		Button btnLoad = (Button)findViewById(R.id.BtnLoad);
		btnLoad.setOnClickListener(this);	
	}

	@Override
	public void onClick(View v) 
	{
		String filepath = txtFilepath.getText().toString();
		int timer = Integer.parseInt(txtTimer.getText().toString());

		try 
		{
			/* Open the KML file */
			File file = new File (filepath);
			BufferedReader reader = new BufferedReader(new FileReader(file));

			/* Read the file */
			StringBuffer buffer = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) 
			{
				buffer.append(line);
			}

			/* Parse the KML */
			ArrayList<String> locations = GPSUtil.parseKML(buffer.toString());

			/* Start GPSLockActivity */
			Intent intent = new Intent(FileChooserActivity.this, GPSLockActivity.class);
			intent.putExtra("source", "kml");
			intent.putExtra("timer", timer);
			intent.putStringArrayListExtra("locations", locations);
			startActivity(intent);
		} 
		catch (FileNotFoundException e) 
		{
			Log.e(TAG, "FileNotFoundException: " + e.getMessage());
			txtStatus.setText("FileNotFoundException: " + e.getMessage());
		} 
		catch (IOException e) 
		{
			Log.e(TAG, "IOException: " + e.getMessage());
			txtStatus.setText("IOException: " + e.getMessage());
		}
		catch (IllegalArgumentException e)
		{
			Log.e(TAG, "IllegalArgumentException: " + e.getMessage());
			txtStatus.setText("IllegalArgumentException: " + e.getMessage());
		}
	}
}
