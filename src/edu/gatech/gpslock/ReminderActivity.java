package edu.gatech.gpslock;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.gatech.gpslock.util.Reminder;
import edu.gatech.gpslock.util.ReminderListAdapter;

public class ReminderActivity extends Activity implements OnClickListener {
	public ListView reminderList;
	private ReminderListAdapter listAdapter;
	private Button btnAdd;
	private TextView emptyView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_app);
		reminderList = (ListView)findViewById(R.id.gpsLV);
		btnAdd = (Button)findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(this);
		emptyView = (TextView)findViewById(android.R.id.empty);
		
		//emptyView.setText("Currently No Reminders");
		emptyView.setTextSize(22);
		reminderList.setEmptyView(emptyView);
		
		listAdapter = new ReminderListAdapter(this, 
				R.layout.list_item, 
				new ArrayList<Reminder>()
		);
		reminderList.setAdapter(listAdapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.add_item: 
	    	//do stuff;
	    	return true;
	    default:
	    	return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onClick(View v)
	{
		Intent intent = null;
		switch (v.getId())
		{
		case R.id.btnAdd:
    		intent = new Intent(this, NewReminderActivity.class);
    		startActivityForResult(intent, 1);
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
	    switch(requestCode) {
	    case 1: 
	          if (resultCode == RESULT_OK) {
	        	  Reminder reminder = new Reminder(
	        			  data.getStringExtra("name"), 
	        			  data.getStringExtra("notes"), 
	        			  data.getIntExtra("longitude", 0), 
	        			  data.getIntExtra("latitude", 0) 
	        	  );
	        	  listAdapter.addReminder(reminder);
	              break;
	          }
	    }
    }
}
