package com.shop.marketapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/***
 * 
 * @author small leaf
 *scrollview Ƕ��gridview �����ͻ��дonMeasure����
 */
public class MyGridView extends GridView{

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {      
        int expandSpec = MeasureSpec.makeMeasureSpec( 
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST); 
        super.onMeasure(widthMeasureSpec, expandSpec); 
    } 

}
