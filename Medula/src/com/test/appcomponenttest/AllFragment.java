package com.test.appcomponenttest;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.test.appcomponenttest.adapters.CustomLogListAdapter;
import com.test.appcomponenttest.providers.HeadacheLogProvider;
import com.test.appcomponenttest.sqldb.MySqliteHelper;

public class AllFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	EventListener mListener;
	SimpleCursorAdapter mSCAdapter;
	private final String TAG = "AllFragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setEmptyText("No Headache Logs");

		mSCAdapter = new CustomLogListAdapter(getActivity(),
				R.layout.list_item, null, new String[] {
						MySqliteHelper.COLUMN_DATE,
						MySqliteHelper.COLUMN_SEVERITY,
						MySqliteHelper.COLUMN_BOOKMARK }, new int[] {
						R.id.date, R.id.svrty, R.id.bm_check }, 0);

		getListView().setDividerHeight(20);
		getListView().setPadding(15, 5, 15, 5);

		setListAdapter(mSCAdapter);
		setListShown(false);

		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (EventListener) activity;
		} catch (Exception e) {
			Log.i(TAG, "" + e);
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		mListener.onHandleEvent(lv.getCount()-(position+1));
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri baseUri = HeadacheLogProvider.CONTENT_URI;
		String selection = null;

		return new CursorLoader(getActivity(), baseUri,
				new String[] { MySqliteHelper.COLUMN_ID,
						MySqliteHelper.COLUMN_DATE,
						MySqliteHelper.COLUMN_SEVERITY,
						MySqliteHelper.COLUMN_BOOKMARK }, selection, null, "_id DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mSCAdapter.swapCursor(data);
		if (isResumed()) {
			setListShown(true);
		} else {
			setListShownNoAnimation(true);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mSCAdapter.swapCursor(null);
	}

	public interface EventListener {
		void onHandleEvent(int id);
	}

}
