package cn.edu.hstc.food.client.service;

import cn.edu.hstc.food.client.db.FoodPageDbOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class FoodPageService {

	
	private Context context;
	private FoodPageDbOpenHelper helper;

	public FoodPageService(Context context) {
		
		this.context = context;
		helper = new FoodPageDbOpenHelper(context);
	}

	public void setPage(Integer sid ,int page) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("replace into food_page(sid,page) values(?,?)",new Object[]{sid,page});
	}
	
	public int getPage(Integer sid) {
		return sid;
		
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
