package com.wtu.university.slidingMenu.right.information.adapter;
 
import java.util.ArrayList;
import java.util.List;

import com.example.universityconnection.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
 
/**
 * @author IdeasAndroid 
 * 可展开（收缩）列表示例
 */
public class IdeasExpandableListAdapter extends BaseExpandableListAdapter {
 
        private Context mContext = null;
        // 测试数据，开发时可能来自数据库，网络....
//        private String[] groups = { "家人", "朋友", "同事" };
//        private String[] familis = { "老爸", "老妈", "妹妹" };
//        private String[] friends = { "小李", "张三", "李四" };
//        private String[] colleagues = { "陈总", "李工", "李客户" };
        
//      
        private String province[];
        String university[][];
        private List<String> groupList = null;
        private List<List<String>> itemList = null;
 
        
        public IdeasExpandableListAdapter(Context context,String province[],String university[][]) {
                this.mContext = context;
                groupList = new ArrayList<String>();
                itemList = new ArrayList<List<String>>();
                this.province=province;
                this.university=university;
                initData();
        }
 
        /**
         * 初始化数据，将相关数据放到List中，方便处理
         */
        private void initData() {
        	
        	
                for (int i = 0; i < province.length; i++) {
                        groupList.add(province[i]);
                }
                
                for (int i = 0; i < university.length; i++) {
                	List<String> item = new ArrayList<String>();
                	for (int j = 0; j < university[i].length; j++) {
                		item.add(university[i][j]);
					}
                	itemList.add(item);
                }
 
        }
 
        public boolean areAllItemsEnabled() {
                return false;
        }
 
        /*
         * 设置子节点对象，在事件处理时返回的对象，可存放一些数据
         */
        public Object getChild(int groupPosition, int childPosition) {
                return itemList.get(groupPosition).get(childPosition);
        }
 
        public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
        }
 
        /*
         * 字节点视图，这里我们显示一个文本对象
         */
        @SuppressLint("ResourceAsColor")
		public View getChildView(int groupPosition, int childPosition,
                        boolean isLastChild, View convertView, ViewGroup parent) {
                TextView text = null;
                if (convertView == null) {
                        text = new TextView(mContext);
                } else {
                        text = (TextView) convertView;
                }
                // 获取子节点要显示的名称
                String name = (String) itemList.get(groupPosition).get(childPosition);
                // 设置文本视图的相关属性
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                                ViewGroup.LayoutParams.FILL_PARENT, 90);
                text.setLayoutParams(lp);
                text.setTextSize(18);
                text.setBackgroundResource(R.drawable.university_child_bg);
                text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                text.setPadding(65, 0, 0, 0);
                text.setText(name);
                return text;
        }
 
        /*
         * 返回当前分组的字节点个数
         */
        public int getChildrenCount(int groupPosition) {
                return itemList.get(groupPosition).size();
        }
 
        /*
         * 返回分组对象，用于一些数据传递，在事件处理时可直接取得和分组相关的数据
         */
        public Object getGroup(int groupPosition) {
                return groupList.get(groupPosition);
        }
 
        /*
         * 分组的个数
         */
        public int getGroupCount() {
                return groupList.size();
        }
 
        public long getGroupId(int groupPosition) {
                return groupPosition;
        }
 
        /*
         * 分组视图，这里也是一个文本视图
         */
        public View getGroupView(int groupPosition, boolean isExpanded,
                        View convertView, ViewGroup parent) {
                TextView text = null;
                if (convertView == null) {
                        text = new TextView(mContext);
                } else {
                        text = (TextView) convertView;
                }
                String name = (String) groupList.get(groupPosition);
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                                ViewGroup.LayoutParams.FILL_PARENT, 100);
                text.setLayoutParams(lp);
                text.setTextSize(18);
                text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                text.setPadding(65, 0, 0, 0);
                text.setText(name);
                return text;
        }
 
        /*
         * 判断分组是否为空，本示例中数据是固定的，所以不会为空，我们返回false 
         * 如果数据来自数据库，网络时，可以把判断逻辑写到这个方法中，如果为空
         * 时返回true
         */
        public boolean isEmpty() {
                return false;
        }
 
        /*
         * 收缩列表时要处理的东西都放这儿
         */
        public void onGroupCollapsed(int groupPosition) {
 
        }
 
        /*
         * 展开列表时要处理的东西都放这儿
         */
        public void onGroupExpanded(int groupPosition) {
 
        }
 
        /*
         * Indicates whether the child and group IDs are stable across changes to
         * the underlying data.
         */
        public boolean hasStableIds() {
                return false;
        }
 
        /*
         * Whether the child at the specified position is selectable.
         */
        public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
        }
}