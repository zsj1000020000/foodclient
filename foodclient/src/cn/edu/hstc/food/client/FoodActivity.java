package cn.edu.hstc.food.client;

import java.util.List;

import org.json.JSONException;

import cn.edu.hstc.food.client.adapter.FoodAdapter;
import cn.edu.hstc.food.client.domain.Food;
import cn.edu.hstc.food.client.service.FoodService;
import cn.edu.hstc.food.client.service.TimeService;
import cn.edu.hstc.utils.URLTool;
import cn.edu.hstc.widget.AutoListView;
import cn.edu.hstc.widget.AutoListView.OnLoadListener;
import cn.edu.hstc.widget.AutoListView.OnRefreshListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

public class FoodActivity extends Activity implements OnLoadListener , OnRefreshListener {

	private TextView store_name;
	private TextView store_info;
	private TextView store_phone_number;
	private AutoListView food_lv;
	private List<Food> foods;
	private FoodAdapter adapter;
	private TimeService timeService;
	private int i;
	private int sid;
	private String url ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food);
		store_name = (TextView) this.findViewById(R.id.food_store_name);
		store_info = (TextView) this.findViewById(R.id.food_store_info);
		store_phone_number = (TextView) this.findViewById(R.id.food_phone_number);
		food_lv = (AutoListView) this.findViewById(R.id.food_lv);
		timeService = new TimeService(this);
		i = 1;
		Intent intent = getIntent();
		sid = intent.getIntExtra("sid", 0);
		String name = intent.getStringExtra("name");
		String info = intent.getStringExtra("info");
		String phone_number = intent.getStringExtra("phone_number");		
		store_name.setText("店名：" + name);
		store_info.setText("简介:\n" + info);
		store_phone_number.setText("phone:" + phone_number);
		Long updateTime = timeService.getFoodUpdateTime(sid);
		if (updateTime == null) {
			
		}
		try {
			url = getString(R.string.server_url) + "foods";
//			String url = "http://192.168.1.104:8080/food-server-0.0.1/mobile/foods?page=1&sid=1";
			String path = url + "?page=" + i + "&sid=" + sid;
			System.out.println(path);
			
			String jsonData = URLTool.getJsonData(path, 6000, "GET");
			foods = FoodService.getFoods(jsonData);
			adapter = new FoodAdapter(this, foods);
			food_lv.setAdapter(adapter);
			food_lv.setOnLoadListener(this);
			food_lv.setOnRefreshListener(this);
		} catch (JSONException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		
		
	}
	public void onRefresh() {
		
		
	}
	public void onLoad() {
		i++;
		loadData(AutoListView.LOAD, 0);
		
	}
	
	private void loadData(final int what,final int flag) {
		// 这里模拟从服务器获取数据
		new Thread(new Runnable() {
			
			public void run() {
				if (flag == 0) {
					try {
						String path = url + "?page=" + i + "&sid=" + sid;
						String jsonData = URLTool.getJsonData(path, 6000, "GET");
						Message msg = handler.obtainMessage();
						msg.what = what;
						List<Food> fs = FoodService.getFoods(jsonData);
						System.out.println(fs);
 						msg.obj = fs;
						handler.sendMessage(msg);
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
				
				if (flag == 1) {
					
				}
				
			}
		}).start();
	}

	
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		
		public void handleMessage(Message msg) {
			List<Food> fs = (List<Food>) msg.obj;
//			if (msg.obj != null) {
//				storeDbService.saveOrUpdateStores(ss);
//			}
//			
			switch (msg.what) {
			case AutoListView.REFRESH:
				
				
				if (fs.size() == 0) {
					food_lv.onRefreshComplete();
				}else{
					foods.clear();
					food_lv.onRefreshComplete();
//					ss = storeDbService.getStores();
					foods.addAll(fs);
					
				}	
				
				break;
			case AutoListView.LOAD:
				if (msg.obj != null) {
					food_lv.onLoadComplete();
					if (fs.size() == 5) {					
						foods.addAll(fs);
						food_lv.onLoadComplete();
					}else{
						if (fs.size() == 0) {
							
						}else{
							
							foods.addAll(fs);
						}
						food_lv.onLoadComplete();
						food_lv.setLoadEnable(true);
						
						Toast.makeText(getApplicationContext(), "没有更多数据",
								Toast.LENGTH_SHORT).show();
					}
				}else{
					food_lv.onLoadComplete();
					food_lv.setLoadEnable(true);
				}
				
				
				break;
			}
			food_lv.setResultSize(foods.size());
			adapter.notifyDataSetChanged();
		};
	};
}
