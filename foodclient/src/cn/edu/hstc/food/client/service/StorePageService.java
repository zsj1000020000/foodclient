package cn.edu.hstc.food.client.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class StorePageService {

	
	private Context context;
	

	public StorePageService(Context context) {
		
		this.context = context;
	}

	public void setPage(int page) {
		SharedPreferences preferences = context.getSharedPreferences("page", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt("page", page);
		editor.commit();
	}
	
	public int getPage() {
		SharedPreferences preferences = context.getSharedPreferences("page", Context.MODE_PRIVATE);
		int page = preferences.getInt("page", 0);
		return page;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
