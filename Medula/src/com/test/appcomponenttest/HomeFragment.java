package com.test.appcomponenttest;

import java.util.Calendar;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.appcomponenttest.DatePickerFragment.DatePickerListener;
import com.test.appcomponenttest.TimePickerFragment.TimePickerListener;

public class HomeFragment extends Fragment implements DatePickerListener,
		TimePickerListener {

	DBAccessor dbAccessor;
	LogEntry entry = new LogEntry();
	ImageView im_low;
	ImageView im_high;
	ImageView im_sunexpo;
	ImageView im_fav;
	ImageView im_crocin;
	ImageView im_neprocin;
	EditText et_note;
	Button bt_submit;
	View v;
	/* Button changeTime; */
	/* Button changeDate; */
	LinearLayout dateLayout;
	LinearLayout timeLayout;
	TextView dayTV;
	TextView mnthTV;
	TextView yrTV;
	TextView hrTV;
	TextView minTV;

	int enterCount = 0;
	int prevKeyCode = 0;
	boolean isPrevEnter = false;

	HashMap<Integer, Boolean> isCheckedMap = new HashMap<Integer, Boolean>();

	View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			checkAndDrawTick(v);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		v = inflater.inflate(R.layout.fragment_home, container, false);

		v.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
						.getWindowToken(), 0);
				return true;
			}
		});

		im_low = (ImageView) v.findViewById(R.id.low_intensity);
		im_high = (ImageView) v.findViewById(R.id.high_intensity);
		im_sunexpo = (ImageView) v.findViewById(R.id.sun_expo);
		im_fav = (ImageView) v.findViewById(R.id.fav);
		im_crocin = (ImageView) v.findViewById(R.id.crcn);
		im_neprocin = (ImageView) v.findViewById(R.id.nprcn);
		et_note = (EditText) v.findViewById(R.id.et_note);
		bt_submit = (Button) v.findViewById(R.id.sbmt);
		dayTV = (TextView) v.findViewById(R.id.dd);
		mnthTV = (TextView) v.findViewById(R.id.mm);
		yrTV = (TextView) v.findViewById(R.id.yy);
		hrTV = (TextView) v.findViewById(R.id.hour);
		minTV = (TextView) v.findViewById(R.id.min);

		// initialize isCheckedMap
		isCheckedMap.put(R.id.low_intensity, false);
		isCheckedMap.put(R.id.high_intensity, false);
		isCheckedMap.put(R.id.sun_expo, false);
		isCheckedMap.put(R.id.fav, false);
		isCheckedMap.put(R.id.crcn, false);
		isCheckedMap.put(R.id.nprcn, false);

		checkAndDrawTick(im_low); // To show the default check on low

		initializeDateAndTime();

		dbAccessor = new DBAccessor(getActivity());

		im_low.setOnClickListener(clickListener);
		im_high.setOnClickListener(clickListener);
		im_sunexpo.setOnClickListener(clickListener);
		im_crocin.setOnClickListener(clickListener);
		im_neprocin.setOnClickListener(clickListener);

		et_note.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_ENTER
						&& event.getAction() == KeyEvent.ACTION_UP) {
					Log.i("onKey", "enter:"+enterCount);
					isPrevEnter = true;
					String text = ((EditText) v).getText().toString();
					if (enterCount >= 3) {
						int lastBreakIndex = text.lastIndexOf("\n");
						String newText = text.substring(0, lastBreakIndex);
						((EditText) v).setText("");
						((EditText) v).append(newText);
						return false;
					}
					//prevKeyCode++;
					enterCount++; /* text.split("\n").length; */

				} else if (keyCode == KeyEvent.KEYCODE_DEL
						&& event.getAction() == KeyEvent.ACTION_UP
						&& enterCount > 0) {
					Log.i("onKey", "back:"+enterCount);
					if (isPrevEnter) {
						//prevKeyCode--;
						enterCount--;
					}
				} else if (event.getAction() == KeyEvent.ACTION_UP) {
					Log.i("onKey", "else:"+enterCount);
					isPrevEnter = false;
					/*prevKeyCode--;
					if (prevKeyCode < 0)
						prevKeyCode = 0;*/
				}
				return false;
			}
		});

		im_fav.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isChecked(v)) {
					((ImageView) v).setImageDrawable(getResources()
							.getDrawable(R.drawable.star));
					isCheckedMap.put(R.id.fav, true);
				} else {
					((ImageView) v).setImageDrawable(getResources()
							.getDrawable(R.drawable.star_outline));
					isCheckedMap.put(R.id.fav, false);
				}
			}
		});

		bt_submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setLogEntry();
				dbAccessor.addEntry(entry);
				resetViews();
			}
		});

		return v;
	}

	@Override
	public void onPause() {
		// dbAccessor.closeDB();
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();

		/*
		 * changeTime = (Button) v.findViewById(R.id.ct);
		 * changeTime.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { showTimePickerDialog(v); }
		 * });
		 */

		/*
		 * changeDate = (Button) v.findViewById(R.id.cd);
		 * changeDate.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { showDatePickerDialog(v); }
		 * });
		 */

		dateLayout = (LinearLayout) v.findViewById(R.id.date_layout);
		dateLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePickerDialog(v);

			}
		});
		timeLayout = (LinearLayout) v.findViewById(R.id.time_layout);
		timeLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showTimePickerDialog(v);

			}
		});
	}

	private void setLogEntry() {
		entry.setSeverity(isChecked(im_low) ? "mild" : "severe");
		entry.setisNeprocinTaken(isChecked(im_neprocin) ? 1 : 0);
		entry.setisCrocinTaken(isChecked(im_crocin) ? 1 : 0);
		entry.setIsSunExposure(isChecked(im_sunexpo) ? 1 : 0);
		entry.setIsBookMarked(isChecked(im_fav) ? 1 : 0);
		entry.setOther(et_note.getText().length() == 0 ? "" : et_note.getText().toString());
	}

	public void showDatePickerDialog(View view) {
		DialogFragment newFragment = new DatePickerFragment(this);
		newFragment.show(getChildFragmentManager(), "datePicker");
	}

	public void showTimePickerDialog(View view) {
		DialogFragment newFragment = new TimePickerFragment(this);
		newFragment.show(getChildFragmentManager(), "timePicker");
	}

	private void initializeDateAndTime() {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		if (day < 10)
			dayTV.setText("0" + day);
		else
			dayTV.setText(day + "");
		if (month < 9)
			mnthTV.setText("0" + (month + 1));
		else
			mnthTV.setText((month + 1) + "");
		if ((year % 100) < 10)
			yrTV.setText("0" + (year % 100));
		else
			yrTV.setText((year % 100) + "");
		if (hour < 10)
			hrTV.setText("0" + hour);
		else
			hrTV.setText(hour + "");
		if (minute < 10)
			minTV.setText("0" + minute);
		else
			minTV.setText(minute + "");

		entry.setTime(hrTV.getText() + ":" + minTV.getText());
		entry.setDate(dayTV.getText() + "/" + mnthTV.getText() + "/"
				+ yrTV.getText());
	}

	@Override
	public void onHandleTimeChange(int hourOfDay, int minute) {
		if (hourOfDay < 10)
			hrTV.setText("0" + hourOfDay);
		else
			hrTV.setText(hourOfDay + "");
		if (minute < 10)
			minTV.setText("0" + minute);
		else
			minTV.setText(minute + "");

		entry.setTime(hrTV.getText() + ":" + minTV.getText());
	}

	@Override
	public void onHandleDateChange(int year, int monthOfYear, int dayOfMonth) {
		monthOfYear++;
		if (dayOfMonth < 10)
			dayTV.setText("0" + dayOfMonth);
		else
			dayTV.setText(dayOfMonth + "");
		if (monthOfYear < 10)
			mnthTV.setText("0" + monthOfYear);
		else
			mnthTV.setText(monthOfYear + "");
		if ((year % 100) < 10)
			yrTV.setText("0" + (year % 100));
		else
			yrTV.setText((year % 100) + "");

		entry.setDate(dayTV.getText() + "/" + mnthTV.getText() + "/"
				+ yrTV.getText());
	}

	private boolean isChecked(View v) {
		switch (v.getId()) {
		case R.id.low_intensity:
			return isCheckedMap.get(R.id.low_intensity);
		case R.id.high_intensity:
			return isCheckedMap.get(R.id.high_intensity);
		case R.id.sun_expo:
			return isCheckedMap.get(R.id.sun_expo);
		case R.id.fav:
			return isCheckedMap.get(R.id.fav);
		case R.id.crcn:
			return isCheckedMap.get(R.id.crcn);
		case R.id.nprcn:
			return isCheckedMap.get(R.id.nprcn);
		default:
			return false;
		}
	}

	private void checkAndDrawTick(View v) {
		switch (v.getId()) {
		case R.id.low_intensity:
			if (!isChecked(v)) {
				drawTick(im_high, false);
				isCheckedMap.put(R.id.high_intensity, false);
				drawTick(v, true);
				isCheckedMap.put(R.id.low_intensity, true);
			}
			break;
		case R.id.high_intensity:
			if (!isChecked(v)) {
				drawTick(im_low, false);
				isCheckedMap.put(R.id.low_intensity, false);
				drawTick(v, true);
				isCheckedMap.put(R.id.high_intensity, true);
			}
			break;
		case R.id.sun_expo:
			if (isChecked(v)) {
				drawTick(v, false);
				isCheckedMap.put(R.id.sun_expo, false);
			} else {
				drawTick(v, true);
				isCheckedMap.put(R.id.sun_expo, true);
			}
			break;
		case R.id.crcn:
			if (isChecked(v)) {
				drawTick(v, false);
				isCheckedMap.put(R.id.crcn, false);
			} else {
				drawTick(v, true);
				isCheckedMap.put(R.id.crcn, true);
			}
			break;
		case R.id.nprcn:
			if (isChecked(v)) {
				drawTick(v, false);
				isCheckedMap.put(R.id.nprcn, false);
			} else {
				drawTick(v, true);
				isCheckedMap.put(R.id.nprcn, true);
			}
			break;
		}
	}

	private void drawTick(View v, boolean set) {
		/*
		 * Drawable d = getResources().getDrawable(R.drawable.tick);
		 * d.setAlpha(127);
		 */
		Bitmap bm_tick = BitmapFactory.decodeResource(getResources(),
				R.drawable.tick);
		// Bitmap bm_tick = ((BitmapDrawable) d).getBitmap();
		Bitmap bm_view = ((BitmapDrawable) ((ImageView) v).getDrawable())
				.getBitmap();
		if (set) {
			Bitmap bmOverlay = Bitmap.createBitmap(bm_view.getWidth(),
					bm_view.getHeight(), bm_view.getConfig());
			Canvas canvas = new Canvas(bmOverlay);
			canvas.drawBitmap(bm_view, new Matrix(), null);
			canvas.drawBitmap(bm_tick,
					(bm_view.getWidth() - bm_tick.getWidth()) / 2,
					(bm_view.getHeight() - bm_tick.getHeight()) / 2, null);
			((ImageView) v).setImageBitmap(bmOverlay);
		} else {
			switch (v.getId()) {
			case R.id.low_intensity:
				((ImageView) v).setImageDrawable(getResources().getDrawable(
						R.drawable.sad));
				break;
			case R.id.high_intensity:
				((ImageView) v).setImageDrawable(getResources().getDrawable(
						R.drawable.skull));
				break;
			case R.id.sun_expo:
				((ImageView) v).setImageDrawable(getResources().getDrawable(
						R.drawable.sun_orange));
				break;
			case R.id.crcn:
				((ImageView) v).setImageDrawable(getResources().getDrawable(
						R.drawable.crocin));
				break;
			case R.id.nprcn:
				((ImageView) v).setImageDrawable(getResources().getDrawable(
						R.drawable.neprocin));
				break;
			}
		}
	}
	
	private void resetViews(){
		
		initializeDateAndTime();
		
		drawTick(im_high, false);
		isCheckedMap.put(R.id.high_intensity, false);
		drawTick(im_low, true);
		isCheckedMap.put(R.id.low_intensity, true);
		
		drawTick(im_sunexpo, false);
		isCheckedMap.put(R.id.sun_expo, false);
		
		drawTick(im_crocin, false);
		isCheckedMap.put(R.id.crcn, false);
		
		drawTick(im_neprocin, false);
		isCheckedMap.put(R.id.nprcn, false);
		
		et_note.setText(null);
		
		im_fav.setImageDrawable(getResources()
				.getDrawable(R.drawable.star_outline));
		isCheckedMap.put(R.id.fav, false);

	}
}
