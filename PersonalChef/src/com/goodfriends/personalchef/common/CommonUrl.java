package com.goodfriends.personalchef.common;

public class CommonUrl {
	/**
	 * 服务器ip地址
	 */
	public static final String SERVER = "http://m.schu.cc/app/";

	/**
	 * 分享下载地址
	 */
	public static final String SHAREURL = "http://app.schu.cc//attached//apk//schu_1.0.apk";

	/**
	 * 获取用户订单列表地址
	 */
	public static final String ORDERLIST = SERVER + "order/queryOrderList";

	/**
	 * 获取用户地址信息列表地址
	 */
	public static final String ADDRESSLIST = SERVER + "user/queryUserAddrs";

	/**
	 * 获取分类厨师列表地址
	 */
	public static final String CHEFLIST = SERVER + "master/queryMasters";
	
	/**
	 * 搜索厨师列表地址
	 */
	public static final String SEARCHCHEFLIST = SERVER + "master/searchMaster";

	/**
	 * 获取分类菜品列表地址
	 */
	public static final String DISHLIST = SERVER + "dish/queryDishList";

	/**
	 * 查询收藏厨师列表
	 */
	public static final String COLLECTCHEFLIST = SERVER
			+ "user/queryUserMasterCollects";

	/**
	 * 查询菜品厨师列表
	 */
	public static final String CHEFLISTFROMDISH = SERVER
			+ "dish/queryDishMasters";

	/**
	 * 检查是否已收藏该厨师
	 */
	public static final String CHEFISCOLLECT = SERVER
			+ "user/checkAlreadyCollectMaster";

	/**
	 * 检查是否已收藏该菜品
	 */
	public static final String DISHISCOLLECT = SERVER
			+ "user/checkAlreadyCollectDish";

	/**
	 * 一键登录
	 */
	public static final String REG = SERVER + "user/userDirectLogin";

	/**
	 * 厨师菜品列表
	 */
	public static final String DISHFROMCHEF = SERVER
			+ "master/queryMasterDishes";

	/**
	 * 订单详情
	 */
	public static final String ORDERDEATAIL = SERVER + "order/queryOrderDetail";

	/**
	 * 初始化订单
	 */
	public static final String INITORDER = SERVER + "order/initOrder";

	/**
	 * 取消订单
	 */
	public static final String CANCELORDER = SERVER + "order/cancelOrder";

	/**
	 * 评价订单
	 */
	public static final String EVALORDER = SERVER + "order/evalOrder";

	/**
	 * 查询厨师评论列表
	 */
	public static final String EVALLIST = SERVER + "master/queryMasterEval";

	/**
	 * 获取分享信息
	 */
	public static final String SHARE = SERVER + "common/initShareInfo";

	/**
	 * 取消厨师收藏
	 */
	public static final String DELCOLLECTCHEF = SERVER
			+ "user/delUserMasterCollect";

	/**
	 * 取消菜品收藏
	 */
	public static final String DELCOLLECTDISH = SERVER
			+ "user/delUserDishCollect";

	/**
	 * 获取优惠券列表信息
	 */
	public static final String COUPONLIST = SERVER + "user/queryUserCoupon";

	/**
	 * 分享回调接口
	 */
	public static final String SHARECALLBACK = SERVER + "common/notifyShare";
	
	
	public static final String APP_SERVER = "http://m.schu.cc";
	
	/**
	 * 区域接口
	 */
	public static final String GETDISTINCT = APP_SERVER + "/app/common/queryArea";
}
