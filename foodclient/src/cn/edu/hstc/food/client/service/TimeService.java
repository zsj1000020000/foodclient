package cn.edu.hstc.food.client.service;

import java.util.Date;

import cn.edu.hstc.food.client.db.TimeDbOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TimeService {

	private TimeDbOpenHelper helper;
	private Context context;

	public TimeService(Context context) {

		this.context = context;
		helper = new TimeDbOpenHelper(context);
	}

	public void setStoreUpateTime(Date updateTime) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(
				"replace into time(id,sid, fid, upate_time) values(?,?,?,?)",
				new Object[] { 1, 0, 0, updateTime.getTime() });
		db.close();
	}

	public Long getStoreUpdateTime() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select upate_time from time where id=1", null);
		Long updateTime = null;
		if(cursor.moveToFirst()){
			updateTime = cursor.getLong(cursor.getColumnIndex("upate_time"));				
		}
		cursor.close();
		return updateTime;
	}

	public TimeDbOpenHelper getHelper() {
		return helper;
	}

	public void setHelper(TimeDbOpenHelper helper) {
		this.helper = helper;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
