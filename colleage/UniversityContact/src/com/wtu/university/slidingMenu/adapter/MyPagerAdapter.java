package com.wtu.university.slidingMenu.adapter;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Pager适配器
 */
public class MyPagerAdapter extends PagerAdapter{
	List<View> list =  new ArrayList<View>();
	public List<View> getList() {
		return this.list;
	}
	public MyPagerAdapter(ArrayList<View> list) {
		this.list = list;
	}
	//滑动的view如果超出滑动的数目自动销毁view
	
	@Override
	public void destroyItem(ViewGroup container, int position,
			Object object) {
		ViewPager pViewPager = ((ViewPager) container);
		pViewPager.removeView(list.get(position));
	}
	//判断该是否为同一个view
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	//获得滑动的数目
	@Override
	public int getCount() {
		return list.size();
	}
	// 当要显示的view可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
	@Override
	public Object instantiateItem(View arg0, int arg1) {
		ViewPager pViewPager = ((ViewPager) arg0);
		pViewPager.addView(list.get(arg1));
		return list.get(arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {

	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}
}
