package com.wtu.university.common;

import android.content.res.Resources;

import com.example.universityconnection.R;

public class Constants {
	public static int userChanneChoose = 0;// 表示你选择的是哪个频道

	// SharedPreferences 存储数据
	public static String SP_NAME = "Common";// SharedPreferences名称
	public static String SP_USER_NAME = "username";// 用户名
	public static String SP_IS_PASSWORD = "isChecked";// 是否记住密码
	public static String SP_PASSWORD = "password";// 密码
	public static String SP_ATUON_LOGIN = "isLogin";// 密码
	public static String SP_LOGOUT = "zhxiao";// zhuxiao
	public static String SP_REGISTER = "zhuce";// zhuxiao
	public static String SP_BACK = "back";// 返回
	public static String SP_USER_PICTURE = "bitmap";// 返回
	public static String SP_LOGIN_STATE="login_state";//登陆状态
	//用户信息
	public static String SP_UNIVERSITY="universiy";
	public static String SP_JOB="job";
	public static String SP_MAJOR="major";
	public static String SP_ADDRESS="address";
	public static String SP_HOBBY1="hobby1";
	public static String SP_HOBBY2="hobby2";
	public static String SP_HOBBY3="hobby3";
	public static String SP_ASSOCIATITION="associatition";
	
	/**高校板块 选择高校的名称*/
	public static String SP_UNIVERISTY_CHOOSE="choose";
	/**高校板块 返回高校频道ID*/
	public static String SP_UNIVERISTY_ID="university_id";
	//判断多个线程是否已经结束
	public static int threadNum;
	//线程已经执行的个数
	public static int threadNuber=0;
	
	/**防止左右滑动与下拉刷新的冲突*/
	
