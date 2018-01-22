package com.nuocf.yshuobang.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.nuocf.yshuobang.db.bean.CityEntity;

/**
 * Created by yunfeng on 2016/9/14.
 */
public class AppDBmanager {
    //数据库版本
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ncf.db";
    private static final String DB_CITY_NAME = "city.db";
    static DbManager.DaoConfig daoConfig=null;
    public  DbManager getDBmanager(){
        if(daoConfig==null){
            daoConfig = new DbManager.DaoConfig()
                    .setDbName("ncf.db")
                    .setDbDir(new File("/data/data/" + x.app().getPackageName()
                            + "/databases/" + DB_NAME))//设置数据库存放的路径
                    .setDbVersion(DB_VERSION)
                    .setAllowTransaction(true)
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                        }
                    });
        }
        return x.getDb(daoConfig);
    }

    //把assets目录下的db文件复制到dbpath下
    public static SQLiteDatabase initCityDatabase() {
        String dbPath = "/data/data/" + x.app().getPackageName()
                + "/databases/" + DB_CITY_NAME;
        if (!new File(dbPath).exists()) {
            try {
                FileOutputStream out = new FileOutputStream(dbPath);
                InputStream in = x.app().getAssets().open("city.db");
                byte[] buffer = new byte[1024];
                int readBytes = 0;
                while ((readBytes = in.read(buffer)) != -1)
                    out.write(buffer, 0, readBytes);
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
    }

    /**
    * @Title:
    * @Description: todo(查询城市数据表)
    * @param   上一级id
    * @return  城市集合
    */
    public static List<CityEntity> queryCitys(String parentId) {
        CityEntity city = null;
        Cursor cursor = null;
        List<CityEntity> citys = new ArrayList<CityEntity>(0);
        try {
            cursor = initCityDatabase().query(CityTable.TABLE_NAME,
                    new String[]{CityTable.ID,CityTable.NAME,CityTable.CITY_CODE,CityTable.LEVEL_TYPE,CityTable.PARENT_ID,
                            CityTable.ZIP_CODE,CityTable.SHORT_NAME,CityTable.MEGER_SHORT_NAME},
                    "parentId=?", new String[]{parentId}, null, null, null);

            while (cursor.moveToNext()) {
                city = new CityEntity();
                city.setiD(cursor.getString(cursor
                        .getColumnIndex(CityTable.ID)));
                city.setName(cursor.getString(cursor.getColumnIndex(CityTable.NAME)));
                city.setShortName( cursor.getString(cursor.getColumnIndex(CityTable.SHORT_NAME)));
                city.setCityCode(cursor.getString(cursor.getColumnIndex(CityTable.CITY_CODE)));
                city.setParentId( cursor.getString(cursor.getColumnIndex(CityTable.PARENT_ID)));
                city.setZipCode(cursor.getString(cursor.getColumnIndex(CityTable.ZIP_CODE)));
                city.setLevelType( cursor.getString(cursor.getColumnIndex(CityTable.LEVEL_TYPE)));
                city.setMergerShortName(cursor.getString(cursor.getColumnIndex(CityTable.MEGER_SHORT_NAME)));
                citys.add(city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(cursor!=null)
                cursor.close();
        }
        return citys;
    }

    //城市编码转为城市名的方法
    public static CityEntity code2City(String cityCode) {
        CityEntity city = null;
        Cursor cursor = null;
        try {
            cursor = initCityDatabase().query(CityTable.TABLE_NAME,
                    new String[]{CityTable.ID,CityTable.NAME,CityTable.CITY_CODE,CityTable.LEVEL_TYPE,CityTable.PARENT_ID,
                            CityTable.ZIP_CODE,CityTable.SHORT_NAME,CityTable.MEGER_SHORT_NAME},
                    "iD=?", new String[]{cityCode}, null, null, null);

            if (cursor.getCount()>0) {
                cursor.moveToFirst();
                city = new CityEntity();
                city.setiD(cursor.getString(cursor
                        .getColumnIndex(CityTable.ID)));
                city.setName(cursor.getString(cursor.getColumnIndex(CityTable.NAME)));
                city.setShortName( cursor.getString(cursor.getColumnIndex(CityTable.SHORT_NAME)));
                city.setCityCode(cursor.getString(cursor.getColumnIndex(CityTable.CITY_CODE)));
                city.setParentId( cursor.getString(cursor.getColumnIndex(CityTable.PARENT_ID)));
                city.setZipCode(cursor.getString(cursor.getColumnIndex(CityTable.ZIP_CODE)));
                city.setLevelType( cursor.getString(cursor.getColumnIndex(CityTable.LEVEL_TYPE)));
                city.setMergerShortName(cursor.getString(cursor.getColumnIndex(CityTable.MEGER_SHORT_NAME)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(cursor!=null)
                cursor.close();
        }
        return city;
    }
}
