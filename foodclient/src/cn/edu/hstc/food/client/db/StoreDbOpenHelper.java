package cn.edu.hstc.food.client.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StoreDbOpenHelper extends SQLiteOpenHelper {

	public StoreDbOpenHelper(Context context) {
		super(context, "store.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {// 是在数据库每一次被创建的时候调用的
		db.execSQL("CREATE TABLE store(id integer primary key autoincrement,name varchar(20), "
				+ "info varchar(50) ,pic varchar(50),phone_number varchar(12),updateTime long ,isDelete boolean)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
