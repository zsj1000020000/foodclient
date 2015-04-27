package cn.edu.hstc.food.client.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.hstc.food.client.domain.Category;

public class CategoryService {

	public static List<Category> getCategories(String jsonArray)
			throws JSONException {

		if (jsonArray != null && !("".equals(jsonArray.trim()))) {
			JSONArray arr = new JSONArray(jsonArray);
			List<Category> categories = new ArrayList<Category>();
			for (int i = 0; i < arr.length(); i++) {
				JSONObject c1 = (JSONObject) arr.get(i);
				Category category = new Category();
				Integer id = c1.getInt("id");
				String name = c1.getString("name");
				String info = c1.getString("info");
				category.setId(id);
				category.setName(name);
				category.setInfo(info);
				categories.add(category);
			}

			return categories;
		} else {
			throw new JSONException("获取分类数据失败.");
		}

	}

}
