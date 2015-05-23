package com.goodfriends.personalchef.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.goodfriends.personalchef.bean.Advs;
import com.goodfriends.personalchef.bean.Caixi;
import com.goodfriends.personalchef.bean.Chefs;
import com.goodfriends.personalchef.bean.Dish;
import com.goodfriends.personalchef.bean.IndexFun;
import com.goodfriends.personalchef.bean.InitOrder;
import com.goodfriends.personalchef.bean.Pack;
import com.goodfriends.personalchef.bean.ServiceTime;
import com.goodfriends.personalchef.bean.Times;

public class CommonFun {

	/**
	 * 首页广告接口url
	 */
	private static final String advurl =  CommonUrl.SERVER + "common/queryAds";

	/**
	 * 首页菜品接口url
	 */
	private static final String dishurl = CommonUrl.SERVER + "dish/queryRecommendDishes";

	/**
	 * 分类菜系列表url
	 */
	private static final String caixiurl = CommonUrl.SERVER + "common/queryAllCookStyle";

	/**
	 * 分类厨师籍贯列表url
	 */
	private static final String jiguanurl = CommonUrl.SERVER + "common/queryNativePlace";
	
	/**
	 * 获取食材列表
	 */
	private static final String foodsurl = CommonUrl.SERVER + "common/queryCommonFood";
	/**
	 * 分类菜品列表url
	 */
	private static final String categorychefurl = CommonUrl.SERVER + "dish/queryDishList";

	/**
	 * 厨师详细信息url
	 */
	private static final String chefdetailurl = CommonUrl.SERVER + "master/queryMasterDetail";

	/**
	 * 收藏厨师url
	 */
	private static final String collectchefurl = CommonUrl.SERVER + "user/collectMaster";

	/**
	 * 意见反馈url
	 */
	private static final String feedbackurl = CommonUrl.SERVER + "common/submitFeedback";

	/**
	 * 查询厨师评论列表接口url
	 */
	private static final String pinglunurl = CommonUrl.SERVER + "master/queryMasterEval";

	/**
	 * 发送验证码
	 */
	private static final String msgurl = CommonUrl.SERVER + "user/sendDirectLoginSms";

