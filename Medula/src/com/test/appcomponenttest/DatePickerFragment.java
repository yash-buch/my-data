package com.test.appcomponenttest;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

	private final String TAG = "DatePickerFragment";
	DatePickerListener dpListener;
	
	public DatePickerFragment(HomeFragment hfContext){
		super();
		try{
			dpListener = (DatePickerListener) hfContext;
		}catch(Exception e){
			Log.i(TAG, "" + e);
		} 
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		dpListener.onHandleDateChange(year, monthOfYear, dayOfMonth);		
	}
	
	public interface DatePickerListener {
		void onHandleDateChange(int year, int monthOfYear, int dayOfMonth);
	}

}
