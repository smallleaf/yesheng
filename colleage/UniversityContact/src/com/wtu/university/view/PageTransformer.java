package com.wtu.university.view;

import android.support.v4.view.ViewPager;
import android.view.View;

public class PageTransformer implements ViewPager.PageTransformer{
	private static final float MIN_SCALE=0.75f;
	@Override
	public void transformPage(View view, float position) {
		// TODO Auto-generated method stub
		int pageWidth=view.getWidth();
		
		if(position<-1){
			//向左移
			System.out.println("11+"+position);
			view.setAlpha(50);
		}
		else if(position<=0){
			System.out.println("12+"+position);
			view.setAlpha(1);
			view.setTranslationX(0);
//			view.setScaleX(1);
//			view.setScaleY(1);
		}
		else if(position<=1){
			System.out.println("13+"+position);
			view.setAlpha(1-position);
			view.setTranslationX(pageWidth*-position);
			float scaleFactor=MIN_SCALE+(1-MIN_SCALE)*(1-Math.abs(position));
			
//			view.setScaleX(scaleFactor);
//			view.setScaleY(scaleFactor);
		}
		else {
			//向右移
			view.setAlpha(0);
		}
	}

	

}
