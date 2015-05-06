package cn.edu.hstc.food.client.service;

import cn.edu.hstc.food.client.db.FoodPageDbOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FoodPageService {

	private Context context;
	private FoodPageDbOpenHelper helper;

	public FoodPageService(Context context) {

		this.context = context;
		helper = new FoodPageDbOpenHelper(context);
	}

	public void setPage(Integer sid, int page) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("replace into food_page(sid,page) values(?,?)",
				new Object[] { sid, page });
	}

	public int getPage(Integer sid) {
		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery(
				"select page from food_page where sid=" + sid,
				null);
		int page = 0 ;
		if(cursor.moveToFirst()){
			page = cursor.getInt(cursor.getColumnIndex("page"));	
			
		}

		db.close();
		return page;

	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
