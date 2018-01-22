package com.eeb.pickpiclib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

public class FileUtils {
	
	private static String sdPath = null;;
	
	public static String getSDPath(Context context) {
		if(sdPath==null){
			String path = null;
			if (android.os.Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED)) {
				path = android.os.Environment.getExternalStorageDirectory()
						.getPath();
			}
			if (TextUtils.isEmpty(path)) {
				sdPath = context.getCacheDir().getPath()+ "/nuochunfeng/temp/";
			} else {
				sdPath = path + "/nuochunfeng/temp/";
			}
			File file = new File(sdPath);
			if(!file.exists()){
				file.mkdirs();
			}
		}
		return sdPath;
	}

	public static void saveBitmap(Bitmap bm, String picName,Context context) {
		Log.e("", "保存图片");
		try {
			if (!isFileExist("",context)) {
				createSDDir("",context);
			}
			File f = new File(getSDPath(context), picName + ".jpg"); 
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			Log.e("", "已经保存");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName,Context context) throws IOException {
		File dir = new File(getSDPath(context) + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName,Context context) {
		File file = new File(getSDPath(context) + fileName);
		file.isFile();
		return file.exists();
	}
	
	public static void delFile(String fileName,Context context){
		File file = new File(getSDPath(context) + fileName);
		if(file.isFile()){
			file.delete();
        }
		file.exists();
	}

	public static void deleteDir(Context context) {
		File dir = new File(getSDPath(context));
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除�?��文件
			else if (file.isDirectory())
				deleteDir(context); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

}
