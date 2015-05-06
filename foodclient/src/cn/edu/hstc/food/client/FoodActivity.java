package cn.edu.hstc.food.client;

import java.util.Date;
import java.util.List;
import java.util.Map;
import cn.edu.hstc.food.client.adapter.FoodAdapter;
import cn.edu.hstc.food.client.domain.Food;
import cn.edu.hstc.food.client.service.FoodDbService;
import cn.edu.hstc.food.client.service.FoodPageService;
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

public class FoodActivity extends Activity implements OnLoadListener,
		OnRefreshListener {

	private TextView store_name;
	private TextView store_info;
	private TextView store_phone_number;
	private AutoListView food_lv;
	private List<Food> foods;
	private FoodAdapter adapter;
	private TimeService timeService;
	private FoodPageService pageService;
	private FoodDbService foodDbService;
	private int i;
	private int sid;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.food);
		pageService = new FoodPageService(this);
		timeService = new TimeService(this);
		foodDbService = new FoodDbService(this);
		store_name = (TextView) this.findViewById(R.id.food_store_name);
		store_info = (TextView) this.findViewById(R.id.food_store_info);
		store_phone_number = (TextView) this
				.findViewById(R.id.food_phone_number);
		food_lv = (AutoListView) this.findViewById(R.id.food_lv);
		Intent intent = getIntent();
		sid = intent.getIntExtra("sid", 0);
		String name = intent.getStringExtra("name");
		String info = intent.getStringExtra("info");
		String phone_number = intent.getStringExtra("phone_number");
		store_name.setText("店名：" + name);
		store_info.setText("简介:\n" + info);
		store_phone_number.setText("phone:" + phone_number);
		i = pageService.getPage(sid);
		Long updateTime = (Long) timeService.getFoodUpdateTime(sid).get(
				"upate_time");
		url = getString(R.string.server_url);
		if (updateTime == null) {
			
			timeService.setFoodUpateTime(null, sid, new Date());
			if (i == 0) {
				i = 1;
			}
			try {
				
				String path = url + "foods?page=" + i + "&sid=" + sid;
				String jsonData = URLTool.getJsonData(path, 6000, "GET");
				foods = FoodService.getFoods(jsonData);
				foodDbService.saveOrUpdateFoods(foods, sid);
				
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {
			foods = foodDbService.getFoods(sid);
			
		}
		adapter = new FoodAdapter(this, foods);
		food_lv.setAdapter(adapter);
		food_lv.setOnLoadListener(this);
		food_lv.setOnRefreshListener(this);
	}

	public void onRefresh() {
		loadData(AutoListView.REFRESH, 1);
	}

	public void onLoad() {
		i++;
		pageService.setPage(sid, i);
		loadData(AutoListView.LOAD, 0);

	}

	private void loadData(final int what, final int flag) {
		// 这里模拟从服务器获取数据
		new Thread(new Runnable() {

			public void run() {
				Message msg = handler.obtainMessage();
				msg.what = what;
				if (flag == 0) {
					int i = pageService.getPage(sid);
					if (i > 0) {
						try {
							String path = url + "?page=" + i + "&sid=" + sid;
							String jsonData = URLTool.getJsonData(path, 6000,
									"GET");
							msg = handler.obtainMessage();
							msg.what = what;
							List<Food> fs = FoodService.getFoods(jsonData);
							System.out.println(fs);
							msg.obj = fs;
							
						} catch (Exception e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(), "获取数据失败",
									Toast.LENGTH_SHORT).show();
						}
					} else {

						msg.obj = null;
					}

				}

				if (flag == 1) {
					Map<String, Object> params = timeService.getFoodUpdateTime(sid);
					Long updateTime = (Long) params.get("upate_time");
					Integer id = (Integer) params.get("id");
					timeService.setFoodUpateTime(id, sid, new Date());
					String path = url + "/foods_date?date=" + updateTime + "&sid=" + sid;
					System.out.println(path);
					try {
						String jsonData = URLTool.getJsonData(path, 6000, "GET");
							msg.obj = FoodService.getFoods(jsonData);
							
						} catch (Exception e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(), "获取数据失败",
									Toast.LENGTH_SHORT).show();
						}
					
					
				}
				
				handler.sendMessage(msg);
			}
		}).start();
	}
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			List<Food> fs = (List<Food>) msg.obj;
			// if (msg.obj != null) {
			// storeDbService.saveOrUpdateStores(ss);
			// }
			//
			switch (msg.what) {
			case AutoListView.REFRESH:

				if (fs.size() == 0) {
					food_lv.onRefreshComplete();
				} else {
					foods.clear();
					food_lv.onRefreshComplete();
					// ss = storeDbService.getStores();
					foods.addAll(fs);

				}

				break;
			case AutoListView.LOAD:
				if (msg.obj != null) {
					food_lv.onLoadComplete();
					if (fs.size() == 5) {
						foodDbService.saveOrUpdateFoods(fs, sid);
						fs = foodDbService.getFoods(sid);
						foods.clear();
						foods.addAll(fs);
						food_lv.onLoadComplete();
					} else {
						if (fs.size() == 0) {

						} else {
							foodDbService.saveOrUpdateFoods(fs, sid);
							foods.clear();
							fs = foodDbService.getFoods(sid);
							
							foods.addAll(fs);
						}
						food_lv.onLoadComplete();
						food_lv.setLoadEnable(true);
						pageService.setPage(sid, -10);
						Toast.makeText(getApplicationContext(), "没有更多数据",
								Toast.LENGTH_SHORT).show();
					}
				} else {
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
