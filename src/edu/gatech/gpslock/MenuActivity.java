package edu.gatech.gpslock;

import edu.gatech.gpslock.util.Locator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity implements OnClickListener
{
	private Button btnMap;
	private Button btnFileChooser;
	private Button btnReminder;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.btnMap = (Button) findViewById(R.id.BtnMap);
		this.btnMap.setOnClickListener(this);

		this.btnFileChooser = (Button) findViewById(R.id.BtnFile);
		this.btnFileChooser.setOnClickListener(this);

		this.btnReminder = (Button) findViewById(R.id.BtnReminder);
		this.btnReminder.setOnClickListener(this);
		
		/* Start GPSListener */
		Locator.getInstance(this);
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = null;
		switch (v.getId())
		{
			case R.id.BtnMap:
				intent = new Intent(this, LocationSelectionActivity.class);
				break;
			case R.id.BtnFile:
				intent = new Intent(this, FileChooserActivity.class);
				break;
			case R.id.BtnReminder:
				intent = new Intent(this, ReminderActivity.class);
		}
		startActivity(intent);
	}
}