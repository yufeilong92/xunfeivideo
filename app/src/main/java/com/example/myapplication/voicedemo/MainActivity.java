package com.example.myapplication.voicedemo;

import androidx.core.app.ActivityCompat;
import com.example.myapplication.R;
import com.iflytek.cloud.SpeechUtility;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = MainActivity.class.getSimpleName();
	private Toast mToast;
	private final int URL_REQUEST_CODE = 0X001;
	private TextView edit_text;

	@SuppressLint("ShowToast")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		edit_text = (TextView) findViewById(R.id.edit_text);
		StringBuffer buf = new StringBuffer();
		buf.append("当前APPID为：");
		buf.append(getString(R.string.app_id)+"\n");
		buf.append(getString(R.string.example_explain));
		edit_text.setText(buf);
		requestPermissions();
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		//mscInit(null);//采用sdk默认url
		SimpleAdapter listitemAdapter = new SimpleAdapter();
		((ListView) findViewById(R.id.listview_main)).setAdapter(listitemAdapter);


	}

	@Override
	public void onClick(View view) {
		int tag = Integer.parseInt(view.getTag().toString());
		switch (tag) {
		case 3:
			// 语音合成
			MyIntentService.startActionFoo(this,"你已经超速，你已经超速，你已经超速，你已经超速，你已经超速，你已经超速，你已经超速，你已经超速，你已经超速","");
			break;

		default:
			break;
		}

	}

	// Menu 列表
	String items[] = { "立刻体验语音听写", "立刻体验语法识别", "立刻体验语义理解", "立刻体验语音合成",
			"立刻体验语音评测", "立刻体验语音唤醒", "立刻体验声纹密码","立刻体验人脸识别"/*,"重置域名"*/ };

	private class SimpleAdapter extends BaseAdapter {
		public View getView(int position, View convertView, ViewGroup parent) {
			if (null == convertView) {
				LayoutInflater factory = LayoutInflater.from(MainActivity.this);
				View mView = factory.inflate(R.layout.list_items, null);
				convertView = mView;
			}
			
			Button btn = (Button) convertView.findViewById(R.id.btn);
			btn.setOnClickListener(MainActivity.this);
			btn.setTag(position);
			btn.setText(items[position]);
			
			return convertView;
		}

		@Override
		public int getCount() {
			return items.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}

	private void showTip(final String str) {
		mToast.setText(str);
		mToast.show();
	}

	@Override
	protected void onResume() {
		// 开放统计 移动数据统计分析
		/*FlowerCollector.onResume(MainActivity.this);
		FlowerCollector.onPageStart(TAG);*/
		super.onResume();
	}

	@Override
	protected void onPause() {
		// 开放统计 移动数据统计分析
	/*	FlowerCollector.onPageEnd(TAG);
		FlowerCollector.onPause(MainActivity.this);*/
		super.onPause();
	}

	private void requestPermissions(){
		try {
			if (Build.VERSION.SDK_INT >= 23) {
				int permission = ActivityCompat.checkSelfPermission(this,
						Manifest.permission.WRITE_EXTERNAL_STORAGE);
				if(permission!= PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(this,new String[]
							{Manifest.permission.WRITE_EXTERNAL_STORAGE,
							Manifest.permission.LOCATION_HARDWARE,Manifest.permission.READ_PHONE_STATE,
							Manifest.permission.WRITE_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE,
							Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS},0x0010);
				}

				if(permission != PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(this,new String[] {
							Manifest.permission.ACCESS_COARSE_LOCATION,
							Manifest.permission.ACCESS_FINE_LOCATION},0x0010);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}


	private void mscUninit() {
		if (SpeechUtility.getUtility()!= null) {
			SpeechUtility.getUtility().destroy();
			try{
				new Thread().sleep(40);
			}catch (InterruptedException e) {
				Log.w(TAG,"msc uninit failed"+e.toString());
			}
		}
	}


}
