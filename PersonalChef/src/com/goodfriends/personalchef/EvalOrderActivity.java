package com.goodfriends.personalchef;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;

import com.goodfriends.personalchef.application.SysApplication;
import com.goodfriends.personalchef.bean.Chefs;
import com.goodfriends.personalchef.bean.OrderBean;
import com.goodfriends.personalchef.common.CommonFun1;
import com.goodfriends.personalchef.common.CommonUrl;
import com.goodfriends.personalchef.util.AsynImageLoader;
import com.goodfriends.personalchef.util.ImageTools;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

@SuppressLint("NewApi")
public class EvalOrderActivity extends Activity implements OnClickListener,
		OnRatingBarChangeListener {
	private static final int TAKE_PICTURE = 0;
	private static final int CHOOSE_PICTURE = 1;
	
	private static final int SCALE = 15;//照片缩小比例
	private final int IMGBYTESIZE = 101484;
	private ImageView iv_image = null;
	

	private OrderBean orderBean;
	private String orderId, content;
	private ImageView back, chefHead, certification, gold, star, grade1,
			grade2, grade3, grade4, grade5;
	private TextView chefName, jiguan, caixi, send;
	private Chefs chefs;
	private RatingBar rating1, rating2, rating3;
	private int userid, masterid, i1 = 0, i2 = 0, i3 = 0;
	private EditText editText;
	
	//图片评价
	private RelativeLayout rl_img;
	private String filepath = null;
	private String fileData = null;
	private File file;
 	private Bitmap photo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		orderId = getIntent().getExtras().getString("orderId");
//		orderId = "2015022809295726540";
		setContentView(R.layout.activity_eval);
		userid = getSharedPreferences("USERINFO", MODE_PRIVATE).getInt(
				"userId", 0);
		initView();
		new Thread(runnable).start();
	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.title_back);
		back.setOnClickListener(this);
		chefHead = (ImageView) findViewById(R.id.orderdetail_head);
		chefName = (TextView) findViewById(R.id.orderdetail_name);
		jiguan = (TextView) findViewById(R.id.orderdetail_jiguan);
		caixi = (TextView) findViewById(R.id.orderdetail_caixi);
		certification = (ImageView) findViewById(R.id.orderdetail_certification);
		gold = (ImageView) findViewById(R.id.orderdetail_gold);
		star = (ImageView) findViewById(R.id.orderdetail_star);
		grade1 = (ImageView) findViewById(R.id.orderdeatail_grade1);
		grade2 = (ImageView) findViewById(R.id.orderdeatail_grade2);
		grade3 = (ImageView) findViewById(R.id.orderdeatail_grade3);
		grade4 = (ImageView) findViewById(R.id.orderdeatail_grade4);
		grade5 = (ImageView) findViewById(R.id.orderdeatail_grade5);
		rating1 = (RatingBar) findViewById(R.id.ratingbar1);
		rating1.setOnRatingBarChangeListener(this);
		rating2 = (RatingBar) findViewById(R.id.ratingbar2);
		rating2.setOnRatingBarChangeListener(this);
		rating3 = (RatingBar) findViewById(R.id.ratingbar3);
		rating3.setOnRatingBarChangeListener(this);
		send = (TextView) findViewById(R.id.tijiao);
		send.setOnClickListener(this);
		editText = (EditText) findViewById(R.id.eval_remark);
		iv_image = (ImageView) this.findViewById(R.id.img);
		rl_img = (RelativeLayout) findViewById(R.id.rl_img);
	}

	Runnable runnable2 = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(!iv_image.isShown())
			{
				filepath = null;
				fileData = null;
			}
			makeEval();
		}
	};
	
	public void makeEvalEnd()
	{
		closeDialog();
	}
	
	public void makeEval()
	{
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("orderno", orderId);
		params.put("userid", userid);
		params.put("masterid", masterid);
		params.put("attitudescore", i1);
		params.put("delicisscore", i2);
		params.put("majorscore", i3);
		params.put("content", content);
		
		if(iv_image.isShown())
		{
//			File file = ;
//			params.put("pic",new File(filepath));
			try {
				params.put("pic", new File(filepath));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String url = CommonUrl.EVALORDER;
		client.post(url, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] responseBody) {
            	String res = "";
        		try {
					res = new String(responseBody,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
        		Log.e("success responseBody", res);
        		makeEvalEnd();
        		myHandler.sendEmptyMessage(1);
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] responseBody, Throwable arg3) {
            	String res = "";
        		try {
					res = new String(responseBody,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
        		Log.e("error responseBody", res);
        		makeEvalEnd();
        		myHandler.sendEmptyMessage(404);
			}
		});
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			orderBean = CommonFun1.getOrderDeatail(orderId);
			if (orderBean != null) {
				myHandler.sendEmptyMessage(orderBean.getErrcode());
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				setChefInfo();
				break;
			case 1:
				closeDialog();
				Toast.makeText(getApplicationContext(), "已成功评价",
						Toast.LENGTH_SHORT).show();
				EvalOrderActivity.this.finish();
				break;
			case 403:
				Toast.makeText(getApplicationContext(), "请输入评价内容",
						Toast.LENGTH_SHORT).show();
				break;
			case 404:
				closeDialog();
				Toast.makeText(getApplicationContext(), "评价失败，请稍候重试",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};

	private void setChefInfo() {
		// TODO Auto-generated method stub
		chefs = orderBean.getChefs();
		masterid = chefs.getId();
		AsynImageLoader asynImageLoader = new AsynImageLoader();
		asynImageLoader.showImageAsyn(chefHead, chefs.getHeadpicurl(),
				R.drawable.nopic);
		chefName.setText(chefs.getName());
		jiguan.setText("籍贯：" + chefs.getNativeplace());
		caixi.setText("擅长：" + chefs.getCookstyles());
		if (chefs.getIsauth() == 1) {
			certification.setVisibility(View.VISIBLE);
		}
		switch (chefs.getGrade()) {
		case 1:
			break;
		case 2:
			gold.setVisibility(View.VISIBLE);
			break;
		case 3:
			gold.setVisibility(View.VISIBLE);
			break;
		case 4:
			star.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		switch (chefs.getScore()) {
		case 1:
			grade1.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			break;
		case 2:
			grade1.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade2.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			break;
		case 3:
			grade1.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade2.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade3.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			break;
		case 4:
			grade1.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade2.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade3.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade4.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			break;
		case 5:
			grade1.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade2.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade3.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade4.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade5.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.title_back:
			this.finish();
			break;
		case R.id.tijiao:
			content = editText.getText().toString().trim();
			if (content.equals("")) {
				myHandler.sendEmptyMessage(403);
			} else {
				showDialog("提交中...");
				makeEval();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ratingbar1:
			i1 = (int) arg1;
			break;
		case R.id.ratingbar2:
			i2 = (int) arg1;
			break;
		case R.id.ratingbar3:
			i3 = (int) arg1;
			break;
		default:
			break;
		}
	};

	private ProgressDialog progressDialog;

	private void showDialog(String s) {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(s);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	private void closeDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
	
	public void showPicturePicker(Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("图片来源");
		builder.setNegativeButton("取消", null);
		builder.setItems(new String[]{"拍照","相册"}, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case TAKE_PICTURE:
					Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"image.jpg"));
					//指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(openCameraIntent, TAKE_PICTURE);
					break;
					
				case CHOOSE_PICTURE:
					Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
					openAlbumIntent.setType("image/*");
					startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
					break;

				default:
					break;
				}
			}
		});
		builder.create().show();
	}
	
	private Bitmap bitmap;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_PICTURE:
				//将保存在本地的图片取出并缩小后显示在界面上
				try {
					if(bitmap != null)
					{
						bitmap.recycle();
					}
					bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/image.jpg");
					int size = 1;
					if(bitmap.getByteCount() > IMGBYTESIZE)
					{
						size = (int) Math.sqrt(bitmap.getByteCount()/IMGBYTESIZE);
					}
					Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / size, bitmap.getHeight() / size);
					//由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
					
					filepath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/image.jpg";
					file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"/image.jpg");
					file.createNewFile();
					//Convert bitmap to byte array
					/*
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					newBitmap.compress(CompressFormat.PNG, 0 , bos);
					byte[] bitmapdata = bos.toByteArray();
					//write the bytes in file
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(bitmapdata);
					fos.flush();
					fos.close();
					*/
					//将处理过的图片显示在界面上，并保存到本地
					iv_image.setImageBitmap(newBitmap);
					
					Log.e("filepath", filepath);
					rl_img.setVisibility(View.VISIBLE);
//					ImageTools.savePhotoToSDCard(newBitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}  
				break;

			case CHOOSE_PICTURE:
				ContentResolver resolver = getContentResolver();
				//照片的原始资源地址
				Uri originalUri = data.getData();
	            try {
	            	if(photo != null)
	            	{
	            		photo.recycle();
	            	}
	            	//使用ContentProvider通过URI获取原始图片
					photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
					if (photo != null) {
						int scale_size = 1;
						if(photo.getByteCount() > IMGBYTESIZE)
						{
							scale_size = (int) Math.sqrt(photo.getByteCount()/IMGBYTESIZE);
						}
						//为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
						Bitmap smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / scale_size, photo.getHeight() / scale_size);
						//释放原始图片占用的内存，防止out of memory异常发生
						
						//从相册中获取图片，获得图片路径
						/*
					    String[] proj = {MediaStore.Images.Media.DATA};
			            Cursor cursor = managedQuery(originalUri, proj, null, null, null); 
			            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			            cursor.moveToFirst();
			            filepath = cursor.getString(column_index);
			            */
						filepath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/image.jpg";
						Log.e("filepath", filepath);
						
						//Convert bitmap to byte array
						/*
						ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
						smallBitmap.compress(CompressFormat.PNG, 0 , bos1);
						byte[] bitmapdata1 = bos1.toByteArray();
						//write the bytes in file
						FileOutputStream fos1 = new FileOutputStream(file);
						fos1.write(bitmapdata1);
						fos1.flush();
						fos1.close();
						*/
						iv_image.setImageBitmap(smallBitmap);
						rl_img.setVisibility(View.VISIBLE);
//						ImageTools.savePhotoToSDCard(smallBitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), "/image.jpg");
						
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}  
				break;
			
			default:
				break;
			}
		}
	}
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(photo !=null)
		{
			photo.recycle();
		}
		if(bitmap !=null)
		{
			bitmap.recycle();
		}
	}

	public void showPicture(View v)
	{
		showPicturePicker(this);
	}
	
	public void clearImage(View v)
	{
		iv_image.setImageBitmap(null);
		rl_img.setVisibility(View.GONE);
	}
}
