/**
 * 
 */
package com.nuocf.yshuobang.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author luquan
 *
 * 2014-6-19 上午11:47:06
 */
public class ImageUtil {

	/**
	 * 从view 得到图片
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        return bitmap;
	}
	
	/**
	 * 保存图片到本地
	 * @param imageName 要保存的图片名称
	 * @param bmp 要保存的图片
	 * @return
	 */
	public static boolean saveImage(String imageName, Bitmap bmp){
		if(bmp==null)return false;
		String filePath = PathUtil.get().getSaveImagePath() + imageName;
		File f = new File(filePath);
		if (!f.isFile()) {
			String dir = filePath.substring(0, filePath.lastIndexOf("/"));
			File dirFile = new File(dir);
			if (!dirFile.exists()) {
				if (!dirFile.mkdirs()) {
					return false;
				}
			}
			FileOutputStream fOut = null;
			boolean isSuccesse = false;
			try {
				f.createNewFile();		
				fOut = new FileOutputStream(f);
				bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
				isSuccesse = true;
			} catch (IOException e1) {
				e1.printStackTrace();
			}finally {
				if (fOut != null) {
					try {
						fOut.flush();
						fOut.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}return isSuccesse;
		}
		return false;
	}
	
	/**
	 * 获取本地图片
	 * @param path 本地图片路径
	 * @return 本地图片
	 */
	public static Bitmap getBitmap(String path) {
		Bitmap bit = null;
		bit = BitmapFactory.decodeFile(path);
		return bit;
	}
	
	
	/**
	 * 将bitmap画成圆形
	 * @param bitmap
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {

		if (bitmap == null)
			return null;
		// 处理因图片宽高不等绘制不成圆角
		int wh = 0;
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		if (w >= h) {
			wh = h;
		} else {
			wh = w;
		}
		// Bitmap Config.ARGB_8888 32 每个像素 占八位 相对占内存
		Bitmap output = Bitmap.createBitmap(wh, wh, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();

		final Rect rect = new Rect(0, 0, output.getHeight(), output.getWidth());
		final RectF rectF = new RectF(rect);

		// paint.setAntiAlias(true),抗锯齿，如果没有调用这个方法，写上去的字不饱满，不美观，看地不太清楚/图片边缘不光滑
		paint.setAntiAlias(true);
		// 图片设置底色透明
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		// 画椭圆，参数一是扫描区域，参数二为paint对象。
		// canvas.drawOval(rectF, paint);
		canvas.drawRoundRect(rectF, output.getHeight() / 2,
				output.getHeight() / 2, paint);
		// Mode.SRC_IN 取两层交集，取上层
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
	
	/**
	 * 将bitmap画成圆角矩阵
	 * 
	 * @param bitmap 要画成圆角的bitmap
	 * @param pixels 圆角半径
	 * @return  bitmap
	 */
	public static Bitmap toRoundCornerForRect(Bitmap bitmap, int pixels) {

		if (bitmap == null)
			return null;
		// 根据源文件新建一个darwable对象
		Drawable imageDrawable = new BitmapDrawable(bitmap);
		int x = bitmap.getWidth();
		int y = bitmap.getHeight();
		// 新建一个新的输出图片
		Bitmap output = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		// 新建一个矩形
		RectF outerRect = new RectF(0, 0, x, y);

		// 产生一个红色的圆角矩形
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		canvas.drawRoundRect(outerRect, pixels, pixels, paint);

		// 将源图片绘制到这个圆角矩形上
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		imageDrawable.setBounds(0, 0, x, y);
		canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
		imageDrawable.draw(canvas);
		canvas.restore();

		return output;
	}
	
	
	/**
	 * Drawable 转 Bitmap
	 * @param drawable
	 * @return
	 */
	public static Bitmap convertDrawable2BitmapByCanvas(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 获取资源文件的drawable
	 * @param context
	 * @param id
	 * @return
	 */
	public static Drawable getDrawable(Context context, int id) {
		if (id != 0) {
			Drawable mDrawable = context.getResources().getDrawable(id);
			return mDrawable;
		}
		return null;
	}


}
