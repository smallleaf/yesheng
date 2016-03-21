package com.wtu.university.slidingMenu.left.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * @author ScrollView �� GridView ��ExpandableListView��ʾ��ͻ����
 */
public class MyGridView extends GridView
{
    public MyGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    public MyGridView(Context context)
    {
        super(context);
    }
    
    public MyGridView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
    
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
