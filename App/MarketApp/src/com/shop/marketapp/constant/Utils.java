package com.shop.marketapp.constant;

import android.content.Context;
import android.net.ConnectivityManager;

public class Utils {
	/**
	 * ÍøÂçÊÇ·ñ´ò¿ª
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetWorkIsAvailable(Context context) {

		ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cwjManager.getActiveNetworkInfo() != null){
			return true;
		}else{
			return false;
		}
	}
}
