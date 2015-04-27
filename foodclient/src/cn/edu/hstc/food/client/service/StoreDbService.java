package cn.edu.hstc.food.client.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.hstc.food.client.db.StoreDbOpenHelper;
import cn.edu.hstc.food.client.domain.Store;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StoreDbService {

	private StoreDbOpenHelper helper;
	private Context context;

	public StoreDbService(Context context) {

		this.context = context;
		helper = new StoreDbOpenHelper(context);
	}

	public void saveOrUpdateStores(List<Store> stores) {
		SQLiteDatabase db = helper.getWritableDatabase();
		for (Store store : stores) {
			db.execSQL(
					"replace into store(id,name, info,pic,phone_number, updateTime,isDelete) values(?,?,?,?,?,?,?)",
					new Object[] { store.getId(), store.getName(),
							store.getInfo(), store.getPic(),
							store.getPhone_number(),
							store.getUpdateTime().getTime(),
							store.getIsDelete() });
		}

		db.close();
	}

	public List<Store> getStores() {
		List<Store> stores = new ArrayList<Store>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from Store order by id desc",
				null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String info = cursor.getString(cursor.getColumnIndex("info"));
			String pic = cursor.getString(cursor.getColumnIndex("pic"));
			String phone_number = cursor.getString(cursor
					.getColumnIndex("phone_number"));
			long updateTime = cursor.getLong(cursor
					.getColumnIndex("updateTime"));
			boolean isDelete = Boolean.parseBoolean(cursor.getString(cursor
					.getColumnIndex("isDelete")));
			Store store = new Store(id, name, info, pic, phone_number,
					new Date(updateTime), isDelete);
			stores.add(store);
		}

		return stores;
	}

	public Long getStoreUpdateTime() {
		return null;
	}

	public StoreDbOpenHelper getHelper() {
		return helper;
	}

	public void setHelper(StoreDbOpenHelper helper) {
		this.helper = helper;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
