package cn.edu.hstc.food.client.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
				"replace into time(id,sid,upate_time) values(?,?,?)",
				new Object[] { 1, 0,updateTime.getTime() });
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
	
	public void setFoodUpateTime(Integer id,Integer sid ,Date updateTime) {
		SQLiteDatabase db = helper.getWritableDatabase();
		if (id != null) {
			db.execSQL(
					"replace into time(id,sid,upate_time) values(?,?,?)",
					new Object[] { id, sid,updateTime.getTime() });
		}else{
			db.execSQL(
					"replace into time(sid,upate_time) values(?,?)",
					new Object[] {sid,updateTime.getTime() });
		}
		
		db.close();
	}
	
	public Map<String, Object> getFoodUpdateTime(Integer sid) {
		SQLiteDatabase db = helper.getReadableDatabase();
		 Map<String, Object> params = new HashMap<String, Object>();
		Cursor cursor = db.rawQuery("select id,upate_time from time where sid=" + sid, null);
		Long updateTime = null;
		Integer id = null;
		if(cursor.moveToFirst()){
			updateTime = cursor.getLong(cursor.getColumnIndex("upate_time"));	
			id = cursor.getInt(cursor.getColumnIndex("id"));
		}
		cursor.close();
		params.put("id", id);
		System.out.println(updateTime);
		params.put("upate_time", updateTime);
		return params;
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
