/*
 * 首页相关操作
 * 
 */


package com.app.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import com.app.entity.Ad;
import com.app.entity.Bargain;
import com.app.entity.Store;
import com.app.service.AdService;
import com.app.service.BargainService;
import com.app.service.StoreService;
import com.app.util.ImageUtils;

public class MainAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private AdService adService;
	@Resource
	private StoreService storeService;
	@Resource
	private BargainService bargainService;
	
	/*private Map<String, Object> data=new HashMap<String, Object>();

	
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}*/

	//读取数据库中广告信息,使用base64编码后传回客户端
	public String getAds(){
		data.clear();
		List<Ad> ads=adService.getAd();
		if(ads==null || ads.size()<1){
			setDatas(false,"没有广告","ads",null);
			return SUCCESS;
		}else{
			for(Ad ad:ads){
				try {
					ad.setPic(ImageUtils.encodeImage(ad.getPic()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			setDatas(true,"ok","ads",ads);
			return SUCCESS;
		}
	}
	
	//读取商家信息
	public String getStores(){
		data.clear();
		List<Store> list=storeService.getStoreInfo();
		if(list!=null && list.size()>0){
			for(Store store:list){
				try {
					store.setPic(ImageUtils.encodeImage(store.getPic()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			setDatas(true,"ok","stores",list);
			/*data.put("code",1);
			data.put("desc","ok");
			data.put("stores",list);*/
		}
		return SUCCESS;
	}
	
	//获取特价信息
	public String getBargains(){
		List<Bargain> bargains=bargainService.getBargains();
		if(bargains!=null && bargains.size()>0){
			for(Bargain bargain :bargains){
				try {
					bargain.setPic(ImageUtils.encodeImage(bargain.getPic()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			setDatas(true,"ok","bargains",bargains);
		}else{
			setDatas(false,"fail","bargains",null);
		}
		return SUCCESS;
	}
	
}
