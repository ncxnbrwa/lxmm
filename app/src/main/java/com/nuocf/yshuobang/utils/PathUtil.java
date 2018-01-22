package com.nuocf.yshuobang.utils;

import android.text.TextUtils;

import com.nuocf.yshuobang.appBase.Config;

import org.xutils.x;

import java.io.File;

public class PathUtil {
	private String root;
	private String mDownloadImagePath,mUploadImagePath;
	private String mSaveImagePath;
	private static PathUtil mPathUtil;
	//懒汉式单例
	public static PathUtil get() {
		if(mPathUtil==null)
			mPathUtil = new PathUtil();
		return mPathUtil;
	}

	public void initRootPaht() {
		String path = null;
		//判断路径是否可用
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			//获取路径
			path = android.os.Environment.getExternalStorageDirectory()
					.getPath();
		}
		if (TextUtils.isEmpty(path)) {
			root = x.app().getCacheDir().getPath()+ Config.ROOTPATH;
		} else {
			root = path + Config.ROOTPATH;
		}
		exitesFolder(root);
		DLog.d(getClass().getSimpleName(), "the path of app:" + path);
	}
	
	//创建目录方法
	public void exitesFolder(String path){
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	//获取根目录路径
	public String getRootpath(){
		if(TextUtils.isEmpty(root)){
			initRootPaht();
		}
		return root;
	}
	//获取图片下载的路径
	public String getDownloadImagePath(){
		if (TextUtils.isEmpty(mDownloadImagePath)) {
			mDownloadImagePath = root + "download";
		}
		exitesFolder(mDownloadImagePath);
		return mDownloadImagePath;
	}

	public String getUploadImagePath(){
		if (TextUtils.isEmpty(mUploadImagePath)) {
			mUploadImagePath = getRootpath() + "upload";
		}
		DLog.d(getClass().getSimpleName(), mUploadImagePath);
		exitesFolder(mUploadImagePath);
		return mUploadImagePath;
	}
	
	//获取图片保存路径
	public String getSaveImagePath(){
		if (TextUtils.isEmpty(mSaveImagePath)) {
			mSaveImagePath = root + "saveImage";
		}
		exitesFolder(mSaveImagePath);
		return mSaveImagePath;
	}

	//删除对应文件
	public static void delFile(File file){
		if(file.isFile()){
			file.delete();
		}
		file.exists();
	}
}
