package com.nuocf.yshuobang.imagecache;

/**
* @ClassName: UploadEnum
* @Description: todo(上传状态)
* @author yunfeng
* @date 2016/9/26
*/
public enum UploadEnum {
    //等待上传
    UP_PRE(1),
    //上传中
    UP_LOADING(2),
    //上传完成
    UP_COMPLETE(3) {
        @Override
        public boolean isRest() {
            return true;
        }
    };

    UploadEnum(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }

    public boolean isRest() {
        return false;
    }
}
