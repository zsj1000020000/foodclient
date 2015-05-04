package cn.edu.hstc.food.client.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodPageDbOpenHelper extends SQLiteOpenHelper {

	public FoodPageDbOpenHelper(Context context) {
		super(context, "food_page.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {// 是在数据库每一次被创建的时候调用的
		db.execSQL("CREATE TABLE food_page(sid integer primary key autoincrement,page integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
