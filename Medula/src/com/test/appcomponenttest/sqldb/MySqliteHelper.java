package com.test.appcomponenttest.sqldb;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class MySqliteHelper extends SQLiteOpenHelper {

	public static final String TABLE_HEADACHE_LOG = "headache_log";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DATE = "_date";
	public static final String COLUMN_TIME = "_time";
	public static final String COLUMN_SEVERITY = "_severity";
	public static final String COLUMN_CROCIN = "_crocin";
	public static final String COLUMN_NEPROCIN = "_neprocin";
	public static final String COLUMN_SUN = "_sun_exposure";
	public static final String COLUMN_BOOKMARK = "_bookmark";
	public static final String COLUMN_OTHER = "_other";

	public static final String DATABASE_NAME = "headache_log.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = "create table "+TABLE_HEADACHE_LOG
			+ "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_DATE + " text not null, "
			+ COLUMN_TIME + " text not null, "
			+ COLUMN_SEVERITY + " text not null, "
			+ COLUMN_CROCIN + " integer not null, "
			+ COLUMN_NEPROCIN + " integer not null, "
			+ COLUMN_SUN + " integer not null, "
			+ COLUMN_BOOKMARK + " text not null, "
			+ COLUMN_OTHER + " text not null);";
	
	public MySqliteHelper(Context mcontext) {
		super(mcontext, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_HEADACHE_LOG);
		onCreate(database);
	}

}
