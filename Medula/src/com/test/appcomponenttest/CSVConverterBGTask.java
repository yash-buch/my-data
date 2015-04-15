package com.test.appcomponenttest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;
import com.test.appcomponenttest.providers.HeadacheLogProvider;
import com.test.appcomponenttest.sqldb.MySqliteHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class CSVConverterBGTask extends AsyncTask<String, Void, Boolean> {

	private final String TAG = "CSVConverterBGTask";
	private Context mContext;
	private final ProgressDialog dialog;

	public CSVConverterBGTask(Context ctx) {
		mContext = ctx;
		dialog = new ProgressDialog(mContext);
	}

	@Override
	protected void onPreExecute() {
		this.dialog.setMessage("Exporting database...");
		this.dialog.show();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		File dbFile = mContext.getDatabasePath(MySqliteHelper.DATABASE_NAME);
		Log.i(TAG, "File= " + dbFile);

		File exportDir = new File(Environment.getExternalStorageDirectory(), "");
		if (!exportDir.exists()) {
			exportDir.mkdirs();
		}
		File exportCSVFile = new File(exportDir, "Headache Logs.csv");
		try {
			if (exportCSVFile.createNewFile()) {
				Log.i(TAG, "FilePath= " + exportCSVFile.getAbsolutePath());
			} else {
				Log.i(TAG, "File already exists.");
			}
			CSVWriter csvWrite = new CSVWriter(new FileWriter(exportCSVFile),
					',');
			Cursor cursor = mContext
					.getApplicationContext()
					.getContentResolver()
					.query(HeadacheLogProvider.CONTENT_URI, null, null, null,
							null);
			String[] heading = { "ID", "DATE", "TIME", "SEVERITY", "CROCIN",
					"NEPROCIN", "SUN EXPOSURE", "BOOKMARK", "OTHER" };
			csvWrite.writeNext(heading/* cursor.getColumnNames() */);
			while (cursor.moveToNext())

			{
				String arrStr[] = { cursor.getString(0), cursor.getString(1),
						cursor.getString(2), cursor.getString(3),
						cursor.getInt(4) == 0 ? "NA" : "YES", cursor.getInt(5) == 0 ? "NA" : "YES",
						cursor.getInt(6) == 0 ? "NA" : "YES", cursor.getString(7).equals("0") ? "NO" : "YES",
						cursor.getString(8).equals("") ? "No note" : cursor.getString(8) };
				csvWrite.writeNext(arrStr);
			}
			csvWrite.close();
			cursor.close();
			return true;
		} catch (SQLException e) {
			Log.i(TAG, "SQLException");
			return false;
		} catch (IOException e) {
			Log.i(TAG, "IOException");
			Log.i(TAG, "exception=" + e);
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean success) {
		if (this.dialog.isShowing()){
			this.dialog.dismiss();
		}
		if (success){
			Toast.makeText(mContext, "Export succeed", Toast.LENGTH_SHORT)
					.show();
		}else{
			Toast.makeText(mContext, "Export failed", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
