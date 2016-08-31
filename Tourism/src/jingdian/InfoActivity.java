package jingdian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bean.Comment;

import tools.Address;
import tools.ItemImg;

import tools.NetWork;
import tourism.main.MainActivity;
import tourism.main.R;
import adapter.CommentAdapter;
import adapter.ImageAdapter;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends Activity {
	
	private TextView mNameTextView;
	private TextView mAddressTextView;
	private TextView mCreateTextView;
	private TextView mUpdateTextView;
	private TextView mDistanceTextView;
	
	public static final String POIID = "poiid_text";
	public static final String POINAME = "poi_name";
	public static final String POILOCATION = "location_text";
	public static final String POIADDRESS = "address_text";
	public static final String POICREATETIME = "createtime_text";
	public static final String POIUPDATETIME = "update_time_text";
	public static final String POIDISTANCE = "distance_text";
	public static final String POIKEY = "poi_key";
	public static final String POIVALUE = "poi_value";
	public String imgAddress="";
	String poiId,poiName,poiLocation,poiAddress,poiCreateTime,poiUpdateTime,poiDistance;
	ListView commentList,imgList;
	int much;
	LinearLayout mContainer;
	final int getJingdianWhat=1,sendToastWhat=2,yudingWhat=3;
	final String getJingdianKey="getJingdianKey",sendToastKey="sendToastKey",yudingKey="yudingKey";
	List<Comment>comments;
	
	Handler handler=new Handler(){
		public void handleMessage(Message message){
			switch (message.what) {
			case getJingdianWhat:
				String result=message.getData().getString(getJingdianKey);
				try {
					comments=new ArrayList<Comment>();
					JSONObject jsonObject=new JSONObject(result);
					String img=jsonObject.getString("img");
					JSONArray jsonArray=jsonObject.getJSONArray("comments");
					for(int i=0;i<jsonArray.length();i++){
						Comment comment=new Comment();
						JSONObject commentObject=jsonArray.getJSONObject(i);
						comment.setContent(commentObject.getString("content"));
						comment.setTime(commentObject.getString("time"));
						comment.setUserame(commentObject.getString("userName"));
						comments.add(comment);
					}
					ImageAdapter adapter1=new ImageAdapter(img,InfoActivity.this);
					imgList.setAdapter(adapter1);
					MainActivity.setListViewHeightBasedOnChildren(imgList);
					CommentAdapter adapter2=new CommentAdapter(comments,InfoActivity.this);
					commentList.setAdapter(adapter2);
					MainActivity.setListViewHeightBasedOnChildren(commentList);
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;

			case sendToastWhat:
				String toast=message.getData().getString(sendToastKey);
				Toast.makeText(InfoActivity.this, toast, Toast.LENGTH_SHORT).show();
				break;
			case yudingWhat:
				String yudingResult=message.getData().getString(yudingKey);
				try {
					JSONObject jsonObject=new JSONObject(yudingResult);
					String code=jsonObject.getString("code");
					if(code.equals("success")){
						sendToast("订票成功");
						Intent intent = new Intent(InfoActivity.this, JingDianActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						startActivity(intent);
						finish();
					}
					else{
						if(code.equals("no_money"))
						sendToast("余额不足");
						else{
							sendToast("订票失败");
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	};
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poi_layout);
		commentList=(ListView)findViewById(R.id.comment_list);
		imgList=(ListView)findViewById(R.id.img_list);
		mNameTextView = (TextView) findViewById(R.id.name_text);
		mAddressTextView = (TextView) findViewById(R.id.address_text);
		mCreateTextView = (TextView) findViewById(R.id.createtime_text);
		mUpdateTextView = (TextView) findViewById(R.id.update_time_text);
		mDistanceTextView = (TextView) findViewById(R.id.distance_text);
		mContainer = (LinearLayout) findViewById(R.id.container);
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			return;
		}
		poiId = bundle.getString(POIID, "");
		poiName = bundle.getString(POINAME, "");
		poiLocation = bundle.getString(POILOCATION, "");
		poiAddress = bundle.getString(POIADDRESS, "");
		poiCreateTime = bundle.getString(POICREATETIME, "");
		poiUpdateTime = bundle.getString(POIUPDATETIME, "");
		poiDistance = bundle.getString(POIDISTANCE, "");
		
		mNameTextView.setText(poiName);
		mAddressTextView.setText(poiAddress);
		mCreateTextView.setText(poiCreateTime);
		mUpdateTextView.setText(poiUpdateTime);
		mDistanceTextView.setText(poiDistance);
		ArrayList<String> keys = bundle.getStringArrayList(POIKEY);
		ArrayList<String> values = bundle.getStringArrayList(POIVALUE);
		for (int i = 0; i < keys.size(); i++) {
			View view = getLayoutInflater().inflate(R.layout.item_layout, null);
		
			TextView valueText = (TextView) view
					.findViewById(R.id.poi_value_id);
			if(keys.get(i).equals("much")){
				
				much=Integer.parseInt(values.get(i));
				valueText.setText(much+" ¥");
			}
			
			if(keys.get(i).equals("tel")){
		
			valueText.setText(values.get(i));
			}
			mContainer.addView(view);
		}
		
		initData();
		Button yuding=(Button)findViewById(R.id.yuding);
		yuding.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				yuding();
			}
		});
	}
	public void yuding(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				NetWork netWork=new NetWork();
				Map<String,String>map=new HashMap<String,String>();
				map.put("poiId", poiId);
				map.put("userId",Address.id);
				map.put("name", poiName);
				map.put("address", poiAddress);
				map.put("money", much+"");
				String result=netWork.doPost(map,Address.yudingURL);
				if(result!=null)
					sendMessage(yudingWhat,yudingKey,result);
			}
		}).start();
		
	}
	
	public void initData(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				NetWork netWork=new NetWork();
				Map<String,String>map=new HashMap<String,String>();
				map.put("poiId", poiId);
				String result=netWork.doGet(map,Address.getJingDianURL);
				if(result!=null){
					sendMessage(getJingdianWhat,getJingdianKey,result);
				}
			}
		}).start();
		
	}
	public void sendMessage(int what,String key,String value){
		Message message=new Message();
		message.what=what;
		Bundle bundle=new Bundle();
		bundle.putString(key, value);
		message.setData(bundle);
		handler.sendMessage(message);
	}
	public void sendToast(String toast){
		sendMessage(sendToastWhat,sendToastKey,toast);
	}
	
	/**
	 * 
	 * 返回键监
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(InfoActivity.this, JingDianActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			finish();

		}
		return super.onKeyDown(keyCode, event);
	}

}
