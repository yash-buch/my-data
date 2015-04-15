package com.test.appcomponenttest;

import java.util.Calendar;

import com.test.appcomponenttest.DatePickerFragment.DatePickerListener;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;
import android.app.Dialog;
import android.app.TimePickerDialog;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

	TimePickerListener tpListener;
	private final String TAG = "TimePickerFragment";
	
	public TimePickerFragment(HomeFragment hfContext){
		super();
		try{
			tpListener = (TimePickerListener) hfContext;
		}catch(Exception e){
			Log.i(TAG, "" + e);
		} 
	}
	
	 @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the current time as the default values for the picker
	        final Calendar c = Calendar.getInstance();
	        int hour = c.get(Calendar.HOUR_OF_DAY);
	        int minute = c.get(Calendar.MINUTE);

	        // Create a new instance of TimePickerDialog and return it
	        return new TimePickerDialog(getActivity(), this, hour, minute,
	                DateFormat.is24HourFormat(getActivity()));
	    }
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		tpListener.onHandleTimeChange(hourOfDay, minute);
	}

	public interface TimePickerListener {
		void onHandleTimeChange(int hourOfDay, int minute);
	}

}
