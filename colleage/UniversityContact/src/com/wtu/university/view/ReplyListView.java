package com.wtu.university.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 
 * @author small leaf
 *以防listview嵌套只显示第一行
 */
public class ReplyListView extends ListView{

	public ReplyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public ReplyListView(Context context,AttributeSet attrs){
		super(context, attrs);
	}
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
