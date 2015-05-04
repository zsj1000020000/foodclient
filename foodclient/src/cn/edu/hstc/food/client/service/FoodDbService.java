package cn.edu.hstc.food.client.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import cn.edu.hstc.food.client.db.FoodDbOpenHelper;
import cn.edu.hstc.food.client.domain.Food;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FoodDbService {

	private FoodDbOpenHelper helper;
	private Context context;

	public FoodDbService(Context context) {

		this.context = context;
		helper = new FoodDbOpenHelper(context);
	}

	public void saveOrUpdateFoods(List<Food> foods,Integer sid) {
		SQLiteDatabase db = helper.getWritableDatabase();
		for (Food food : foods) {
			db.execSQL(
					"replace into food(id,name,info,pic,price,updateTime,isDelete,sid) values(?,?,?,?,?,?,?,?)",
					new Object[] { food.getId(), food.getName(),
							food.getInfo(), food.getPic(),
							food.getPrice(),
							food.getUpdateTime().getTime(),
							food.getIsDelete(),sid });
		}

		db.close();
	}

	public List<Food> getFoods(Integer sid) {
		List<Food> foods = new ArrayList<Food>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from food order by id desc",
				null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String info = cursor.getString(cursor.getColumnIndex("info"));
			String pic = cursor.getString(cursor.getColumnIndex("pic"));
			String price = cursor.getString(cursor
					.getColumnIndex("price"));
			long updateTime = cursor.getLong(cursor
					.getColumnIndex("updateTime"));
			boolean isDelete = Boolean.parseBoolean(cursor.getString(cursor
					.getColumnIndex("isDelete")));
			
			Food food = new Food(id, name, info, pic, price,
					new Date(updateTime), isDelete,sid);
			foods.add(food);
		}

		return foods;
	}

	public Long getStoreUpdateTime() {
		return null;
	}

	
	public FoodDbOpenHelper getHelper() {
		return helper;
	}

	public void setHelper(FoodDbOpenHelper helper) {
		this.helper = helper;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
