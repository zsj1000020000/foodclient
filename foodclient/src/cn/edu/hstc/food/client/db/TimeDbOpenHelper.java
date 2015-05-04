package cn.edu.hstc.food.client.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TimeDbOpenHelper extends SQLiteOpenHelper {

	public TimeDbOpenHelper(Context context) {
		super(context, "time.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {//是在数据库每一次被创建的时候调用的
		db.execSQL("CREATE TABLE time(id integer primary key autoincrement,sid integer, upate_time Long)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
