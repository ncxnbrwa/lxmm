/**
 * 
 */ 
package com.nuocf.yshuobang.adapter;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eeb.pickpiclib.Bimp;
import com.eeb.pickpiclib.FileUtils;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.appBase.ScreenInfo;

/** 
 * 类说明 :
 * @author  luquan
 * 创建时间：2014-11-3 下午5:12:16 
 */

public class UploadImageAdapter extends CommonAdapter<Bitmap>{

	/**
	 * @param context
	 * @param datas
	 */
	private Context mContext;
	public UploadImageAdapter(Context context, List<Bitmap> datas) {
		super(context, datas);
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_published_grida, null);
		}
		ImageView imageView = ViewHolder.get(convertView,R.id.item_grida_image,(ScreenInfo.screenWidth-30)/5-10);
		if (position == Bimp.bmp.size()) {
			imageView.setImageBitmap(BitmapFactory.decodeResource(
					mContext.getResources(), R.mipmap.upload));
			if (position == 3) {
				imageView.setVisibility(View.GONE);
			}
		} else {
			imageView.setImageBitmap(Bimp.bmp.get(position));
		}
		return convertView;
	}

	public void update() {
		loading();
	}
	public int getCount() {
		return (Bimp.bmp.size() + 1);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				UploadImageAdapter.this.notifyDataSetChanged();
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void loading() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					if (Bimp.max == Bimp.drr.size()) {
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
						break;
					} else {
						try {
							String path = Bimp.drr.get(Bimp.max);
							System.out.println(path);
							//压缩图片
							Bitmap bm = Bimp.revitionImageSize(path);
							Bimp.bmp.add(bm);
							String newStr = path.substring(
									path.lastIndexOf("/") + 1,
									path.lastIndexOf("."));
							FileUtils.saveBitmap(bm, "" + newStr,mContext);
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						} catch (IOException e) {

							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}
}
 