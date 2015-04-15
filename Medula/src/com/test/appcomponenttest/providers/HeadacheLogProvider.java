package com.test.appcomponenttest.providers;

import com.test.appcomponenttest.sqldb.MySqliteHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class HeadacheLogProvider extends ContentProvider {

	private MySqliteHelper dbHelper;
	private static final int CASE_GEN = 1;
	private static final int CASE_ID = 2;

	private static final String AUTHORITY = "com.test.appcomponenttest.providers";
	private static final String TABLE = MySqliteHelper.TABLE_HEADACHE_LOG;

	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, TABLE, CASE_GEN);
		sURIMatcher.addURI(AUTHORITY, TABLE + "/#", CASE_ID);
	}

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE);

	@Override
	public boolean onCreate() {
		dbHelper = new MySqliteHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(TABLE);
		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case CASE_GEN:
			break;
		case CASE_ID:
			queryBuilder.appendWhere(MySqliteHelper.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		// Implement later if necessary
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long id = db.insert(TABLE, null, values);
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(TABLE + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// Implement later if necessary
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// Implement later if necessary
		return 0;
	}

}
