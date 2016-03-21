package com.shop.marketapp.constant;


import android.os.Environment;

import com.example.marketapp.R;

public class Constants {

	/**
	 * 图片文件传出位置
	 */
	public static String PIC_PATH=Environment.getExternalStorageDirectory().getAbsolutePath()+"/marketapp/picture/";
	// SharedPreferences 存储数据
	public static String SP_NAME = "Common";// SharedPreferences名称
	public static String SP_LOGIN_NAME="login_name";//登陆的用户名
	/**
	 * 是否登陆
	 */
	public static String SP_ISLOGIN="islogin";
	/**
	 * 保存图片
	 */
	public static String SP_PICTURE="picter";
	/**
	 * 由于从商品详细页面跳到 购物车页面 我们此时这个页面需要返回按钮 而主页的不需要返回按钮
	 */
	public static String SP_BURCAR_BACK="back";
	/**
	 * 数据发生改变时 要在onResume中恢复数据
	 */
	public static String SP_DATA_CHANGE="datachange";
	public static int[] merchant={
		R.drawable.zhongbai,
		R.drawable.zhongbai,
		R.drawable.zhongbai,
		R.drawable.zhongbai,
		R.drawable.zhongbai,
		R.drawable.zhongbai,
		R.drawable.zhongbai,
		R.drawable.zhongbai,
		R.drawable.zhongbai,
		R.drawable.zhongbai,
		R.drawable.zhongbai
	};
	
	public static int[] recomment={
		R.drawable.main_today_recomment,
		R.drawable.main_today_recomment,
		R.drawable.main_today_recomment,
		R.drawable.main_today_recomment,
		R.drawable.main_today_recomment,
		R.drawable.main_today_recomment,
		R.drawable.main_today_recomment,
		R.drawable.main_today_recomment,
		R.drawable.main_today_recomment,
		R.drawable.main_today_recomment,
		R.drawable.main_today_recomment,
		R.drawable.main_today_recomment,
		R.drawable.main_today_recomment
	};
	
	
	public static int[] city = { R.array.beijin_province_item,
		R.array.tianjin_province_item, R.array.heibei_province_item,
		R.array.shanxi1_province_item, R.array.neimenggu_province_item,
		R.array.liaoning_province_item, R.array.jilin_province_item,
		R.array.heilongjiang_province_item, R.array.shanghai_province_item,
		R.array.jiangsu_province_item, R.array.zhejiang_province_item,
		R.array.anhui_province_item, R.array.fujian_province_item,
		R.array.jiangxi_province_item, R.array.shandong_province_item,
		R.array.henan_province_item, R.array.hubei_province_item,
		R.array.hunan_province_item, R.array.guangdong_province_item,
		R.array.guangxi_province_item, R.array.hainan_province_item,
		R.array.chongqing_province_item, R.array.sichuan_province_item,
		R.array.guizhou_province_item, R.array.yunnan_province_item,
		R.array.xizang_province_item, R.array.shanxi2_province_item,
		R.array.gansu_province_item, R.array.qinghai_province_item,
		R.array.linxia_province_item, R.array.xinjiang_province_item,
		R.array.hongkong_province_item, R.array.aomen_province_item,
		R.array.taiwan_province_item };
}
