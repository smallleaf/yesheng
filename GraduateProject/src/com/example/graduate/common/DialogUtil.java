package com.example.graduate.common;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;

/**
 * 
 * @author Administrator
 *常用的对话框
 */
public class DialogUtil
{
	public static void showDialog(final Context ctx
		, String msg , boolean closeSelf)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
			.setMessage(msg).setCancelable(false);
		if(closeSelf)
		{
			builder.setPositiveButton("确定", new OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					((Activity)ctx).finish();
				}
			});		
		}
		else
		{
			builder.setPositiveButton("取消", null);
		}
		builder.create().show();
	}	
	public static void showDialog(Context ctx , View view)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
			.setView(view).setCancelable(false)
			.setPositiveButton("sfds", null);
		builder.create()
			.show();
	}
	public static void showToast(Context  content,String msg)
	{
		
		Toast.makeText(content, msg, Toast.LENGTH_SHORT).show();
		
	}
	public  static void showToastThread(Handler handler,final Context  content,final String msg) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(content, msg, Toast.LENGTH_SHORT).show();
			}

		});
	}
}
