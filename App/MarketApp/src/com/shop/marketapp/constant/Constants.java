package com.shop.marketapp.constant;


import android.os.Environment;

import com.example.marketapp.R;

public class Constants {

	/**
	 * ͼƬ�ļ�����λ��
	 */
	public static String PIC_PATH=Environment.getExternalStorageDirectory().getAbsolutePath()+"/marketapp/picture/";
	// SharedPreferences �洢����
	public static String SP_NAME = "Common";// SharedPreferences����
	public static String SP_LOGIN_NAME="login_name";//��½���û���
	/**
	 * �Ƿ��½
	 */
	public static String SP_ISLOGIN="islogin";
	/**
	 * ����ͼƬ
	 */
	public static String SP_PICTURE="picter";
	/**
	 * ���ڴ���Ʒ��ϸҳ������ ���ﳵҳ�� ���Ǵ�ʱ���ҳ����Ҫ���ذ�ť ����ҳ�Ĳ���Ҫ���ذ�ť
	 */
	public static String SP_BURCAR_BACK="back";
	/**
	 * ���ݷ����ı�ʱ Ҫ��onResume�лָ�����
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