	/**
	 * 获取首页广告列表
	 * 
	 * @return
	 */
	public static List<Advs> getAdv(Context context) {
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		List<Advs> list_advs = null;
		String result = Common.post(advurl, postParameters);
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getInt("errcode") == 0) {
					list_advs = new ArrayList<Advs>();
					JSONArray jsonArray = jsonObject.getJSONArray("content");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.getJSONObject(i);
						String imgurl = object.getString("imgurl");
						String url = object.getString("url");
						Advs advs = new Advs(imgurl, url);
						list_advs.add(advs);
					}
				} else {
					Toast.makeText(context, jsonObject.getString("errmsg"),
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list_advs;
	}

	/**
	 * 获取首页推荐菜品列表
	 * 
	 * @param context
	 * @param page
	 * @param pagecount
	 * @return
	 */
	public static List<Dish> getDish(Context context, int page, int pagecount) {
		List<Dish> list_dishs = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("page", Integer
				.toString(page)));
		postParameters.add(new BasicNameValuePair("pagecount", Integer
				.toString(pagecount)));
		String result = Common.post(dishurl, postParameters);

		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getInt("errcode") == 0) {
					list_dishs = new ArrayList<Dish>();
					JSONArray jsonArray = jsonObject.getJSONArray("content");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.getJSONObject(i);
						String bigimgurl = object.getString("bigimgurl");
						String desc = object.getString("desc");
						String name = object.getString("name");
						String smallimgurl = object.getString("smallimgurl");
						int dishid = object.getInt("dishid");
						String imgurl = object.getString("imgurl");
						Dish dish = new Dish(bigimgurl, desc, dishid, name,
								smallimgurl, imgurl);
						list_dishs.add(dish);
					}
				} else {
					Toast.makeText(context, jsonObject.getString("errmsg"),
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list_dishs;
	}

	/**
	 * 获取分类菜品列表
	 * 
	 * @param context
	 * @param page
	 * @param pagecount
	 * @return
	 */
	public static List<Dish> getCategoryDish(Context context, int cookstyleid,
			String food, int page, int pagecount) {
		List<Dish> list_dishs = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("cookstyleid", Integer
				.toString(cookstyleid)));
		postParameters.add(new BasicNameValuePair("food", food));
		postParameters.add(new BasicNameValuePair("page", Integer
				.toString(page)));
		postParameters.add(new BasicNameValuePair("pagecount", Integer
				.toString(pagecount)));

		String result = Common.post(categorychefurl, postParameters);
		if (result != null) {
			try {
				JSONObject jsonObject1 = new JSONObject(result);
				JSONObject jsonObject = jsonObject1.getJSONObject("content");
				if (jsonObject1.getInt("errcode") == 0) {
					list_dishs = new ArrayList<Dish>();
					JSONArray jsonArray = jsonObject.getJSONArray("list");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.getJSONObject(i);
						String bigimgurl = object.getString("bigimgurl");
						String desc = object.getString("desc");
						String name = object.getString("name");
						String smallimgurl = object.getString("smallimgurl");
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
						Dish dish = new Dish(bigimgurl, desc, dishid, name,
								smallimgurl, ordercount, chefs);
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
	 * 获取厨师详细信息
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	public static Chefs getChefDetail(Context context, int id,String position) {
		Chefs chefs = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("masterid", Integer
				.toString(id)));
		postParameters.add(new BasicNameValuePair("position", position));
		String result = Common.post(chefdetailurl, postParameters);
		if (result != null) {
			System.out.println(result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getInt("errcode") == 0) {
					JSONObject object = jsonObject.getJSONObject("content");
					String headpicurl = object.getString("headpicurl");
					String intro = object.getString("intro");
					String name = object.getString("name");
					String nativeplace = object.getString("nativeplace");
					int score = object.getInt("score");
					int evalcount = object.getInt("evalcount");
					int grade = object.getInt("grade");
					int chefid = object.getInt("masterid");
					int isauth = object.getInt("isauth");
					int age = object.getInt("age");
					int servicecount = object.getInt("servicecount");
					int distance = object.getInt("distance");
					JSONArray pack = object.getJSONArray("packs");
					List<Pack> packs = null;
					if (pack != null) {
						packs = new ArrayList<Pack>();
						for (int i = 0; i < pack.length(); i++) {
							JSONObject object2 = pack.getJSONObject(i);
							String desc = object2.getString("desc");
							String name1 = object2.getString("name");
							int fromcount = object2.getInt("fromcount");
							int id1 = object2.getInt("id");
							int mastergrade = object2.getInt("mastergrade");
							int price = object2.getInt("price");
							int tocount = object2.getInt("tocount");
							Pack pack1 = new Pack(desc, name1, fromcount, id1,
									mastergrade, price, tocount);
							packs.add(pack1);
						}
					}
					chefs = new Chefs(headpicurl, intro, name, nativeplace,
							evalcount, grade, chefid, isauth, servicecount,
							null, packs, age, score,distance);
				} else {
					Toast.makeText(context, jsonObject.getString("errmsg"),
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Toast.makeText(context, "shit", Toast.LENGTH_SHORT).show();
		}
		return chefs;
	}

	/**
	 * 获取分类菜系列表信息
	 * 
	 * @param context
	 * @return
	 */
	public static List<Caixi> getCaixi(Context context) {

		List<Caixi> list_caixis = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		String result = Common.post(caixiurl, postParameters);
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getInt("errcode") == 0) {
					list_caixis = new ArrayList<Caixi>();
					JSONArray jsonArray = jsonObject.getJSONArray("content");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.getJSONObject(i);
						String name = object.getString("name");
						String imgurl = object.getString("imgurl");
						int id = object.getInt("id");
						Caixi caixi = new Caixi(id, name, imgurl);
						list_caixis.add(caixi);
					}
				} else {
					Toast.makeText(context, jsonObject.getString("errmsg"),
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list_caixis;
	}

	/**
	 * 获取分类籍贯列表
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> getjiguan(Context context) {
		List<String> jiguans = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		String result = Common.post(jiguanurl, postParameters);
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getInt("errcode") == 0) {
					jiguans = new ArrayList<String>();
					JSONArray jsonArray = jsonObject.getJSONArray("content");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.getJSONObject(i);
						String name = object.getString("name");
						jiguans.add(name);
					}
				} else {
					Toast.makeText(context, jsonObject.getString("errmsg"),
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jiguans;
	}

	/**
	 * 提交意见反馈
	 * 
	 * @param context
	 * @param userid
	 * @param msg
	 * @param type
	 * @return
	 */
	public static boolean setFeedBack(Context context, int userid, String msg,
			int type) {
		boolean b = false;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		postParameters.add(new BasicNameValuePair("content", msg));
		postParameters.add(new BasicNameValuePair("type", Integer
				.toString(type)));

		String result = Common.post(feedbackurl, postParameters);
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getInt("errcode") == 0) {
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
	 * 
	 * 
	 * @param context
	 * @param masterid
	 * @param page
	 * @param pagecount
	 * @return
	 */
	public static List<String> getDistrict(Context context) {
		List<String> districts = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("city", "深圳"));
		String result = Common.post(CommonUrl.GETDISTINCT, postParameters);
		Log.v("result 地区", result);
		if (result != null) {
			try {
				
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getInt("errcode") == 0) {
					districts = new ArrayList<String>();
					JSONArray array = jsonObject.getJSONArray("content");
					if (array != null) {
						for(int i=0;i<array.length();i++)
						{
							districts.add(array.getString(i));
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return districts;
	}

	/**
	 * 
	 * 
	 * @param context
	 * @param masterid
	 * @param page
	 * @param pagecount
	 * @return
	 */
	public static List<String> getFoods(Context context) {
		List<String> foods = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("city", "深圳市"));
		String result = Common.get(foodsurl, postParameters);
		if (result != null) {
			try {
				
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getInt("errcode") == 0) {
					foods = new ArrayList<String>();
					JSONArray array = jsonObject.getJSONArray("content");
					if (array != null) {
						for(int i=0;i<array.length();i++)
						{
							foods.add(array.getString(i));
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return foods;
	}
	
	public static String getpinglun(Context context, int masterid, int page,
			int pagecount) {
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("masterid", Integer
				.toString(masterid)));
		postParameters.add(new BasicNameValuePair("page", Integer
				.toString(page)));
		postParameters.add(new BasicNameValuePair("pagecount", Integer
				.toString(pagecount)));
		String result = Common.post(pinglunurl, postParameters);
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getInt("errcode") == 0) {
					JSONObject object = jsonObject.getJSONObject("content");
					if (object != null) {

					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	public static String sendMsg(String phone) {
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("mobile", phone));
		postParameters.add(new BasicNameValuePair("type", Integer.toString(1)));
		String result = Common.post(msgurl, postParameters);
		return result;
	}

	public static boolean collectChef(int userid, int masterid) {
		boolean b = false;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		postParameters.add(new BasicNameValuePair("masterid", Integer
				.toString(masterid)));
		String result = Common.post(collectchefurl, postParameters);
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

	private final static String collectDishUrl = CommonUrl.SERVER + "user/collectDish";

	public static boolean collectDish(int userid, int dishid) {
		boolean b = false;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		postParameters.add(new BasicNameValuePair("dishid", Integer
				.toString(dishid)));
		String result = Common.post(collectDishUrl, postParameters);
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
	 * 添加新地址
	 */
	private static final String addaddrurl = CommonUrl.SERVER + "user/addUserAddr";

	public static boolean setAddress(int userid, String address,
			String province, String city, String area, String mobile,
			String contact) {
		boolean b = false;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		postParameters.add(new BasicNameValuePair("address", address));
		postParameters.add(new BasicNameValuePair("contact", contact));
		postParameters.add(new BasicNameValuePair("mobile", mobile));
		postParameters.add(new BasicNameValuePair("province", province));
		postParameters.add(new BasicNameValuePair("city", city));
		postParameters.add(new BasicNameValuePair("area", area));
		String result = Common.post(addaddrurl, postParameters);
		if (result != null) {
			try {
				JSONObject object = new JSONObject(result);
				if (object.getInt("errcode") == 0) {
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
	 * 修改地址
	 */
	private static final String updataaddrurl = CommonUrl.SERVER + "user/updateUserAddr";

	public static boolean updataAddress(int addrid, int userid, String address,
			String province, String city, String area, String mobile,
			String contact) {
		boolean b = false;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("addrid", Integer
				.toString(addrid)));
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		postParameters.add(new BasicNameValuePair("address", address));
		postParameters.add(new BasicNameValuePair("contact", contact));
		postParameters.add(new BasicNameValuePair("province", province));
		postParameters.add(new BasicNameValuePair("city", city));
		postParameters.add(new BasicNameValuePair("area", area));
		postParameters.add(new BasicNameValuePair("mobile", mobile));
		String result = Common.post(updataaddrurl, postParameters);
		if (result != null) {
			try {
				JSONObject object = new JSONObject(result);
				if (object.getInt("errcode") == 0) {
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
	 * 删除地址
	 */
	private static final String deleteaddrurl = CommonUrl.SERVER + "user/delUserAddr";

	public static boolean deleteAddress(int addrid) {
		boolean b = false;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("addrid", Integer
				.toString(addrid)));
		String result = Common.post(deleteaddrurl, postParameters);
		if (result != null) {
			try {
				JSONObject object = new JSONObject(result);
				if (object.getInt("errcode") == 0) {
					b = true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}

	private static final String orderurl = CommonUrl.SERVER + "order/submitOrder";

	public static String Order(String signature, int masterid, int userid,
			int packid, String dishids, int bringfood, int bringcond,
			String servicedate, int addrid, int orderfee, int paytype,
			String remark, int userCouponid, int couponValue) {
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("masterid", Integer
				.toString(masterid)));
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userid)));
		postParameters.add(new BasicNameValuePair("packid", Integer
				.toString(packid)));
		postParameters.add(new BasicNameValuePair("dishids", dishids));
		postParameters.add(new BasicNameValuePair("bringfood", Integer
				.toString(bringfood)));
		postParameters.add(new BasicNameValuePair("bringcond", Integer
				.toString(bringcond)));
		postParameters.add(new BasicNameValuePair("servicedate", servicedate));
		postParameters.add(new BasicNameValuePair("addrid", Integer
				.toString(addrid)));
		postParameters.add(new BasicNameValuePair("orderfee", Integer
				.toString(orderfee)));
		postParameters.add(new BasicNameValuePair("paytype", Integer
				.toString(paytype)));
		postParameters.add(new BasicNameValuePair("userCouponid", Integer
				.toString(userCouponid)));
		postParameters.add(new BasicNameValuePair("couponValue", Integer
				.toString(couponValue)));
		postParameters.add(new BasicNameValuePair("remark", remark));
		postParameters.add(new BasicNameValuePair("signature", signature));
		String result = "";
		result = Common.post(orderurl, postParameters);
		return result;
	}

	private static final String dishdeatailurl = CommonUrl.SERVER + "dish/queryDishDetail";

	public static Dish getDishDeatail(int dishid) {
		Dish dish = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("dishid", Integer
				.toString(dishid)));
		String result = Common.post(dishdeatailurl, postParameters);
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getInt("errcode") == 0) {
					JSONObject object = jsonObject.getJSONObject("content");
					String bigimgurl = object.getString("imgurl");
					String desc = object.getString("desc");
					String middleimgurl = object.getString("middleimgurl");
					String name = object.getString("name");
					String smallimgurl = object.getString("smallimgurl");
					int ordercount = object.getInt("ordercount");

					JSONArray array = object.getJSONArray("masters");
					List<Chefs> chefs = null;
					chefs = new ArrayList<Chefs>();
					for (int i = 0; i < array.length(); i++) {
						JSONObject object2 = array.getJSONObject(i);
						String headpicurl = object2.getString("headpicurl");
						int masterid = object2.getInt("masterid");
						Chefs chefs2 = new Chefs(headpicurl, masterid);
						chefs.add(chefs2);
					}

					dish = new Dish(bigimgurl, desc, dishid, name, smallimgurl,
							ordercount, chefs, middleimgurl);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dish;
	}

	private static final String initUrl = CommonUrl.SERVER + "order/initOrder";

	public static InitOrder initOrder(int masterid, String dishids) {
		InitOrder initOrder = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("masterid", Integer
				.toString(masterid)));
		postParameters.add(new BasicNameValuePair("dishids", dishids));
		String result = Common.post(initUrl, postParameters);
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				JSONObject jsonObject2 = jsonObject.getJSONObject("content");
				int masterid1 = jsonObject2.getInt("masterid");
				int orderfee = jsonObject2.getInt("orderfee");
				String signature = jsonObject2.getString("signature");
				List<Dish> dishs = null;
				JSONArray dishes = jsonObject2.getJSONArray("dishes");
				if (dishes.length() > 0) {
					dishs = new ArrayList<Dish>();
					for (int i = 0; i < dishes.length(); i++) {
						JSONObject object = dishes.getJSONObject(i);
						String bigimgurl = object.getString("imgurl");
						String name = object.getString("name");
						int dishid = object.getInt("dishid");
						Dish dish = new Dish(bigimgurl, "", dishid, name, "",
								"");
						dishs.add(dish);
					}
				}
				List<Pack> packs = null;
				List<ServiceTime> serviceTimes = null;
				JSONArray servicetimelist = jsonObject2
						.getJSONArray("servicetimelist");
				serviceTimes = new ArrayList<ServiceTime>();
				for (int i = 0; i < servicetimelist.length(); i++) {
					JSONObject object = servicetimelist.getJSONObject(i);
					String servicedate = object.getString("servicedate");
					JSONArray times = object.getJSONArray("times");
					List<Times> times2 = null;
					times2 = new ArrayList<Times>();
					for (int j = 0; j < times.length(); j++) {
						JSONObject object2 = times.getJSONObject(j);
						int status = object2.getInt("status");
						int time = object2.getInt("time");
						Times times3 = new Times(status, time);
						times2.add(times3);
					}
					ServiceTime serviceTime = new ServiceTime(servicedate,
							times2);
					serviceTimes.add(serviceTime);
				}
				initOrder = new InitOrder(signature, masterid1, orderfee,
						dishs, packs, serviceTimes);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return initOrder;
	}

	private static final String indexFuncUrl = CommonUrl.SERVER + "common/queryIndexFunc";

	public static List<IndexFun> getIndexFun(int count) {
		List<IndexFun> indexFuns = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("count", Integer
				.toString(count)));
		String result = Common.post(indexFuncUrl, postParameters);
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				JSONArray array = jsonObject.getJSONArray("content");
				indexFuns = new ArrayList<IndexFun>();
				for (int i = 0; i < array.length(); i++) {
					JSONObject object = array.getJSONObject(i);
					String url = object.getString("url");
					String title = object.getString("title");
					String imgurl = object.getString("imgurl");
					String content = object.getString("content");
					int sort = object.getInt("sort");
					IndexFun fun = new IndexFun(content, imgurl, title, url,
							sort);
					indexFuns.add(fun);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return indexFuns;
	}

//	private static final String calculateUrl = "http://120.24.218.214/app/order/calculateOrderFee";
//
//	public static int getCalulate(int mastergrade, int packid, int dishcount,
//			int bringfood, int bringcond) {
//		int i = 0;
//		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
//		postParameters.add(new BasicNameValuePair("mastergrade", Integer
//				.toString(mastergrade)));
//		postParameters.add(new BasicNameValuePair("packid", Integer
//				.toString(packid)));
//		postParameters.add(new BasicNameValuePair("dishcount", Integer
//				.toString(dishcount)));
//		postParameters.add(new BasicNameValuePair("bringfood", Integer
//				.toString(bringfood)));
//		postParameters.add(new BasicNameValuePair("bringcond", Integer
//				.toString(bringcond)));
//
//		String result = Common.post(calculateUrl, postParameters);
//		try {
//			JSONObject jsonObject = new JSONObject(result);
//			i = jsonObject.getInt("content");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return i;
//	}

	private static final String recommendMastersUrl = CommonUrl.SERVER + "master/queryRecommendMasters";

	public static List<Chefs> getRecommendMasters(Context context, int page,
			int pagecount) {
		List<Chefs> chefs = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("page", Integer
				.toString(page)));
		postParameters.add(new BasicNameValuePair("pagecount", Integer
				.toString(pagecount)));
		String result = Common.post(recommendMastersUrl, postParameters);
		try {
			JSONObject jsonObject1 = new JSONObject(result);
			JSONObject jsonObject = jsonObject1.getJSONObject("content");
			JSONArray array = jsonObject.getJSONArray("list");
			chefs = new ArrayList<Chefs>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				String headpicurl = object.getString("headpicurl");
				int masterid = object.getInt("masterid");
				Chefs chefs2 = new Chefs(headpicurl, masterid);
				chefs.add(chefs2);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chefs;
	}

	private static final String recommendDishsUrl = CommonUrl.SERVER + "dish/queryRecommendDishes";

	public static List<Dish> getRecommendDishs(Context context, int page,
			int pagecount) {
		List<Dish> dishs = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("page", Integer
				.toString(page)));
		postParameters.add(new BasicNameValuePair("pagecount", Integer
				.toString(pagecount)));
		String result = Common.post(recommendDishsUrl, postParameters);
		try {
			JSONObject jsonObject1 = new JSONObject(result);
			JSONObject jsonObject = jsonObject1.getJSONObject("content");
			JSONArray array = jsonObject.getJSONArray("list");
			dishs = new ArrayList<Dish>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				String bigimgurl = object.getString("bigimgurl");
				String smallimgurl = object.getString("smallimgurl");
				String middleimgurl = object.getString("middleimgurl");
				int dishid = object.getInt("dishid");
				Dish dish = new Dish(bigimgurl, dishid, smallimgurl,
						middleimgurl, "");
				dishs.add(dish);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dishs;
	}

	/**
	 * 查询收藏菜品列表
	 */
	private static final String userCollectDishUrl = CommonUrl.SERVER + "user/queryUserDishCollects";

	public static List<Dish> getUserCollectDishs(Context context, int userId,
			int page, int pagecount) {
		List<Dish> dishs = null;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("page", Integer
				.toString(page)));
		postParameters.add(new BasicNameValuePair("pagecount", Integer
				.toString(pagecount)));
		postParameters.add(new BasicNameValuePair("userid", Integer
				.toString(userId)));
		String result = Common.post(userCollectDishUrl, postParameters);
		if (!result.equals("")) {
			try {
				JSONObject jsonObject1 = new JSONObject(result);
				JSONObject jsonObject = jsonObject1.getJSONObject("content");
				if (jsonObject1.getInt("errcode") == 0) {
					dishs = new ArrayList<Dish>();
					JSONArray jsonArray = jsonObject.getJSONArray("list");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.getJSONObject(i);
						String bigimgurl = object.getString("bigimgurl");
						String desc = object.getString("desc");
						String name = object.getString("name");
						String smallimgurl = object.getString("smallimgurl");
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
						Dish dish = new Dish(bigimgurl, desc, dishid, name,
								smallimgurl, ordercount, chefs);
						dishs.add(dish);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dishs;
	}

	/**
	 * 检查更新
	 */
	private static final String chechVersion = CommonUrl.SERVER + "common/checkApkVersion";

	public static boolean checkApkVersion(String version) {
		boolean b = false;
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("version", version));
		String result = Common.post(chechVersion, postParameters);
		if (result != null) {
			try {
				JSONObject object = new JSONObject(result);
				if (object.getInt("errcode") == 0) {
					b = true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}
}
