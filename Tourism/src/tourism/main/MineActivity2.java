package tourism.main;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import bean.Dingdan;

import tools.Address;
import tools.NetWork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MineActivity2 extends Activity {

	final int sendToastWhat=1,getInfoWhat=2,changeInfoWhat=3;
	final String sendToastKey="sendToastKey",getInfoKey="getInfoKey",changeInfoKey="changeInfoKey";
	EditText nameEdit,phoneEdit;
	TextView moneyText;
	
	
	Handler handler=new Handler(){
		public void handleMessage(Message message){
			switch (message.what) {
			case sendToastWhat:
				String toast=message.getData().getString(sendToastKey);
				Toast.makeText(MineActivity2.this, toast, Toast.LENGTH_SHORT).show();
				break;

			case getInfoWhat:
				String result=message.getData().getString(getInfoKey);
				
				try {
					JSONObject jsonObject = new JSONObject(result);
					String name=jsonObject.getString("name");
					double money=jsonObject.getDouble("money");
					String phone=jsonObject.getString("phone");
					nameEdit.setText(name);
					moneyText.setText(money+"¥");
					phoneEdit.setText(phone);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
			case changeInfoWhat:
				String changeResult=message.getData().getString(changeInfoKey);
				try {
					JSONObject jsonObject=new JSONObject(changeResult);
					boolean code=jsonObject.getBoolean("code");
					if(code){
						sendToast("修改成功");
						finish();
					}
					else{
						sendToast("修改失败");
					}
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
		setContentView(R.layout.activity_mine_activity2);
		initView();
		initData();
	}
	
	public void initView(){
		TextView change=(TextView)findViewById(R.id.change);
		change.setOnClickListener(new ClickListener());
		 nameEdit=(EditText)findViewById(R.id.name);
		phoneEdit=(EditText)findViewById(R.id.phone);
		 moneyText=(TextView)findViewById(R.id.money);
		LinearLayout waitComment=(LinearLayout)findViewById(R.id.wait_comment);
		
		waitComment.setOnClickListener(new ClickListener());
	}
	public void initData(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				NetWork netWork=new NetWork();
				Map<String,String> map=new HashMap<String,String>();
				map.put("userId", Address.id);
				String result=netWork.doGet(map,Address.getInfo);
				if(result!=null){
					sendMessage(getInfoWhat,getInfoKey,result);
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
	public class ClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
		

			case R.id.wait_comment:
				
				Intent intent=new Intent(MineActivity2.this,MyDingDan.class);
				intent.putExtra("from", "mine");
				startActivity(intent);
				break;
			case R.id.change:
				String phone=phoneEdit.getText().toString().trim();
				String name=nameEdit.getText().toString().trim();
				changeInfo(name,phone);
				break;
			}
		}
		
	}
	public void changeInfo(final String name,final String phone){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				NetWork netWork=new NetWork();
				Map<String,String> map=new HashMap<String,String>();
				map.put("id", Address.id);
				map.put("phone", phone);
				map.put("name", name);
				String result=netWork.doPost(map, Address.changeInfo);
				if(result!=null){
					sendMessage(changeInfoWhat,changeInfoKey,result);
				}
			}
		}).start();
	}
}
