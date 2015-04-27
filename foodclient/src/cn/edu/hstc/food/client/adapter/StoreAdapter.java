package cn.edu.hstc.food.client.adapter;

import java.util.List;

import cn.edu.hstc.food.client.R;
import cn.edu.hstc.food.client.domain.Store;
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

public class StoreAdapter extends BaseAdapter{
	
	private Activity context;  
    private List<Store> stores;
    
    

	public StoreAdapter() {
		super();
		
	}
	
	

	public StoreAdapter(Activity context, List<Store> stores) {
		super();
		this.context = context;
		this.stores = stores;
	}

	public int getCount() {
		int count = stores.size();
		return count;
	}

	public Object getItem(int position) {
		Object item = stores.get(position);
		return item;
	}

	public long getItemId(int position) {
		
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = context.getLayoutInflater();  
		View itemView = inflater.inflate(R.layout.lv, null);
		Store store = stores.get(position);
		
		ImageView store_pic = (ImageView) itemView.findViewById(R.id.store_pic);
		byte[] data = null;
		
		try {
			
			data = ImageService.getImage(context.getString(R.string.domain)+ store.getPic());
			System.out.println(context.getString(R.string.domain)+ store.getPic());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		store_pic.setImageBitmap(bitmap);
		TextView store_name = (TextView) itemView.findViewById(R.id.store_name);
		store_name.setText(store.getName());
		
		TextView store_info = (TextView) itemView.findViewById(R.id.store_info);
		store_info.setText(store.getInfo());
		
		return itemView;
	}
	
	



	

}
