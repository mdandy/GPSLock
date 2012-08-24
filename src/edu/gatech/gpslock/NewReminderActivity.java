package edu.gatech.gpslock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewReminderActivity extends Activity implements OnClickListener {
	private EditText etName;
	private EditText etNotes;
	private Button btnGetLocation;
	private TextView tvLongLat;
	private Button btnCreate;
	private int longitude;
	private int latitude;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_reminder);

		etName = (EditText)findViewById(R.id.etName);
		etNotes = (EditText)findViewById(R.id.etNotes);
		btnGetLocation = (Button)findViewById(R.id.btnGetLocation);
		tvLongLat = (TextView)findViewById(R.id.tvLongLat);
		btnCreate = (Button)findViewById(R.id.btnCreate);
		btnGetLocation.setOnClickListener(this);
		btnCreate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = null;
		switch (v.getId())
		{
		case R.id.btnGetLocation:
			intent = new Intent(this, ReminderLSActivity.class);
			startActivityForResult(intent, 1);
		case R.id.btnCreate:
			if(longitude != 0 && latitude != 0)
			{
				Intent returnIntent = new Intent();
			    returnIntent.putExtra("name", etName.getText().toString());
			    returnIntent.putExtra("notes", etNotes.getText().toString());
			    returnIntent.putExtra("longitude", longitude);
			    returnIntent.putExtra("latitude", latitude);
			    setResult(RESULT_OK, returnIntent);
				finish();
			}
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
	    switch(requestCode) {
	    case 1: 
	          if (resultCode == RESULT_OK) {
	        	  longitude = data.getIntExtra("longitude", 0);
	        	  latitude  = data.getIntExtra("latitude", 0);
	        	  tvLongLat.setText(latitude + ", " + longitude);
	              break;
	          }
	    }
    }
}
