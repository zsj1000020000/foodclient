package cn.edu.hstc.food.client.service;

import cn.edu.hstc.food.client.db.StoreDbPicOpenHelper;
import cn.edu.hstc.food.client.domain.Store;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class StoreDbPicService {

	private StoreDbPicOpenHelper helper;
	private Context context;

	public StoreDbPicService(Context context) {

		this.context = context;
		helper = new StoreDbPicOpenHelper(context);
	}

	public void saveStores(Store store) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("insert into store_pic(pic) values(?)",new Object[]{store.getPic()});
		db.close();
	}

	public Store getStore() {
		return null;
		
	}

	public Long getStoreUpdateTime() {
		return null;
	}

	public StoreDbPicOpenHelper getHelper() {
		return helper;
	}

	public void setHelper(StoreDbPicOpenHelper helper) {
		this.helper = helper;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
