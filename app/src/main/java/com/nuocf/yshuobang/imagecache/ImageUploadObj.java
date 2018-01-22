package com.nuocf.yshuobang.imagecache;

/**
 * Created by yunfeng on 2016/9/26.
 */
public class ImageUploadObj {
    public String filePath;
    public UploadEnum upState;
    public String fileId;
    public int upSize;
    public void initUploadSize(){
        upSize = 0;
    }
    public void setPreUploadState(){
        upState = UploadEnum.UP_PRE;
    }
}
