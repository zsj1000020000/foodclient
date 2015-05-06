package cn.edu.hstc.food.client;

import java.util.Date;
import java.util.List;

import cn.edu.hstc.food.client.adapter.StoreAdapter;
import cn.edu.hstc.food.client.domain.Store;
import cn.edu.hstc.food.client.service.StoreDbService;
import cn.edu.hstc.food.client.service.StorePageService;
import cn.edu.hstc.food.client.service.StoreService;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class StoreActivity extends Activity implements OnRefreshListener,
		OnLoadListener {
	/** Called when the activity is first created. */

	private AutoListView stores_lv;
	private List<Store> stores;
	private StoreAdapter adapter;
	private String urlPath ;
	private int i ;
	private TimeService timeService;
	private StoreDbService storeDbService;
	private StorePageService pageService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		pageService = new StorePageService(this);
		i = pageService.getPage();
		stores_lv = (AutoListView) this.findViewById(R.id.stores_lv);
		timeService = new TimeService(this);
		storeDbService = new StoreDbService(this);
		
		urlPath = getString(R.string.server_url).trim();
		
		Long update = timeService.getStoreUpdateTime();
		
		
		if (update != null) {			
			stores = storeDbService.getStores();
		}else{
			
			timeService.setStoreUpateTime(new Date());
			if (i == 0) {
				i = 1;
			}
			try {
				String url = urlPath + "stores?page=" + i;
				String jsonData = URLTool.getJsonData(url, 6000, "GET");
				stores = StoreService.getStores(jsonData);
				storeDbService.saveOrUpdateStores(stores);
				

			} catch (Exception e) {

				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "获取数据失败",
						Toast.LENGTH_SHORT).show();

			}
		}
		adapter = new StoreAdapter(this, stores);
		stores_lv.setAdapter(adapter);
		stores_lv.setOnItemClickListener(new StoreOnItemClickListener());
		stores_lv.setOnLoadListener(this);
		stores_lv.setOnRefreshListener(this);		
		

	}

	public void onLoad() {
		
		i++;
		pageService.setPage(i);
		loadData(AutoListView.LOAD, 0);
		System.out.println("加载数据");
	
	}

	public void onRefresh() {
		
		loadData(AutoListView.REFRESH, 1);
	}
	
	private void loadData(final int what,final int flag) {
		// 这里模拟从服务器获取数据
		new Thread(new Runnable() {

			public void run() {
				
				
				Message msg = handler.obtainMessage();
				msg.what = what;
				
				if (flag==0) {
					System.out.println("加载数据");
					i = pageService.getPage();
					if (i > 0) {
						String url = urlPath + "stores?page=" + i;
						try {
						String jsonData = URLTool.getJsonData(url, 6000, "GET");
							msg.obj = StoreService.getStores(jsonData);
						} catch (Exception e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(), "获取数据失败",
									Toast.LENGTH_SHORT).show();
						}
					}else{
						msg.obj = null;
					}
					
					
				}
				if (flag==1) {
					Long updateTime = timeService.getStoreUpdateTime();
					String url = urlPath + "stores_date?date=" + updateTime;
					timeService.setStoreUpateTime(new Date());
					try {
						String jsonData = URLTool.getJsonData(url, 6000, "GET");
							msg.obj = StoreService.getStores(jsonData);
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
			List<Store> ss = (List<Store>) msg.obj;
			if (msg.obj != null) {
				storeDbService.saveOrUpdateStores(ss);
			}
			
			switch (msg.what) {
			case AutoListView.REFRESH:
				
				
				if (ss.size() == 0) {
					stores_lv.onRefreshComplete();
				}else{
					stores.clear();
					stores_lv.onRefreshComplete();
					ss = storeDbService.getStores();
					stores.addAll(ss);
					
				}	
				
				break;
			case AutoListView.LOAD:
				if (msg.obj != null) {
					stores_lv.onLoadComplete();
					if (ss.size() == 5) {
						storeDbService.saveOrUpdateStores(ss);	
						stores.clear();
						ss = storeDbService.getStores();
						stores.addAll(ss);
					}else{
						if (ss.size() == 0) {
							
						}else{
							System.out.println("加载数据");
							storeDbService.saveOrUpdateStores(ss);
							stores.clear();
							ss = storeDbService.getStores();
							stores.addAll(ss);
						}
						stores_lv.setLoadEnable(true);
						pageService.setPage(-10);
						Toast.makeText(getApplicationContext(), "没有更多数据",
								Toast.LENGTH_SHORT).show();
					}
				}else{
					stores_lv.onLoadComplete();
					stores_lv.setLoadEnable(true);
				}
				
				
				break;
			}
			stores_lv.setResultSize(stores.size());
			adapter.notifyDataSetChanged();
		};
	};
	
	 class StoreOnItemClickListener implements OnItemClickListener {
		
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			 System.out.println(position);
			Store store = stores.get(position -1);
			Integer sid = store.getId();
			String name = store.getName();
			String info = store.getInfo();
			String phone_number = store.getPhone_number();
			
			Intent intent = new Intent(getApplicationContext(), FoodActivity.class);
			
			intent.putExtra("sid", sid);
			intent.putExtra("name", name);
			intent.putExtra("info", info);
			intent.putExtra("phone_number", phone_number);
			System.out.println("sid:" + sid);
			System.out.println("name:" + name);
			System.out.println("info:" + info);
			System.out.println("phone_number:" + phone_number);
			startActivity(intent);
			
		}
		
	}
}