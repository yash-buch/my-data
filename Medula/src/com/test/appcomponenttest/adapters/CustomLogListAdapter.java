package com.test.appcomponenttest.adapters;


import com.test.appcomponenttest.R;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomLogListAdapter extends SimpleCursorAdapter {

	Context mcontext;
	
	public CustomLogListAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		mcontext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// get reference to the row
		View view = super.getView(position, convertView, parent);
		TextView bookmark = (TextView) view.findViewById(R.id.bm_check);
		TextView date = (TextView) view.findViewById(R.id.date);
		TextView divider = (TextView) view.findViewById(R.id.div);
		TextView severity = (TextView) view.findViewById(R.id.svrty);
		
		if(bookmark.getText().toString().equals("1")){
			date.setTextAppearance(mcontext, R.style.TextViewFontRed);
			divider.setTextAppearance(mcontext, R.style.TextViewFontRed);
			divider.setTypeface(null,Typeface.BOLD);
			divider.setTextSize(30);
			severity.setTextAppearance(mcontext, R.style.TextViewFontRed);
			view.setBackgroundResource(R.drawable.item_bg_red);
		} else{
			date.setTextAppearance(mcontext, R.style.TextViewFont);
			divider.setTextAppearance(mcontext, R.style.TextViewFont);
			divider.setTypeface(null,Typeface.BOLD);
			divider.setTextSize(30);
			severity.setTextAppearance(mcontext, R.style.TextViewFont);
			view.setBackgroundResource(R.drawable.item_bg_green);
		}
		// check for odd or even to set alternate colors to the row background
		/*if (position % 2 == 0) {
			view.setBackgroundColor(Color.rgb(100, 100, 100));// 238, 233, 233
		} else {
			view.setBackgroundColor(Color.rgb(255, 255, 255));
		}*/
		return view;
	}

}
