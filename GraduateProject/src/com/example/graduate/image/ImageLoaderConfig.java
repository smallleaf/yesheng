package com.example.graduate.image;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.graduateproject.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * @author Tau.Chen ����
 * 
 * @email tauchen1990@gmail.com,1076559197@qq.com
 * 
 * @date 2013��9��12��
 * 
 * @version V_1.0.0
 * 
 * @description
 * 
 */
public class ImageLoaderConfig {

	/**
	 * ����Ĭ�ϵĲ�������
	 * 
	 * @param isDefaultShow
	 *            true����ʾĬ�ϵļ���ͼƬ false������ʾĬ�ϵļ���ͼƬ
	 * @return
	 */
	public static DisplayImageOptions initDisplayOptions(boolean isShowDefault) {
		DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
		// ����ͼƬ���ŷ�ʽ
		// EXACTLY: ͼ����ȫ��������С��Ŀ���С
		// EXACTLY_STRETCHED: ͼƬ�����ŵ�Ŀ���С
		// IN_SAMPLE_INT: ͼ�񽫱����β�����������
		// IN_SAMPLE_POWER_OF_2: ͼƬ������2����ֱ����һ���ٲ��裬ʹͼ���С��Ŀ���С
		// NONE: ͼƬ�������
		displayImageOptionsBuilder.imageScaleType(ImageScaleType.EXACTLY);
		if (isShowDefault) {
			// Ĭ����ʾ��ͼƬ
			displayImageOptionsBuilder.showStubImage(R.drawable.iamge1);
			// ��ַΪ�յ�Ĭ����ʾͼƬ
			displayImageOptionsBuilder
					.showImageForEmptyUri(R.drawable.iamge1);
			// ����ʧ�ܵ���ʾͼƬ
			displayImageOptionsBuilder.showImageOnFail(R.drawable.iamge1);
		}
		// �����ڴ滺��
		displayImageOptionsBuilder.cacheInMemory(true);
		// ����SDCard����
		displayImageOptionsBuilder.cacheOnDisc(true);
		// ����ͼƬ�ı����ʽΪRGB_565���˸�ʽ��ARGB_8888��
		displayImageOptionsBuilder.bitmapConfig(Bitmap.Config.RGB_565);

		return displayImageOptionsBuilder.build();
	}

	/**
	 * �����޸�ͼƬ��С�ļ��ز�������
	 * 
	 * @return
	 */
	public static DisplayImageOptions initDisplayOptions(int targetWidth,
			boolean isShowDefault) {
		DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
		// ����ͼƬ���ŷ�ʽ
		// EXACTLY: ͼ����ȫ��������С��Ŀ���С
		// EXACTLY_STRETCHED: ͼƬ�����ŵ�Ŀ���С
		// IN_SAMPLE_INT: ͼ�񽫱����β�����������
		// IN_SAMPLE_POWER_OF_2: ͼƬ������2����ֱ����һ���ٲ��裬ʹͼ���С��Ŀ���С
		// NONE: ͼƬ�������
		displayImageOptionsBuilder.imageScaleType(ImageScaleType.EXACTLY);
		if (isShowDefault) {
			// Ĭ����ʾ��ͼƬ
			displayImageOptionsBuilder.showStubImage(R.drawable.iamge1);
			// ��ַΪ�յ�Ĭ����ʾͼƬ
			displayImageOptionsBuilder
					.showImageForEmptyUri(R.drawable.iamge1);
			// ����ʧ�ܵ���ʾͼƬ
			displayImageOptionsBuilder
					.showImageOnFail(R.drawable.iamge1);
		}
		// �����ڴ滺��
		displayImageOptionsBuilder.cacheInMemory(true);
		// ����SDCard����
		displayImageOptionsBuilder.cacheOnDisc(true);
		// ����ͼƬ�ı����ʽΪRGB_565���˸�ʽ��ARGB_8888��
		displayImageOptionsBuilder.bitmapConfig(Bitmap.Config.RGB_565);
		// ����ͼƬ��ʾ��ʽ
		displayImageOptionsBuilder.displayer(new SimpleImageDisplayer(
				targetWidth));

		return displayImageOptionsBuilder.build();
	}

	/**
	 * �첽ͼƬ����ImageLoader�ĳ�ʼ����������Application�е��ô˷���
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param cacheDisc
	 *            ͼƬ���浽SDCard��Ŀ¼��ֻ��Ҫ����SDCard��Ŀ¼�µ���Ŀ¼���ɣ�Ĭ�ϻὨ����SDcard�ĸ�Ŀ¼��
	 */
	public static void initImageLoader(Context context, String cacheDisc) {
		// ����ImageLoader
		// ��ȡ���ػ����Ŀ¼����Ŀ¼��SDCard�ĸ�Ŀ¼��
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, cacheDisc);
		// ʵ����Builder
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				context);
		// �����߳�����
		builder.threadPoolSize(3);
		// �趨�̵߳ȼ�����ͨ��һ��
		builder.threadPriority(Thread.NORM_PRIORITY);
		// �趨�ڴ滺��Ϊ������
		builder.memoryCache(new WeakMemoryCache());
		// �趨�ڴ�ͼƬ�����С���ƣ�������Ĭ��Ϊ��Ļ�Ŀ��
		builder.memoryCacheExtraOptions(480, 800);
		// �趨ֻ����ͬһ�ߴ��ͼƬ���ڴ�
		builder.denyCacheImageMultipleSizesInMemory();
		// �趨�����SDcardĿ¼��UnlimitDiscCache�ٶ����
		builder.discCache(new UnlimitedDiscCache(cacheDir));
		// �趨���浽SDCardĿ¼���ļ�����
		builder.discCacheFileNameGenerator(new HashCodeFileNameGenerator());
		// �趨�������ӳ�ʱ timeout: 10s ��ȡ�������ӳ�ʱread timeout: 60s
		builder.imageDownloader(new BaseImageDownloader(context, 10000, 60000));
		// ����ImageLoader�����ò���
		builder.defaultDisplayImageOptions(initDisplayOptions(true));

		// ��ʼ��ImageLoader
		ImageLoader.getInstance().init(builder.build());
	}
}