	/**连接服务器是否成功*/
	public static String conncectServer="";
	
	
	
	
	/**university 界面 gridview图片*/
	public static int[] universitys_picture={
		R.drawable.sale_coin,
		R.drawable.sale_coin,
		R.drawable.sale_coin,
		R.drawable.sale_coin
	};
	/**
	 * university 界面gridview图片对应的文字
	 */
	public static String[] universitys_textStrings={
		"校内新闻",
		"学生活动",
		"校内通知",
		"校内讲座"
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
	
	private int[] countyOfBeiJing = { R.array.beijin_city_item };
	private int[] countyOfTianJing = { R.array.tianjin_city_item };
	private int[] countyOfHeBei = { R.array.shijiazhuang_city_item,
			R.array.tangshan_city_item, R.array.qinghuangdao_city_item,
			R.array.handan_city_item, R.array.xingtai_city_item,
			R.array.baoding_city_item, R.array.zhangjiakou_city_item,
			R.array.chengde_city_item, R.array.cangzhou_city_item,
			R.array.langfang_city_item, R.array.hengshui_city_item };
	private int[] countyOfShanXi1 = { R.array.taiyuan_city_item,
			R.array.datong_city_item, R.array.yangquan_city_item,
			R.array.changzhi_city_item, R.array.jincheng_city_item,
			R.array.shuozhou_city_item, R.array.jinzhong_city_item,
			R.array.yuncheng_city_item, R.array.xinzhou_city_item,
			R.array.linfen_city_item, R.array.lvliang_city_item };
	private int[] countyOfNeiMengGu = { R.array.huhehaote_city_item,
			R.array.baotou_city_item, R.array.wuhai_city_item,
			R.array.chifeng_city_item, R.array.tongliao_city_item,
			R.array.eerduosi_city_item, R.array.hulunbeier_city_item,
			R.array.bayannaoer_city_item, R.array.wulanchabu_city_item,
			R.array.xinganmeng_city_item, R.array.xilinguolemeng_city_item,
			R.array.alashanmeng_city_item };
	private int[] countyOfLiaoNing = { R.array.shenyang_city_item,
			R.array.dalian_city_item, R.array.anshan_city_item,
			R.array.wushun_city_item, R.array.benxi_city_item,
			R.array.dandong_city_item, R.array.liaoning_jinzhou_city_item,
			R.array.yingkou_city_item, R.array.fuxin_city_item,
			R.array.liaoyang_city_item, R.array.panjin_city_item,
			R.array.tieling_city_item, R.array.zhaoyang_city_item,
			R.array.huludao_city_item };
	private int[] countyOfJiLin = { R.array.changchun_city_item,
			R.array.jilin_city_item, R.array.siping_city_item,
			R.array.liaoyuan_city_item, R.array.tonghua_city_item,
			R.array.baishan_city_item, R.array.songyuan_city_item,
			R.array.baicheng_city_item, R.array.yanbian_city_item };
	private int[] countyOfHeiLongJiang = { R.array.haerbing_city_item,
			R.array.qiqihaer_city_item, R.array.jixi_city_item,
			R.array.hegang_city_item, R.array.shuangyashan_city_item,
			R.array.daqing_city_item, R.array.heilongjiang_yichun_city_item,
			R.array.jiamusi_city_item, R.array.qitaihe_city_item,
			R.array.mudanjiang_city_item, R.array.heihe_city_item,
			R.array.suihua_city_item, R.array.daxinganling_city_item };
	private int[] countyOfShangHai = { R.array.shanghai_city_item };

	private int[] countyOfJiangSu = { R.array.nanjing_city_item,
			R.array.wuxi_city_item, R.array.xuzhou_city_item,
			R.array.changzhou_city_item, R.array.nanjing_suzhou_city_item,
			R.array.nantong_city_item, R.array.lianyungang_city_item,
			R.array.huaian_city_item, R.array.yancheng_city_item,
			R.array.yangzhou_city_item, R.array.zhenjiang_city_item,
			R.array.jiangsu_taizhou_city_item, R.array.suqian_city_item };
	private int[] countyOfZheJiang = { R.array.hangzhou_city_item,
			R.array.ningbo_city_item, R.array.wenzhou_city_item,
			R.array.jiaxing_city_item, R.array.huzhou_city_item,
			R.array.shaoxing_city_item, R.array.jinhua_city_item,
			R.array.quzhou_city_item, R.array.zhoushan_city_item,
			R.array.zejiang_huzhou_city_item, R.array.lishui_city_item };
	private int[] countyOfAnHui = { R.array.hefei_city_item,
			R.array.wuhu_city_item, R.array.bengbu_city_item,
			R.array.huainan_city_item, R.array.maanshan_city_item,
			R.array.huaibei_city_item, R.array.tongling_city_item,
			R.array.anqing_city_item, R.array.huangshan_city_item,
			R.array.chuzhou_city_item, R.array.fuyang_city_item,
			R.array.anhui_suzhou_city_item, R.array.chaohu_city_item,
			R.array.luan_city_item, R.array.haozhou_city_item,
			R.array.chizhou_city_item, R.array.xuancheng_city_item };
	private int[] countyOfFuJian = { R.array.huzhou_city_item,
			R.array.xiamen_city_item, R.array.putian_city_item,
			R.array.sanming_city_item, R.array.quanzhou_city_item,
			R.array.zhangzhou_city_item, R.array.nanp_city_item,
			R.array.longyan_city_item, R.array.ningde_city_item };
	private int[] countyOfJiangXi = { R.array.nanchang_city_item,
			R.array.jingdezhen_city_item, R.array.pingxiang_city_item,
			R.array.jiujiang_city_item, R.array.xinyu_city_item,
			R.array.yingtan_city_item, R.array.ganzhou_city_item,
			R.array.jian_city_item, R.array.jiangxi_yichun_city_item,
			R.array.jiangxi_wuzhou_city_item, R.array.shangrao_city_item };
	private int[] countyOfShanDong = { R.array.jinan_city_item,
			R.array.qingdao_city_item, R.array.zaobo_city_item,
			R.array.zaozhuang_city_item, R.array.dongying_city_item,
			R.array.yantai_city_item, R.array.weifang_city_item,
			R.array.jining_city_item, R.array.taian_city_item,
			R.array.weihai_city_item, R.array.rizhao_city_item,
			R.array.laiwu_city_item, R.array.linxi_city_item,
			R.array.dezhou_city_item, R.array.liaocheng_city_item,
			R.array.shandong_bingzhou_city_item, R.array.heze_city_item };
	private int[] countyOfHeNan = { R.array.zhenshou_city_item,
			R.array.kaifang_city_item, R.array.luoyang_city_item,
			R.array.kaipingshan_city_item, R.array.anyang_city_item,
			R.array.hebi_city_item, R.array.xinxiang_city_item,
			R.array.jiaozuo_city_item, R.array.buyang_city_item,
			R.array.xuchang_city_item, R.array.leihe_city_item,
			R.array.sanmenxia_city_item, R.array.nanyang_city_item,
			R.array.shangqiu_city_item, R.array.xinyang_city_item,
			R.array.zhoukou_city_item, R.array.zhumadian_city_item };
	private int[] countyOfHuBei = { R.array.wuhan_city_item,
			R.array.huangshi_city_item, R.array.shiyan_city_item,
			R.array.yichang_city_item, R.array.xiangpan_city_item,
			R.array.erzhou_city_item, R.array.jinmen_city_item,
			R.array.xiaogan_city_item, R.array.hubei_jinzhou_city_item,
			R.array.huanggang_city_item, R.array.xianning_city_item,
			R.array.suizhou_city_item, R.array.enshi_city_item,
			R.array.shenglongjia_city_item };

	private int[] countyOfHuNan = { R.array.changsha_city_item,
			R.array.zhuzhou_city_item, R.array.xiangtan_city_item,
			R.array.hengyang_city_item, R.array.shaoyang_city_item,
			R.array.yueyang_city_item, R.array.changde_city_item,
			R.array.zhangjiajie_city_item, R.array.yiyang_city_item,
			R.array.hunan_bingzhou_city_item, R.array.yongzhou_city_item,
			R.array.huaihua_city_item, R.array.loudi_city_item,
			R.array.xiangxi_city_item };
	private int[] countyOfGuangDong = { R.array.guangzhou_city_item,
			R.array.shaoguan_city_item, R.array.shenzhen_city_item,
			R.array.zhuhai_city_item, R.array.shantou_city_item,
			R.array.foshan_city_item, R.array.jiangmen_city_item,
			R.array.zhangjiang_city_item, R.array.maoming_city_item,
			R.array.zhaoqing_city_item, R.array.huizhou_city_item,
			R.array.meizhou_city_item, R.array.shanwei_city_item,
			R.array.heyuan_city_item, R.array.yangjiang_city_item,
			R.array.qingyuan_city_item, R.array.dongguan_city_item,
			R.array.zhongshan_city_item, R.array.chaozhou_city_item,
			R.array.jiyang_city_item, R.array.yunfu_city_item };
	private int[] countyOfGuangXi = { R.array.nanning_city_item,
			R.array.liuzhou_city_item, R.array.guilin_city_item,
			R.array.guangxi_wuzhou_city_item, R.array.beihai_city_item,
			R.array.fangchenggang_city_item, R.array.qinzhou_city_item,
			R.array.guigang_city_item, R.array.yuelin_city_item,
			R.array.baise_city_item, R.array.hezhou_city_item,
			R.array.hechi_city_item, R.array.laibing_city_item,
			R.array.chuangzuo_city_item };
	private int[] countyOfHaiNan = { R.array.haikou_city_item,
			R.array.sanya_city_item };
	private int[] countyOfChongQing = { R.array.chongqing_city_item };
	private int[] countyOfSiChuan = { R.array.chengdu_city_item,
			R.array.zigong_city_item, R.array.panzhihua_city_item,
			R.array.luzhou_city_item, R.array.deyang_city_item,
			R.array.mianyang_city_item, R.array.guangyuan_city_item,
			R.array.suining_city_item, R.array.neijiang_city_item,
			R.array.leshan_city_item, R.array.nanchong_city_item,
			R.array.meishan_city_item, R.array.yibing_city_item,
			R.array.guangan_city_item, R.array.dazhou_city_item,
			R.array.yaan_city_item, R.array.bazhong_city_item,
			R.array.ziyang_city_item, R.array.abei_city_item,
			R.array.ganmu_city_item, R.array.liangshan_city_item };
	private int[] countyOfGuiZhou = { R.array.guiyang_city_item,
			R.array.lupanshui_city_item, R.array.zhunyi_city_item,
			R.array.anshun_city_item, R.array.tongren_city_item,
			R.array.qingxinan_city_item, R.array.biji_city_item,
			R.array.qingdongnan_city_item, R.array.qingnan_city_item };
	private int[] countyOfYunNan = { R.array.kunming_city_item,
			R.array.qujing_city_item, R.array.yuexi_city_item,
			R.array.baoshan_city_item, R.array.zhaotong_city_item,
			R.array.lijiang_city_item, R.array.simao_city_item,
			R.array.lingcang_city_item, R.array.chuxiong_city_item,
			R.array.honghe_city_item, R.array.wenshan_city_item,
			R.array.xishuangbanna_city_item, R.array.dali_city_item,
			R.array.dehuang_city_item, R.array.nujiang_city_item,
			R.array.diqing_city_item };
	private int[] countyOfXiZang = { R.array.lasa_city_item,
			R.array.changdu_city_item, R.array.shannan_city_item,
			R.array.rgeze_city_item, R.array.naqu_city_item,
			R.array.ali_city_item, R.array.linzhi_city_item };

	private int[] countyOfShanXi2 = { R.array.xian_city_item,
			R.array.tongchuan_city_item, R.array.baoji_city_item,
			R.array.xianyang_city_item, R.array.weinan_city_item,
			R.array.yanan_city_item, R.array.hanzhong_city_item,
			R.array.yulin_city_item, R.array.ankang_city_item,
			R.array.shangluo_city_item };
	private int[] countyOfGanSu = { R.array.lanzhou_city_item,
			R.array.jiayuguan_city_item, R.array.jinchang_city_item,
			R.array.baiyin_city_item, R.array.tianshui_city_item,
			R.array.wuwei_city_item, R.array.zhangyue_city_item,
			R.array.pingliang_city_item, R.array.jiuquan_city_item,
			R.array.qingyang_city_item, R.array.dingxi_city_item,
			R.array.longnan_city_item, R.array.linxia_city_item,
			R.array.gannan_city_item };
	private int[] countyOfQingHai = { R.array.xining_city_item,
			R.array.haidong_city_item, R.array.haibai_city_item,
			R.array.huangnan_city_item, R.array.hainan_city_item,
			R.array.guluo_city_item, R.array.yushu_city_item,
			R.array.haixi_city_item };
	private int[] countyOfNingXia = { R.array.yinchuan_city_item,
			R.array.shizuishan_city_item, R.array.wuzhong_city_item,
			R.array.guyuan_city_item, R.array.zhongwei_city_item };
	private int[] countyOfXinJiang = { R.array.wulumuqi_city_item,
			R.array.kelamayi_city_item, R.array.tulyfan_city_item,
			R.array.hami_city_item, R.array.changji_city_item,
			R.array.boertala_city_item, R.array.bayinguolen_city_item,
			R.array.akesu_city_item, R.array.kemuleisu_city_item,
			R.array.geshen_city_item, R.array.hetian_city_item,
			R.array.yili_city_item, R.array.tacheng_city_item,
			R.array.aleitai_city_item, R.array.shihezi_city_item,
			R.array.alaer_city_item, R.array.tumushihe_city_item,
			R.array.wujiaqu_city_item };
	private int[] countyOfHongKong = {};
	private int[] countyOfAoMen = {};
	private int[] countyOfTaiWan = {};
	
	public static String[] images = new String[] { "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383264_3954.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383264_4787.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383264_8243.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383248_3693.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383243_5120.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383242_3127.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383242_9576.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383242_1721.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383219_5806.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383214_7794.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383213_4418.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383213_3557.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383210_8779.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383172_4577.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383166_3407.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383166_2224.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383166_7301.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383165_7197.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383150_8410.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383131_3736.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383130_5094.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383130_7393.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383129_8813.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383100_3554.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383093_7894.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383092_2432.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383092_3071.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383091_3119.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383059_6589.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383059_8814.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383059_2237.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383058_4330.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406383038_3602.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382942_3079.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382942_8125.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382942_4881.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382941_4559.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382941_3845.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382924_8955.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382923_2141.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382923_8437.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382922_6166.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382922_4843.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382905_5804.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382904_3362.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382904_2312.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382904_4960.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382900_2418.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382881_4490.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382881_5935.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382880_3865.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382880_4662.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382879_2553.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382862_5375.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382862_1748.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382861_7618.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382861_8606.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382861_8949.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382841_9821.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382840_6603.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382840_2405.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382840_6354.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382839_5779.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382810_7578.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382810_2436.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382809_3883.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382809_6269.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382808_4179.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382790_8326.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382789_7174.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382789_5170.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382789_4118.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382788_9532.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382767_3184.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382767_4772.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382766_4924.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382766_5762.jpg",
			"http://img.my.csdn.net/uploads/201407/26/1406382765_7341.jpg" };

}
