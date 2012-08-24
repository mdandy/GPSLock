package edu.gatech.gpslock.util;

import java.util.ArrayList;

import edu.gatech.gpslock.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("rawtypes")
public class ReminderListAdapter extends ArrayAdapter {
	private ArrayList<Reminder> reminders;
	private Context context;
	private int layout;
	
	@SuppressWarnings("unchecked")
	public ReminderListAdapter(Context context, int textViewResourceId, ArrayList<Reminder> reminders) {
		super(context, textViewResourceId, reminders);
		this.reminders = reminders;
		this.context = context;
		this.layout = textViewResourceId;
	}
	
	public void addReminder(Reminder reminder)
	{
		this.reminders.add(reminder);
		 notifyDataSetChanged();
	}
	
	public int Count()
	{
		return reminders.size(); 
	}

	public Object GetItem(int position)
	{
		return position;
	}

	public long GetItemId(int position)
	{
		return position;
	}
	
	public Reminder GetItemAtPosition(int position)
	{
		return reminders.get(position);
	}

	public View getView(final int position, View convertView, ViewGroup parent){
		View view;
        final Reminder reminder = reminders.get(position);
        
        if (convertView == null) {
        	LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(layout, null);
        }
        else
        {
        	view = convertView;
        }

        TextView name = (TextView)view.findViewById(R.id.row_name);
        TextView location = (TextView)view.findViewById(R.id.row_location);
        
        
        
        view.setOnLongClickListener(new View.OnLongClickListener(){
        
        	@Override
             public boolean onLongClick(View v) {
        		new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete Reminder")
                .setMessage("Do you want to delete this Reminder")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	reminders.remove(position);
                		ReminderListAdapter.this.notifyDataSetChanged();
                		Toast.makeText(context, "Reminder: " + 
                		reminder.getName() + " deleted.", Toast.LENGTH_SHORT).show();
                    }

                })
                .setNegativeButton("No", null)
                .show();
        		
				return true;
            }
        	
        });
        
        view.setOnClickListener(new View.OnClickListener(){
        	@Override
             public void onClick(View v) {
        		Toast.makeText(context, "ALL DECISIONS FINAL.", Toast.LENGTH_SHORT).show();
            }
        	
        });
        
        name.setText(reminder.getName());
        location.setText(reminder.getNotes());
        return view;
    }
}
