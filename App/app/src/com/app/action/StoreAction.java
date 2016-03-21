/*
 * 店铺相关操作
 * 
 */

package com.app.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import com.app.entity.Goods;
import com.app.entity.PageBean;
import com.app.entity.Store;
import com.app.service.StoreService;
import com.app.util.ImageUtils;

public class StoreAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private StoreService storeService;

	private PageBean<Goods> pageBean;
	private int page=1;
	
	public PageBean<Goods> getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean<Goods> pageBean) {
		this.pageBean = pageBean;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	private int sid;
	
	public int getSid() {
		return sid;
	}
	
	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getGoods(){
		System.out.println(sid);
		this.pageBean=storeService.getAllGoodsInfo(sid,10, page);
		List<Goods> goods= pageBean.getList();
		System.out.println("大小+"+goods.size());
		if(goods!=null && goods.size()>0){
				for(Goods good:goods){
					try {
						System.out.println(good.getName());
						good.setPic(ImageUtils.encodeImage(good.getPic()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			setDatas(true, "ok","goods",goods);
		}else{
			setDatas(false,"failed to get goods ","goods",null);
		}
		return SUCCESS;
	}
	
	
}
