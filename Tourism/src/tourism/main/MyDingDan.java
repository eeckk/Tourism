package tourism.main;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bean.Dingdan;

import tools.Address;
import tools.NetWork;
import adapter.DingdanAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;

public class MyDingDan extends Activity {
	private Cursor mCursor;
	ListView mListView;
	 List<String> list;
	 final int getDingdanWhat=1,sendToastWhat=2;
	 final String getDingdanKey="getDingdanKey",sendToastKey="sendToastKey";
	 String from;
	 List<Dingdan> dingdans;
	 Handler handler=new Handler(){
		 public void handleMessage(Message message){
		switch (message.what) {
		case sendToastWhat:
			String toast=message.getData().getString(sendToastKey);
			Toast.makeText(MyDingDan.this, toast, Toast.LENGTH_SHORT).show();
			break;

		case getDingdanWhat:
			String result=message.getData().getString(getDingdanKey);
			dingdans=new ArrayList<Dingdan>();
			
			try {
				
				JSONObject jsonObject=new JSONObject(result);
				JSONArray jsonArray=jsonObject.getJSONArray("dingdans");
				for(int i=0;i<jsonArray.length();i++){
					Dingdan dingdan=new Dingdan();
					JSONObject dingdanObject=jsonArray.getJSONObject(i);
					dingdan.setAddress(dingdanObject.getString("address"));
					dingdan.setTime(dingdanObject.getString("time"));
					dingdan.setJingdianName(dingdanObject.getString("name"));
					dingdan.setPoiId(dingdanObject.getInt("poi_id"));
					dingdan.setId(dingdanObject.getInt("id"));
					dingdans.add(dingdan);
				}
				DingdanAdapter adapter=new DingdanAdapter(dingdans,MyDingDan.this);
				mListView.setAdapter(adapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}	 
		 }
	 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dingdan);

		from=getIntent().getStringExtra("from");
		initView();
	}
	
	public void initView(){
		mListView=(ListView)findViewById(R.id.listview);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(from.equals("mine")){
					Intent intent=new Intent(MyDingDan.this,AddComment.class);
					intent.putExtra("poiId", dingdans.get(arg2).getPoiId());
					intent.putExtra("id",dingdans.get(arg2).getId());
					startActivityForResult(intent,1);
				}
			}
		});
		getDingDan();
	}
	public void getDingDan(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				NetWork netWork=new NetWork();
				Map<String,String> map=new HashMap<String,String>();
				map.put("userId", Address.id);
				if(from.equals("mine")){
					map.put("status", "order");
				}
				String result=netWork.doGet(map,Address.getDingDan);
				if(result!=null){
					sendMessage(getDingdanWhat,getDingdanKey,result);
					
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1&&resultCode==1){
			getDingDan();
		}
	}
	
	
	
}
