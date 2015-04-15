package com.test.appcomponenttest;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.appcomponenttest.providers.HeadacheLogProvider;
import com.test.appcomponenttest.sqldb.MySqliteHelper;

public class DetailActivity extends Activity {
	
	final int SIZE = 20;

	Cursor cursor;
	TextView tv_date;
	TextView tv_time;
	TextView tv_severity;
	TextView tv_sun;
	TextView tv_crcn;
	TextView tv_nprcn;
	TextView tv_note;
	ImageView im_severity;
	ImageView im_sun;
	ImageView im_crcn;
	ImageView im_nprcn;
	LinearLayout layout;
	String bookMark;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		Bundle bundle = getIntent().getExtras();
		int id = (Integer) bundle.get("id");
		String severity = bundle.getString("list");
		String[] projection = null;//{ MySqliteHelper.COLUMN_SEVERITY };
		String selection = MySqliteHelper.COLUMN_SEVERITY + " = ?";
		layout = (LinearLayout) findViewById(R.id.detail_layout);
		
		tv_date = (TextView)findViewById(R.id.date_tv);
		tv_time = (TextView)findViewById(R.id.time_tv);
		tv_severity = (TextView)findViewById(R.id.m_s_text);
		tv_sun = (TextView)findViewById(R.id.s_text);
		tv_crcn = (TextView)findViewById(R.id.crocin_text);
		tv_nprcn = (TextView)findViewById(R.id.neprocin_text);
		tv_note = (TextView) findViewById(R.id.note_text1);
		
		im_severity = (ImageView) findViewById(R.id.m_s_tick);
		im_sun = (ImageView) findViewById(R.id.s_tick);
		im_crcn = (ImageView) findViewById(R.id.crocin_tick);
		im_nprcn = (ImageView) findViewById(R.id.neprocin_tick);
		

		if ("all".equals(severity)) {
			Uri CONTENT_ID_URI = Uri.parse(HeadacheLogProvider.CONTENT_URI
					.toString() + "/" + (id + 1));
			cursor = getApplicationContext().getContentResolver().query(
					CONTENT_ID_URI, projection, null, null, null);
			setViews(0);
			setBackground();
			setImages();
			
			/*cursor.moveToFirst();
			
			tv_date.setText(cursor.getString(1));
			tv_
			bookMark = cursor.getString(7);
			if (bookMark.equals("0")) {
				layout.setBackgroundResource(R.drawable.item_bg_green);
			} else {
				layout.setBackgroundResource(R.drawable.item_bg_red);
			}*/
			
			
			
		} else if ("mild".equals(severity)) {
			cursor = getApplicationContext().getContentResolver().query(
					HeadacheLogProvider.CONTENT_URI, projection, selection,
					new String[] { severity }, "_id DESC");
			setViews(id);
			setBackground();
			setImages();
		} else {
			cursor = getApplicationContext().getContentResolver().query(
					HeadacheLogProvider.CONTENT_URI, projection, selection,
					new String[] { severity }, "_id DESC");
			setViews(id);
			setBackground();
			setImages();
		}
		/*
		 * TextView detail = (TextView) findViewById(R.id.tv1);
		 * detail.setOnClickListener(new View.OnClickListener(){
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * }
		 * 
		 * });
		 */
	}
	
	void setViews(int index){
			cursor.moveToPosition(index);
		
		String et_Value = cursor.getString(8);
		tv_date.setText(cursor.getString(1));
		tv_time.setText(cursor.getString(2));
		tv_severity.setText(cursor.getString(3).toUpperCase());
		tv_sun.setText("Sun Exposure"/*cursor.getInt(6)*/);
		tv_crcn.setText("Crocin"/*cursor.getInt(4)*/);
		tv_nprcn.setText("Neprocin"/*cursor.getInt(5)*/);
		tv_note.setText(et_Value.equals("") ? "No note" : et_Value);
	}
	void setBackground(){
		bookMark = cursor.getString(7);
		if (bookMark.equals("0")) {
			layout.setBackgroundResource(R.drawable.layout_bg_whitegreen);
		} else {
			layout.setBackgroundResource(R.drawable.layout_bg_whitered);
			tv_date.setTextAppearance(getApplicationContext(), R.style.TextViewFontRed);
			tv_date.setTextSize(SIZE);
			tv_time.setTextAppearance(getApplicationContext(), R.style.TextViewFontRed);
			tv_time.setTextSize(SIZE);
			tv_severity.setTextAppearance(getApplicationContext(), R.style.TextViewFontRed);
			tv_severity.setTextSize(SIZE);
			tv_sun.setTextAppearance(getApplicationContext(), R.style.TextViewFontRed);
			tv_sun.setTextSize(SIZE);
			tv_crcn.setTextAppearance(getApplicationContext(), R.style.TextViewFontRed);
			tv_crcn.setTextSize(SIZE);
			tv_nprcn.setTextAppearance(getApplicationContext(), R.style.TextViewFontRed);
			tv_nprcn.setTextSize(SIZE);
		}
	}
	void setImages(){
		im_severity.setImageDrawable(getResources().getDrawable(R.drawable.tick2));
		
		if(cursor.getInt(4) == 0) im_crcn.setImageDrawable(getResources().getDrawable(R.drawable.cross));
		else im_crcn.setImageDrawable(getResources().getDrawable(R.drawable.tick2));
		
		if(cursor.getInt(5) == 0) im_nprcn.setImageDrawable(getResources().getDrawable(R.drawable.cross));
		else im_nprcn.setImageDrawable(getResources().getDrawable(R.drawable.tick2));
		
		if(cursor.getInt(6) == 0) im_sun.setImageDrawable(getResources().getDrawable(R.drawable.cross));
		else im_sun.setImageDrawable(getResources().getDrawable(R.drawable.tick2));
	}
}
