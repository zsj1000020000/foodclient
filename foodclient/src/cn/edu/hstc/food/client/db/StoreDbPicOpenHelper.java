package cn.edu.hstc.food.client.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StoreDbPicOpenHelper extends SQLiteOpenHelper {

	public StoreDbPicOpenHelper(Context context) {
		super(context, "store_pic.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {// 是在数据库每一次被创建的时候调用的
		db.execSQL("CREATE TABLE store_pic(id integer primary key autoincrement,path varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
