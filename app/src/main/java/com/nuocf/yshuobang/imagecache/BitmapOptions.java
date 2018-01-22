package com.nuocf.yshuobang.imagecache;

import android.widget.ImageView;

import org.xutils.image.ImageOptions;

import com.nuocf.yshuobang.R;

/**
* @ClassName: BitmapOptions
* @Description: todo(加载图片的配置)
* @author yunfeng
* @date 2016/8/31
*/
public class BitmapOptions{
        protected ImageOptions.Builder myBuilder = null;
        public BitmapOptions(){
                newBitmapOptions();
        }

        protected void newBitmapOptions() {
                if(myBuilder==null)
                        myBuilder = new ImageOptions.Builder();
        }

        public ImageOptions getOptions(){
                return myBuilder.build();
        }

        /**
        * @Title:initDefaultOptions
        * @Description: todo(初始化应用默认加载图片配置)
        */
        public ImageOptions initDefaultOptions() {
                myBuilder.setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP);
                myBuilder.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                myBuilder.setLoadingDrawableId(R.mipmap.default_image);
                myBuilder.setFailureDrawableId(R.mipmap.default_image);
                return myBuilder.build();
        }
        /**
         * @Title:initMyOptions
         * @Description: todo(初始化应用当前加载图片配置)
         */
        public ImageOptions initMyOptions(ImageView.ScaleType holderScale,
                                          ImageView.ScaleType imageScale,
                                          int loadingDrawableId,
                                          int failureDrawableId) {
                myBuilder.setPlaceholderScaleType(holderScale);
                myBuilder.setImageScaleType(imageScale);
                myBuilder.setLoadingDrawableId(loadingDrawableId);
                myBuilder.setFailureDrawableId(failureDrawableId);
                return myBuilder.build();
        }

        /**
        * @Title: setLoadingDrawableId
        * @Description: todo(设置加载中显示图片)
        * @param int 加载中显示图片
        */
        public void setLoadingDrawableId(int loadingDrawableId){
                myBuilder.setLoadingDrawableId(loadingDrawableId);
        }
        /**
         * @Title: setLoadingDrawableId
         * @Description: todo(设置加载失败显示图片)
         * @param int 加载失败显示图片
         */
        public void setFailureDrawableId(int failureDrawableId){
                myBuilder.setLoadingDrawableId(failureDrawableId);
        }
    }



