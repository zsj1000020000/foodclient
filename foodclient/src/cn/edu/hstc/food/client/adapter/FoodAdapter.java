package cn.edu.hstc.food.client.adapter;

import java.util.List;

import cn.edu.hstc.food.client.R;
import cn.edu.hstc.food.client.domain.Food;
import cn.edu.hstc.food.client.service.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodAdapter extends BaseAdapter {

	private Activity context;
	private List<Food> foods;

	public FoodAdapter() {
		super();

	}

	public FoodAdapter(Activity context, List<Food> foods) {
		super();
		this.context = context;
		this.foods = foods;
	}

	public int getCount() {
		int count = foods.size();
		return count;
	}

	public Object getItem(int position) {
		Object item = foods.get(position);
		return item;
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		View itemView = inflater.inflate(R.layout.food_lv, null);
		Food food = foods.get(position);

		ImageView food_pic = (ImageView) itemView.findViewById(R.id.food_pic);
		byte[] data = null;

		try {
			data = ImageService.getImage(context.getString(R.string.domain)
					+ food.getPic());

		} catch (Exception e) {

			e.printStackTrace();
		}

		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		food_pic.setImageBitmap(bitmap);
		TextView food_name = (TextView) itemView.findViewById(R.id.food_name);
		food_name.setText(food.getName());

		TextView food_info = (TextView) itemView.findViewById(R.id.food_info);
		food_info.setText(food.getInfo());
		TextView food_price = (TextView) itemView.findViewById(R.id.food_price);
		food_price.setText(food.getPrice() + " 元");
		

		return itemView;
	}

}
