package tourism.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Address;
import tools.NetWork;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	
	EditText phoneEdit,passwordEdit;

	String phone, password;
	NetWork netWork;

	final int ToastWhat=1,loginWhat=2;
	final String ToastKey="toastKey",loginKey="loginKey";
	Handler handler=new Handler(){
		public void handleMessage(Message message){
			switch (message.what) {
			case ToastWhat:
				String content=message.getData().getString(ToastKey);
				Toast.makeText(Login.this, content, Toast.LENGTH_SHORT).show();
				break;
			case loginWhat:
				String loginInfo=message.getData().getString(loginKey);
				try {
					 JSONObject jsonObject = new JSONObject(loginInfo);
                     String result=jsonObject.getString("code");
                     if (result.equals("success")) {
                         Address.id=jsonObject.getString("id");
                         Intent intent = new Intent(Login.this, MainActivity.class);
                         startActivity(intent);
                         finish();
                     } else {
                         sendToast("登录失败");
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
		setContentView(R.layout.activity_login);
		init();
	}

	public void init() {
		phoneEdit = (EditText) findViewById(R.id.phone);
		passwordEdit = (EditText) findViewById(R.id.password);
		Button login=(Button)findViewById(R.id.login);
		Button regist=(Button)findViewById(R.id.regist);
		login.setOnClickListener(new ClickListener());
		regist.setOnClickListener(new ClickListener());
	}

	public class ClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.login:
				String phone=phoneEdit.getText().toString().trim();
				String password=passwordEdit.getText().toString().trim();
				if(phone.equals("")||password.equals("")){
				}
				
				else
				login(phone,password);
				break;

			case R.id.regist:
				Intent intent=new Intent(Login.this,RegisteActivity.class);
				startActivity(intent);
				break;
			}
		}
		
	}
	

	public void login(final String phone,final String password) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				NetWork netWork=new NetWork();
				Map<String,String> map=new HashMap<String,String>();
				map.put("phone", phone);
				map.put("password", password);
				String result=netWork.doGet(map, Address.login);
				
				if(result!=null){
					sendMessage(loginWhat,loginKey,result);
				}
			}
		}).start();
			
		

	}

	 public void sendMessage(int what, String key, String value) {
	        Message message = new Message();
	        message.what = what;
	        Bundle bundle = new Bundle();
	        bundle.putString(key, value);
	        message.setData(bundle);
	        handler.sendMessage(message);
	    }

	    public void sendToast(String text) {
	        if (text == null) {
	            text = "";
	        }
	        sendMessage(ToastWhat,ToastKey, text);
	    }
}
