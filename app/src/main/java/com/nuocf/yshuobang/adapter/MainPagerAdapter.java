package com.nuocf.yshuobang.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import com.nuocf.yshuobang.frament.HomeFragment;
import com.nuocf.yshuobang.frament.MineFragment;
import com.nuocf.yshuobang.frament.NewsFragment;
import com.nuocf.yshuobang.frament.RecommendFragment;

/**
* @ClassName: MainPagerAdapter
* @Description: todo(主页碎片适配器)
* @author xiong
* @date 2016/8/30
*/

public class MainPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> list = new ArrayList<Fragment>();

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        list.add(new HomeFragment());
        list.add(new RecommendFragment());
        list.add(new NewsFragment());
        list.add(new MineFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

}
