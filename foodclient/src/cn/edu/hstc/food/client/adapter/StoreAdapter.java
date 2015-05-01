package cn.edu.hstc.food.client.adapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import cn.edu.hstc.food.client.R;
import cn.edu.hstc.food.client.domain.Store;
import cn.edu.hstc.food.client.service.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StoreAdapter extends BaseAdapter {

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
		File sdCardDir = null;
		File storeDir = null;
		String storePicName = null;
		try {

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				storePicName = store.getPic().substring(
						store.getPic().lastIndexOf("/") + 1);
				sdCardDir = Environment.getExternalStorageDirectory();
				storeDir = new File(sdCardDir, "store");
				if (!storeDir.exists()) {
					storeDir.mkdirs();
				}
				File pic = new File(storeDir, storePicName);
				if (pic.exists()) {
					InputStream in = new FileInputStream(pic);
					ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
					byte[] b = new byte[1000];
					int len;
					while ((len = in.read(b)) != -1) {
						out.write(b, 0, len);
					}
					in.close();
					out.close();
					data = out.toByteArray();
					System.out.println("找到了。");
				} else {
					data = ImageService.getImage(context
							.getString(R.string.domain) + store.getPic());
					
					OutputStream out = new FileOutputStream(new File(storeDir,
							storePicName));
					out.write(data);
					out.close();
					System.out.println("没有找到，到网上找。");
				}

			} else {
				data = ImageService.getImage(context.getString(R.string.domain)
						+ store.getPic());
				
			}

		} catch (Exception e) {
			
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
