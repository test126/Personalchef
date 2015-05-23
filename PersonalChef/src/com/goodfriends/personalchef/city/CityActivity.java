package com.goodfriends.personalchef.city;

import java.util.ArrayList;

import com.goodfriends.personalchef.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CityActivity extends Activity implements OnClickListener {

	private TextView tv_city1, tv_city2;
	private City city ;
	private Button btn_clear;
	private ArrayList<City> toCitys;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addr_activity_main);
		tv_city1 = (TextView) findViewById(R.id.tv_city1);
		tv_city1.setOnClickListener(this);
		tv_city2 = (TextView) findViewById(R.id.tv_city2);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		tv_city2.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
		city = new City();
		toCitys = new ArrayList<City>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onClick(View v) {

		if(v == tv_city1){
			Intent in = new Intent(this, CitySelect1Activity.class);
			in.putExtra("city", city);
			startActivityForResult(in, 1);
		}else if(v == tv_city2){
			
			Intent in = new Intent(this, CitySelect2Activity.class);
			in.putExtra("toCitys", toCitys);
			startActivityForResult(in, 2);
		}else if(v == btn_clear){
			tv_city1.setText("ѡ��һ������");
			tv_city2.setText("ѡ��������");
			city = null;
			toCitys.clear();
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 8){
			if(requestCode == 1){
				city = data.getParcelableExtra("city");
				tv_city1.setText(city.getProvince()+city.getCity()+city.getDistrict());
				
			}else if(requestCode == 2){
				toCitys = data.getParcelableArrayListExtra("toCitys");
				StringBuffer ab = new StringBuffer();
				for (int i = 0; i < toCitys.size(); i++) {
					if(i==toCitys.size()-1){//��������һ�����оͲ���Ҫ����						
						ab.append(toCitys.get(i).getCity());
					}else{
						ab.append(toCitys.get(i).getCity()+"�� ");//��������һ�����о���Ҫ����		
					}
				}
				tv_city2.setText(ab.toString());
			}
		}
	}

}
