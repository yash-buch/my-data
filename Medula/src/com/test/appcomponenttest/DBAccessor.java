package com.test.appcomponenttest;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import com.test.appcomponenttest.providers.HeadacheLogProvider;
import com.test.appcomponenttest.sqldb.MySqliteHelper;


public class DBAccessor {

	private Context mContext;
	//private MySqliteHelper dbHelper;

	public DBAccessor(Context ctx) {
		mContext = ctx;//dbHelper = new MySqliteHelper(mContext);
	}

	/*public void openDB() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}*/
	
	/*public void closeDB(){
		dbHelper.close();
	}*/

	public Uri addEntry(LogEntry entry) { // Insert a record in the
												// database table
		
		ContentValues cv = new ContentValues();
		cv.put(MySqliteHelper.COLUMN_SEVERITY, entry.getSeverity());
		cv.put(MySqliteHelper.COLUMN_SUN, entry.getIsSunExposure());
		cv.put(MySqliteHelper.COLUMN_CROCIN, entry.getisCrocinTaken());
		cv.put(MySqliteHelper.COLUMN_NEPROCIN, entry.getisNeprocinTaken());
		cv.put(MySqliteHelper.COLUMN_OTHER, entry.getOther());
		cv.put(MySqliteHelper.COLUMN_DATE, entry.getDate());
		cv.put(MySqliteHelper.COLUMN_TIME, entry.getTime());
		cv.put(MySqliteHelper.COLUMN_BOOKMARK, entry.getIsBookMarked());
		
		Uri uri = mContext.getContentResolver().insert(HeadacheLogProvider.CONTENT_URI,
				cv);
		return uri;
	}

	/*public void removeEntry(LogEntry student) {
		long id = student.getID();
		database.delete(MySqliteHelper.TABLE_HEADACHE_LOG,
				MySqliteHelper.COLUMN_ID + " = " + id, null);
	}

	public Cursor getHeadacheLogs(String idArgument) {		// add a check for idArgument constraints
		Cursor cursor = database.query(MySqliteHelper.TABLE_HEADACHE_LOG, null,
				"WHERE " + MySqliteHelper.COLUMN_ID + " = " + idArgument, null,
				null, null, null);
		return cursor;
	}
	
	public Cursor getAllHeadacheLogs() {
		Cursor cursor = database.query(MySqliteHelper.TABLE_HEADACHE_LOG, null,
				null, null, null, null, null, null);
		return cursor;
	}*/

}
