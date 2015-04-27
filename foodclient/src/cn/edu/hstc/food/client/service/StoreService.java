package cn.edu.hstc.food.client.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.hstc.food.client.domain.Store;

public class StoreService {
	

	public static List<Store> getStores(String jsonArray) throws JSONException, ParseException {

		if (jsonArray != null && !("".equals(jsonArray.trim()))) {
			JSONArray arr = new JSONArray(jsonArray);
			List<Store> stores = new ArrayList<Store>();
			for (int i = 0; i < arr.length(); i++) {
				JSONObject s1 = (JSONObject) arr.get(i);
				Store store = new Store();

				Integer id = s1.getInt("id");
				String name = s1.getString("name");
				String info = s1.getString("info");
				String pic = s1.getString("pic");				
				String phone_number = s1.getString("phone_number");
				Long updateTime = s1.getLong("updateTime");
				Boolean isDelete = s1.getBoolean("isDelete");
				store.setId(id);
				store.setName(name);
				store.setInfo(info);
				store.setPic(pic);
				store.setPhone_number(phone_number);
				store.setIsDelete(isDelete);
				store.setUpdateTime(new Date(updateTime));
				stores.add(store);
			}
			
			return stores;
		} else {
			throw new JSONException("获取商店数据失.");
		}

	}
}
