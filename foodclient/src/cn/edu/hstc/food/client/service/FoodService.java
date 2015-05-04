package cn.edu.hstc.food.client.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.hstc.food.client.domain.Food;


public class FoodService {

	public static List<Food> getFoods(String jsonArray) throws JSONException {

		if (jsonArray != null && !("".equals(jsonArray.trim()))) {
			JSONArray arr = new JSONArray(jsonArray);
			List<Food> foods = new ArrayList<Food>();
			for (int i = 0; i < arr.length(); i++) {
				JSONObject s1 = (JSONObject) arr.get(i);
				Food food = new Food();

				Integer id = s1.getInt("id");
				String name = s1.getString("name");
				String info = s1.getString("info");
				String pic = s1.getString("pic");
				String price = s1.getString("price");
				Long updateTime = s1.getLong("updateTime");
				Boolean isDelete = s1.getBoolean("isDelete");

				food.setId(id);
				food.setName(name);
				food.setInfo(info);
				food.setPic(pic);
				food.setPrice(price);
				food.setIsDelete(isDelete);
				food.setUpdateTime(new Date(updateTime));
				foods.add(food);
			}
			return foods;
		} else {
			throw new JSONException("获取商店数据失.");
		}

	}
}
