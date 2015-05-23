package com.goodfriends.personalchef.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.annotation.SuppressLint;
import android.util.Log;

import com.goodfriends.personalchef.bean.Address;
import com.goodfriends.personalchef.bean.AddressBean;
import com.goodfriends.personalchef.bean.ChefBean;
import com.goodfriends.personalchef.bean.ChefInfo;
import com.goodfriends.personalchef.bean.Chefs;
import com.goodfriends.personalchef.bean.CouPon;
import com.goodfriends.personalchef.bean.Dish;
import com.goodfriends.personalchef.bean.DishBean;
import com.goodfriends.personalchef.bean.DishInfo;
import com.goodfriends.personalchef.bean.Eval;
import com.goodfriends.personalchef.bean.EvalBean;
import com.goodfriends.personalchef.bean.Food;
import com.goodfriends.personalchef.bean.Order;
import com.goodfriends.personalchef.bean.OrderBean;
import com.goodfriends.personalchef.bean.OrderInfos;
import com.goodfriends.personalchef.bean.Pack;
import com.goodfriends.personalchef.bean.ShareInfo;
import com.goodfriends.personalchef.bean.UserInfo;
import com.umeng.socialize.net.utils.Base64;

public class CommonFun1 {

	/**
	 * 获取用户订单列表
	 * 
	 * @param userid
	 *            用户ID
	 * @param page
	 *            页数
	 * @param pagecount
	 *            单页返回量
	 * @return OrderBean
	 */
	public static OrderBean getOrderList(int userid, int page, int pagecount) {
		OrderBean orderBean = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		postParameters.add(new BasicNameValuePair("page", Integer
				.toString(page)));
		postParameters.add(new BasicNameValuePair("pagecount", Integer
				.toString(pagecount)));
		String result = Common.post(CommonUrl.ORDERLIST, postParameters);
		if (result != null) {// 返回数据不为空
			try {
				JSONObject jsonObject = new JSONObject(result);
				int errcode = jsonObject.getInt("errcode");
				String errmsg = jsonObject.getString("errmsg");
				if (errcode == 0) {
					OrderInfos orderInfos = null;
					JSONObject content = jsonObject.getJSONObject("content");
					if (!content.equals("")) {
						int countPage = content.getInt("countPage");
						int countRecord = content.getInt("countRecord");
						int currentPage = content.getInt("currentPage");
						int onePageCount = content.getInt("onePageCount");
						JSONArray list = content.getJSONArray("list");
						List<Order> orders = new ArrayList<Order>();
						for (int i = 0; i < list.length(); i++) {
							JSONObject object = list.getJSONObject(i);
							String masterheadpic = object
									.getString("masterheadpic");
							String mastername = object.getString("mastername");
							String orderno = object.getString("orderno");
							String packname = object.getString("packname");
							String servicedate = object
									.getString("servicedate");
							int masterid = object.getInt("masterid");
//							int ordertype = object.getInt("ordertype");//订单查询不到是因为解析ordertype出错，服务器没有给这个值
							int packid = object.getInt("packid");
							int status = object.getInt("status");
							int totaldishcount = object
									.getInt("totaldishcount");
							int totalfee = object.getInt("totalfee");
							Order order = new Order(masterheadpic, mastername,
									orderno, servicedate, packname, masterid,
									0, packid, status, totaldishcount,
									totalfee);
							orders.add(order);
						}
						orderInfos = new OrderInfos(countPage, countRecord,
								currentPage, onePageCount, orders);
						orderBean = new OrderBean(errcode, errmsg, orderInfos);
					} else {
						orderBean = new OrderBean(errcode, errmsg, orderInfos);
					}
				} else {
					orderBean = new OrderBean(errcode, errmsg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return orderBean;
			}
			return orderBean;
		} else {// 返回空
			return orderBean;
		}
	}

	/**
	 * 获取用户地址列表信息
	 * 
	 * @param userid
	 * @return AddressBean
	 */
	public static AddressBean getAddressList(int userid) {
		AddressBean addressBean = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		String result = Common.post(CommonUrl.ADDRESSLIST, postParameters);
		if (result != null) {// 返回数据不为空
			try {
				JSONObject jsonObject = new JSONObject(result);
				int errcode = jsonObject.getInt("errcode");
				String errmsg = jsonObject.getString("errmsg");
				if (errcode == 0) {
					JSONArray content = jsonObject.getJSONArray("content");
					List<Address> addresses = null;
					if (content.length() != 0) {
						addresses = new ArrayList<Address>();
						for (int i = 0; i < content.length(); i++) {
							JSONObject object = content.getJSONObject(i);
							String address = object.getString("address");
							String contact = object.getString("contact");
							String mobile = object.getString("mobile");
							int id = object.getInt("id");
							String province = object.getString("province");
							String city = object.getString("city");
							String area = object.getString("area");
							int state = object.getInt("state");
							Address address2 = new Address(address, contact,
									mobile, id, province, city, area, state);
							addresses.add(address2);
						}
						addressBean = new AddressBean(errcode, errmsg,
								addresses);
					} else {
						addressBean = new AddressBean(errcode, errmsg,
								addresses);
					}
				} else {
					addressBean = new AddressBean(errcode, errmsg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return addressBean;
			}
			return addressBean;
		} else {// 返回空
			return addressBean;
		}
	}

	/**
	 * 获取分类厨师列表
	 * 
	 * @param cookstyleid
	 * @param nativeplace
	 * @param distanceType
	 * @param dishcount
	 * @param page
	 * @param pagecount
	 * @param position
	 * @return ChefBean
	 */
	public static ChefBean getChefList(int cookstyleid, String nativeplace,
			int distanceType, int dishcount, int page, int pagecount,
			String position,String area) {
		Log.e("TAG", position);
		ChefBean chefBean = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("cookstyleid", Integer
				.toString(cookstyleid)));
		postParameters.add(new BasicNameValuePair("distanceType", Integer
				.toString(distanceType)));
		postParameters.add(new BasicNameValuePair("dishcount", Integer
				.toString(dishcount)));
		postParameters.add(new BasicNameValuePair("nativeplace", nativeplace));
		postParameters.add(new BasicNameValuePair("page", Integer
				.toString(page)));
		postParameters.add(new BasicNameValuePair("pagecount", Integer
				.toString(pagecount)));
		postParameters.add(new BasicNameValuePair("position", position));
		postParameters.add(new BasicNameValuePair("city", "深圳市"));
		postParameters.add(new BasicNameValuePair("area", area));
		String result = Common.post(CommonUrl.CHEFLIST, postParameters);
		if (result != null) {// 返回数据不为空
			try {
				JSONObject jsonObject = new JSONObject(result);
				int errcode = jsonObject.getInt("errcode");
				String errmsg = jsonObject.getString("errmsg");
				if (errcode == 0) {
					ChefInfo chefInfo = null;
					JSONObject content = jsonObject.getJSONObject("content");
					if (!content.equals("")) {
						int countPage = content.getInt("countPage");
						int countRecord = content.getInt("countRecord");
						int currentPage = content.getInt("currentPage");
						int onePageCount = content.getInt("onePageCount");
						JSONArray list = content.getJSONArray("list");
						List<Chefs> chefs = new ArrayList<Chefs>();
						for (int i = 0; i < list.length(); i++) {
							JSONObject object = list.getJSONObject(i);
							String headpicurl = object.getString("headpicurl");
							String intro = object.getString("intro");
							String name = object.getString("name");
							String nativeplace1 = object
									.getString("nativeplace");
							String cookstyles = object.getString("cookstyles");
							int distance = object.getInt("distance");
							int evalcount = object.getInt("evalcount");
							int grade = object.getInt("grade");
							int id = object.getInt("masterid");
							int isauth = object.getInt("isauth");
							int servicecount = object.getInt("servicecount");
							int age = object.getInt("age");
							int score = object.getInt("score");
							JSONArray array = object.getJSONArray("dishes");
							List<Dish> dishs = new ArrayList<Dish>();
							for (int j = 0; j < array.length(); j++) {
								JSONObject object2 = array.getJSONObject(j);
								String imgurl = object2.getString("imgurl");
								int dishid = object2.getInt("dishid");
								Dish dish = new Dish(imgurl, dishid);
								dishs.add(dish);
							}
							Chefs chef = new Chefs(headpicurl, intro, name,
									nativeplace1, evalcount, grade, id, isauth,
									servicecount, age, dishs, score, cookstyles,distance);
							chefs.add(chef);
						}
						chefInfo = new ChefInfo(countPage, countRecord,
								currentPage, onePageCount, chefs);
						chefBean = new ChefBean(errcode, errmsg, chefInfo);
					} else {
						chefBean = new ChefBean(errcode, errmsg, chefInfo);
					}
				} else {
					chefBean = new ChefBean(errcode, errmsg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			return chefBean;
		} else {// 返回空
			return chefBean;
		}
	}

	/**
	 * 获取分类菜品列表
	 * 
	 * @param cookstyleid
	 * @param food
	 * @param page
	 * @param pagecount
	 * @return DishBean
	 */
	public static DishBean getDishList(int cookstyleid, String food, int page,
			int pagecount) {
		DishBean dishBean = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("cookstyleid", Integer
				.toString(cookstyleid)));
		postParameters.add(new BasicNameValuePair("food", food));
		postParameters.add(new BasicNameValuePair("page", Integer
				.toString(page)));
		postParameters.add(new BasicNameValuePair("pagecount", Integer
				.toString(pagecount)));
		String result = Common.post(CommonUrl.DISHLIST, postParameters);
		if (result != null) {// 返回数据不为空
			try {
				JSONObject jsonObject = new JSONObject(result);
				int errcode = jsonObject.getInt("errcode");
				String errmsg = jsonObject.getString("errmsg");
				if (errcode == 0) {
					DishInfo dishInfo = null;
					JSONObject content = jsonObject.getJSONObject("content");
					if (!content.equals("")) {
						int countPage = content.getInt("countPage");
						int countRecord = content.getInt("countRecord");
						int currentPage = content.getInt("currentPage");
						int onePageCount = content.getInt("onePageCount");
						JSONArray list = content.getJSONArray("list");
						List<Dish> dishs = new ArrayList<Dish>();
						for (int i = 0; i < list.length(); i++) {
							JSONObject object = list.getJSONObject(i);
							String imgurl = object.getString("imgurl");
							String desc = object.getString("desc");
							String name = object.getString("name");
							String smallimgurl = object
									.getString("smallimgurl");
							int ordercount = object.getInt("ordercount");
							int dishid = object.getInt("dishid");
							JSONArray array = object.getJSONArray("masters");
							List<Chefs> chefs = null;
							if (array != null) {
								chefs = new ArrayList<Chefs>();
								for (int j = 0; j < array.length(); j++) {
									JSONObject object2 = array.getJSONObject(j);
									String headpicurl = object2
											.getString("headpicurl");
									int masterid = object2.getInt("masterid");
									Chefs chef = new Chefs(headpicurl, masterid);
									chefs.add(chef);
								}
							}
							Dish dish = new Dish(imgurl, desc, dishid, name,
									smallimgurl, ordercount, chefs);
							dishs.add(dish);
						}
						dishInfo = new DishInfo(countPage, countRecord,
								currentPage, onePageCount, dishs);
						dishBean = new DishBean(errcode, errmsg, dishInfo);
					} else {
						dishBean = new DishBean(errcode, errmsg, dishInfo);
					}
				} else {
					dishBean = new DishBean(errcode, errmsg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			return dishBean;
		} else {// 返回空
			return dishBean;
		}
	}

	/**
	 * 获取收藏厨师列表信息
	 * 
	 * @param userId
	 * @param page
	 * @param pagecount
	 * @param dishcount
	 * @return
	 */
	public static ChefBean getCollectChefList(int userId, int page,
			int pagecount, int dishcount) {
		ChefBean chefBean = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("page", Integer
				.toString(page)));
		postParameters.add(new BasicNameValuePair("pagecount", Integer
				.toString(pagecount)));
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userId)));
		postParameters.add(new BasicNameValuePair("dishcount", Integer
				.toString(dishcount)));
		String result = Common.post(CommonUrl.COLLECTCHEFLIST, postParameters);
		if (result != null) {// 返回数据不为空
			try {
				JSONObject jsonObject = new JSONObject(result);
				int errcode = jsonObject.getInt("errcode");
				String errmsg = jsonObject.getString("errmsg");
				if (errcode == 0) {
					ChefInfo chefInfo = null;
					JSONObject content = jsonObject.getJSONObject("content");
					if (!content.equals("")) {
						int countPage = content.getInt("countPage");
						int countRecord = content.getInt("countRecord");
						int currentPage = content.getInt("currentPage");
						int onePageCount = content.getInt("onePageCount");
						JSONArray list = content.getJSONArray("list");
						List<Chefs> chefs = new ArrayList<Chefs>();
						for (int i = 0; i < list.length(); i++) {
							JSONObject object = list.getJSONObject(i);
							String headpicurl = object.getString("headpicurl");
							String intro = object.getString("intro");
							String name = object.getString("name");
							String nativeplace1 = object
									.getString("nativeplace");
							String cookstyles = object.getString("cookstyles");
							int distance = object.getInt("distance");
							int evalcount = object.getInt("evalcount");
							int grade = object.getInt("grade");
							int id = object.getInt("masterid");
							int isauth = object.getInt("isauth");
							int servicecount = object.getInt("servicecount");
							int age = object.getInt("age");
							int score = object.getInt("score");
							JSONArray array = object.getJSONArray("dishes");
							List<Dish> dishs = new ArrayList<Dish>();
							for (int j = 0; j < array.length(); j++) {
								JSONObject object2 = array.getJSONObject(j);
								String imgurl = object2.getString("imgurl");
								int dishid = object2.getInt("dishid");
								Dish dish = new Dish(imgurl, dishid);
								dishs.add(dish);
							}
							Chefs chef = new Chefs(headpicurl, intro, name,
									nativeplace1, evalcount, grade, id, isauth,
									servicecount, age, dishs, score, cookstyles,distance);
							chefs.add(chef);
						}
						chefInfo = new ChefInfo(countPage, countRecord,
								currentPage, onePageCount, chefs);
						chefBean = new ChefBean(errcode, errmsg, chefInfo);
					} else {
						chefBean = new ChefBean(errcode, errmsg, chefInfo);
					}
				} else {
					chefBean = new ChefBean(errcode, errmsg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return chefBean;
			}
			return chefBean;
		} else {// 返回空
			return chefBean;
		}
	}

	/**
	 * 获取菜品厨师列表
	 * 
	 * @param dishid
	 * @param page
	 * @param pagecount
	 * @return ChefBean
	 */
	public static ChefBean getChefListFromDish(int dishid, int page,
			int pagecount) {
		ChefBean chefBean = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("page", Integer
				.toString(page)));
		postParameters.add(new BasicNameValuePair("pagecount", Integer
				.toString(pagecount)));
		postParameters.add(new BasicNameValuePair("dishid", Integer
				.toString(dishid)));
		String result = Common.post(CommonUrl.CHEFLISTFROMDISH, postParameters);
		if (result != null) {// 返回数据不为空
			try {
				JSONObject jsonObject = new JSONObject(result);
				int errcode = jsonObject.getInt("errcode");
				String errmsg = jsonObject.getString("errmsg");
				if (errcode == 0) {
					ChefInfo chefInfo = null;
					JSONObject content = jsonObject.getJSONObject("content");
					if (!content.equals("")) {
						int countPage = content.getInt("countPage");
						int countRecord = content.getInt("countRecord");
						int currentPage = content.getInt("currentPage");
						int onePageCount = content.getInt("onePageCount");
						JSONArray list = content.getJSONArray("list");
						List<Chefs> chefs = new ArrayList<Chefs>();
						for (int i = 0; i < list.length(); i++) {
							JSONObject object = list.getJSONObject(i);
							String headpicurl = object.getString("headpicurl");
							String intro = object.getString("intro");
							String name = object.getString("name");
							String nativeplace1 = object
									.getString("nativeplace");
							String cookstyles = object.getString("cookstyles");
							int distance = object.getInt("distance");
							int evalcount = object.getInt("evalcount");
							int grade = object.getInt("grade");
							int id = object.getInt("masterid");
							int isauth = object.getInt("isauth");
							int servicecount = object.getInt("servicecount");
							int age = object.getInt("age");
							int score = object.getInt("score");
							List<Dish> dishs = null;
							Chefs chef = new Chefs(headpicurl, intro, name,
									nativeplace1, evalcount, grade, id, isauth,
									servicecount, age, dishs, score, cookstyles,distance);
							chefs.add(chef);
						}
						chefInfo = new ChefInfo(countPage, countRecord,
								currentPage, onePageCount, chefs);
						chefBean = new ChefBean(errcode, errmsg, chefInfo);
					} else {
						chefBean = new ChefBean(errcode, errmsg, chefInfo);
					}
				} else {
					chefBean = new ChefBean(errcode, errmsg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return chefBean;
			}
			return chefBean;
		} else {// 返回空
			return chefBean;
		}
	}

	/**
	 * 检查是否已收藏该厨师
	 * 
	 * @param userid
	 * @param masterid
	 * @return boolean
	 */
	public static boolean chefIsCollect(int userid, int masterid) {
		boolean b = false;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		postParameters.add(new BasicNameValuePair("masterid", Integer
				.toString(masterid)));
		String result = Common.post(CommonUrl.CHEFISCOLLECT, postParameters);
		if (result != null) {
			try {
				JSONObject object = new JSONObject(result);
				if (object.getInt("errcode") == 201) {
					b = true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}

	/**
	 * 检查是否已收藏菜品
	 * 
	 * @param userid
	 * @param dishid
	 * @return boolean
	 */
	public static boolean dishIsCollect(int userid, int dishid) {
		boolean b = false;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		postParameters.add(new BasicNameValuePair("dishid", Integer
				.toString(dishid)));
		String result = Common.post(CommonUrl.DISHISCOLLECT, postParameters);
		if (result != null) {
			try {
				JSONObject object = new JSONObject(result);
				if (object.getInt("errcode") == 201) {
					b = true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}

	/**
	 * 注册接口
	 * 
	 * @param phone
	 * @param identifycode
	 * @return UserInfo
	 */
	public static UserInfo reg(String phone, String identifycode) {
		UserInfo info = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("mobile", phone));
		postParameters
				.add(new BasicNameValuePair("identifycode", identifycode));
		String result = Common.post(CommonUrl.REG, postParameters);
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				int errcode = jsonObject.getInt("errcode");
				String errmsg = jsonObject.getString("errmsg");
				if (jsonObject.getInt("errcode") == 0) {
					JSONObject object = jsonObject.getJSONObject("content");
					String headpicurl = object.getString("headpicurl");
					String mobile = object.getString("mobile");
					String name = object.getString("name");
					int userid = object.getInt("userid");
					info = new UserInfo(headpicurl, mobile, name, userid,
							errcode, errmsg);
				} else {
					info = new UserInfo(errcode, errmsg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return info;
	}

	/**
	 * 获取厨师菜品列表
	 * 
	 * @param masterid
	 * @param dishcount
	 * @return List<Dish>
	 */
	public static List<Dish> getDishListFromchef(int masterid, int dishcount) {
		List<Dish> list_dishs = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("masterid", Integer
				.toString(masterid)));
		postParameters.add(new BasicNameValuePair("dishcount", Integer
				.toString(dishcount)));
		String result = Common.post(CommonUrl.DISHFROMCHEF, postParameters);
		if (result != null) {
			System.out.println(result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getInt("errcode") == 0) {
					list_dishs = new ArrayList<Dish>();
					JSONArray jsonArray = jsonObject.getJSONArray("content");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.getJSONObject(i);
						String desc = object.getString("desc");
						String name = object.getString("name");
						String imgurl = object.getString("imgurl");
						int dishid = object.getInt("dishid");
						int ordercount = object.getInt("ordercount");
						Dish dish = new Dish(imgurl, desc, dishid, name, "",
								ordercount);
						list_dishs.add(dish);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list_dishs;
	}

	/**
	 * 获取订单详情
	 * 
	 * @param orderno
	 * @return OrderBean
	 */
	@SuppressLint("NewApi")
	public static OrderBean getOrderDeatail(String orderno) {
		OrderBean orderBean = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("orderno", orderno));
		String result = Common.post(CommonUrl.ORDERDEATAIL, postParameters);
		Log.e("result", result);
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				int errcode = jsonObject.getInt("errcode");
				String errmsg = jsonObject.getString("errmsg");
				if (errcode == 0) {
					JSONObject object = jsonObject.getJSONObject("content");
					int bringcond = object.getInt("bringcond");
					int bringfood = object.getInt("bringfood");
					//后台没有传custdishcount给手机
//					int custdishcount = object.getInt("custdishcount");
					int status = object.getInt("status");
					//后台没有传orderType给手机
//					int orderType = object.getInt("orderType");
					int totaldishcount = object.getInt("totaldishcount");
					int totalfee = object.getInt("totalfee");
					String remark = object.getString("remark");
					String servicedate = object.getString("servicedate");
					Order order = new Order(remark, servicedate, 0,
							status, totaldishcount, totalfee, bringcond,
							bringfood, 1);
					JSONObject master = object.getJSONObject("master");
					int age = master.getInt("age");
					int evalcount = master.getInt("evalcount");
					int grade = master.getInt("grade");
					int isauth = master.getInt("isauth");
					int masterid = master.getInt("masterid");
					int score = master.getInt("score");
					int servicecount = master.getInt("servicecount");
					String headpicurl = master.getString("headpicurl");
					String name = master.getString("name");
					String nativeplace = master.getString("nativeplace");
					String intro = master.getString("intro");
					String mobile = master.getString("mobile");
					String cookstyles = master.getString("cookstyles");
					Chefs chefs = new Chefs(headpicurl, intro, name,
							nativeplace, evalcount, grade, masterid, isauth,
							servicecount, age, null, score, cookstyles, mobile);
					Address address2 = null;
					String detailaddress = object.getString("detailaddress");
					String contactmobile = object.getString("contactmobile");
					String contact = object.getString("contact");
					address2 = new Address(detailaddress, contact,
							contactmobile);
					JSONObject pack = object.getJSONObject("pack");
					String desc = pack.getString("desc");
					String name1 = pack.getString("name");
					int price = pack.getInt("price");
					List<Dish> dishs = null;
					JSONArray dishes = object.getJSONArray("dishes");
					if (dishes.length() > 0) {
						dishs = new ArrayList<Dish>();
						for (int i = 0; i < dishes.length(); i++) {
							JSONObject object2 = dishes.getJSONObject(i);
							String imgurl = object2.getString("imgurl");
							String name2 = object2.getString("name");
							Dish dish = new Dish(imgurl, "", 0, name2, "", 0);
							dishs.add(dish);
						}
					}
					List<Food> foods = null;
					JSONArray foodList = object.getJSONArray("foodList");
					if (foodList.length() > 0) {
						foods = new ArrayList<Food>();
						for (int i = 0; i < foodList.length(); i++) {
							JSONObject object2 = foodList.getJSONObject(i);
							String foodname = object2.getString("foodname");
							String weight = object2.getString("weight");
							Food food = new Food(foodname, weight);
							foods.add(food);
						}
					}
					Pack pack2 = new Pack(desc, name1, 0, 0, 0, price, 0);
					orderBean = new OrderBean(errcode, errmsg, order, chefs,
							address2, pack2, dishs, foods);
				} else {
					orderBean = new OrderBean(errcode, errmsg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return orderBean;
		} else {
			return orderBean;
		}
	}

	/**
	 * 取消订单接口
	 * 
	 * @param orderno
	 * @param userid
	 * @param reason
	 * @return OrderBean
	 */
	public static OrderBean cancleOrder(String orderno, int userid,
			String reason) {
		OrderBean orderBean = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("orderno", orderno));
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		postParameters.add(new BasicNameValuePair("reason", reason));
		String result = Common.post(CommonUrl.CANCELORDER, postParameters);
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				int errcode = jsonObject.getInt("errcode");
				String errmsg = jsonObject.getString("errmsg");
				orderBean = new OrderBean(errcode, errmsg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return orderBean;
	}

	/**
	 * 获取评价列表信息
	 * 
	 * @param masterid
	 * @param page
	 * @param pagecount
	 * @return EvalBean
	 */
	public static EvalBean getEvalList(int masterid, int page, int pagecount) {
		EvalBean evalBean = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("masterid", Integer
				.toString(masterid)));
		postParameters.add(new BasicNameValuePair("page", Integer
				.toString(page)));
		postParameters.add(new BasicNameValuePair("pagecount", Integer
				.toString(pagecount)));
		String result = Common.post(CommonUrl.EVALLIST, postParameters);
		if (result != null) {
			System.out.println(result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				int errcode = jsonObject.getInt("errcode");
				String errmsg = jsonObject.getString("errmsg");
				if (errcode == 0) {
					JSONObject content = jsonObject.getJSONObject("content");
					List<Eval> evals = null;
					if (content.getJSONArray("list") != null) {
						JSONArray list = content.getJSONArray("list");
						evals = new ArrayList<Eval>();
						for (int i = 0; i < list.length(); i++) {
							JSONObject object = list.getJSONObject(i);
							String content1 = object.getString("content");
							String evaldate = object.getString("evaldate");
							String userheadpic = object
									.getString("userheadpic");
							String username = object.getString("username");
							int score = object.getInt("score");
							Eval eval = new Eval(score, evaldate, content1,
									userheadpic, username);
							evals.add(eval);
						}
						evalBean = new EvalBean(errcode, errmsg, evals);
					} else {
						evalBean = new EvalBean(errcode, errmsg, evals);
					}
				} else {
					evalBean = new EvalBean(errcode, errmsg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return evalBean;
	}

	/**
	 * 评价订单
	 * 
	 * @param orderno
	 * @param userid
	 * @param masterid
	 * @param attitudescore
	 * @param delicisscore
	 * @param majorscore
	 * @param content
	 */
	public static boolean evalOrder(String orderno, int userid, int masterid,
			int attitudescore, int delicisscore, int majorscore, String content,String fileData) {
		boolean b = false;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("orderno", orderno));
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		postParameters.add(new BasicNameValuePair("masterid", Integer
				.toString(masterid)));
		postParameters.add(new BasicNameValuePair("attitudescore", Integer
				.toString(attitudescore)));
		postParameters.add(new BasicNameValuePair("delicisscore", Integer
				.toString(delicisscore)));
		postParameters.add(new BasicNameValuePair("majorscore", Integer
				.toString(majorscore)));
		postParameters.add(new BasicNameValuePair("content", content));
		postParameters.add(new BasicNameValuePair("pic", fileData));
		String result = Common.post(CommonUrl.EVALORDER, postParameters);
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.getInt("errcode") == 0) {
				b = true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 获取分享内容
	 * 
	 * @return
	 */
	public static ShareInfo getShareInfo() {
		ShareInfo shareInfo = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		String result = Common.post(CommonUrl.SHARE, postParameters);
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getInt("errcode") == 0) {
					shareInfo = new ShareInfo();
					JSONObject object = jsonObject.getJSONObject("content");
					shareInfo.setContent(object.getString("content"));
					shareInfo.setDownloadurl(object.getString("downloadurl"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
		}
		return shareInfo;
	}

	/**
	 * 取消厨师收藏
	 * 
	 * @param userid
	 * @param masterid
	 * @return boolean
	 */
	public static boolean delCollectChef(int userid, int masterid) {
		boolean b = false;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		postParameters.add(new BasicNameValuePair("masterid", Integer
				.toString(masterid)));
		String result = Common.post(CommonUrl.DELCOLLECTCHEF, postParameters);
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getInt("errcode") == 0) {
					b = true;
				} else {
					b = false;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}

	/**
	 * 取消菜品收藏
	 * 
	 * @param userid
	 * @param dishid
	 * @return boolean
	 */
	public static boolean delCollectDish(int userid, int dishid) {
		boolean b = false;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		postParameters.add(new BasicNameValuePair("dishid", Integer
				.toString(dishid)));
		String result = Common.post(CommonUrl.DELCOLLECTDISH, postParameters);
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);

				if (jsonObject.getInt("errcode") == 0) {
					b = true;
				} else {
					b = false;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}

	public static List<CouPon> getCouponList(int userid) {

		List<CouPon> couPons = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		String result = Common.post(CommonUrl.COUPONLIST, postParameters);
		if (result != null) {
			System.out.println(result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				JSONObject content = jsonObject.getJSONObject("content");
				JSONArray effectives = content.getJSONArray("effectives");
				JSONArray invalids = content.getJSONArray("invalids");
				if (effectives.length() > 0) {
					couPons = new ArrayList<CouPon>();
					for (int i = 0; i < effectives.length(); i++) {
						JSONObject object = effectives.getJSONObject(i);
						String couponName = object.getString("couponName");
						String description = object.getString("description");
						String endTime = object.getString("endTime");
						String fromTime = object.getString("fromTime");
						String getTime = object.getString("getTime");
						int couponid = object.getInt("couponid");
						int status = object.getInt("status");
						int userCouponId = object.getInt("userCouponId");
						int value = object.getInt("value");
						CouPon couPon = new CouPon(couponName, endTime,
								getTime, fromTime, couponid, status, value,
								userCouponId);
						couPons.add(couPon);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return couPons;
	}

	public static void notifyShare(int userid, int channel, String content) {
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		postParameters.add(new BasicNameValuePair("channel", Integer
				.toString(channel)));
		postParameters.add(new BasicNameValuePair("content", content));
		String result = Common.post(CommonUrl.SHARECALLBACK, postParameters);
		Log.e("分享回调", result + "");
	}
	
	/**
	 * 搜索厨师列表
	 * 
	 * @param cookstyleid
	 * @param nativeplace
	 * @param distanceType
	 * @param dishcount
	 * @param page
	 * @param pagecount
	 * @param position
	 * @return ChefBean
	 */
	public static ChefBean searchChefList(String key, int dishcount,
			int page, int pagecount,String position) {
		ChefBean chefBean = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("key", key));
		postParameters.add(new BasicNameValuePair("dishcount", Integer
				.toString(dishcount)));
		postParameters.add(new BasicNameValuePair("page", Integer
				.toString(page)));
		postParameters.add(new BasicNameValuePair("pagecount", Integer
				.toString(pagecount)));
		postParameters.add(new BasicNameValuePair("position", position));
		String result = Common.post(CommonUrl.SEARCHCHEFLIST, postParameters);
		Log.e("result", result);
		if (result != null) {// 返回数据不为空
			try {
				JSONObject jsonObject = new JSONObject(result);
				int errcode = jsonObject.getInt("errcode");
				String errmsg = jsonObject.getString("errmsg");
				if (errcode == 0) {
					ChefInfo chefInfo = null;
					JSONObject content = jsonObject.getJSONObject("content");
					if (!content.equals("")) {
						int countPage = content.getInt("countPage");
						int countRecord = content.getInt("countRecord");
						int currentPage = content.getInt("currentPage");
						int onePageCount = content.getInt("onePageCount");
						JSONArray list = content.getJSONArray("list");
						List<Chefs> chefs = new ArrayList<Chefs>();
						for (int i = 0; i < list.length(); i++) {
							JSONObject object = list.getJSONObject(i);
							String headpicurl = object.getString("headpicurl");
							String intro = object.getString("intro");
							String name = object.getString("name");
							String nativeplace1 = object
									.getString("nativeplace");
							String cookstyles = object.getString("cookstyles");
							int distance = object.getInt("distance");
							int evalcount = object.getInt("evalcount");
							int grade = object.getInt("grade");
							int id = object.getInt("masterid");
							int isauth = object.getInt("isauth");
							int servicecount = object.getInt("servicecount");
							int age = object.getInt("age");
							int score = object.getInt("score");
							JSONArray array = object.getJSONArray("dishes");
							List<Dish> dishs = new ArrayList<Dish>();
							for (int j = 0; j < array.length(); j++) {
								JSONObject object2 = array.getJSONObject(j);
								String imgurl = object2.getString("imgurl");
								int dishid = object2.getInt("dishid");
								Dish dish = new Dish(imgurl, dishid);
								dishs.add(dish);
							}
							Chefs chef = new Chefs(headpicurl, intro, name,
									nativeplace1, evalcount, grade, id, isauth,
									servicecount, age, dishs, score, cookstyles,distance);
							chefs.add(chef);
						}
						chefInfo = new ChefInfo(countPage, countRecord,
								currentPage, onePageCount, chefs);
						chefBean = new ChefBean(errcode, errmsg, chefInfo);
					} else {
						chefBean = new ChefBean(errcode, errmsg, chefInfo);
					}
				} else {
					chefBean = new ChefBean(errcode, errmsg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			return chefBean;
		} else {// 返回空
			return chefBean;
		}
	}
}