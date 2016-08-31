package tourism.main;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Address;
import tools.NetWork;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisteActivity extends Activity {
EditText phoneEdit,userNameEdit,passwordEdit,confirmPasswordEdit;
final int sendToastWhat=0x101,registWhat=0x102;
final String sendToastKey="sendToastKey",registeKey="registeKey";


Handler handler=new Handler(){

    public void handleMessage(Message message){
        switch (message.what){
            case sendToastWhat:
                String toast=message.getData().getString(sendToastKey);
                Toast.makeText(RegisteActivity.this,toast,Toast.LENGTH_SHORT).show();
                break;
            case registWhat:
                String regist=message.getData().getString(registeKey);
                try {
                    JSONObject jsonObject=new JSONObject(regist);
                    String code=jsonObject.getString("code");
                    if(code.equals("had_phone")){
                        sendToast("该账号已存在");
                    }
                    else{
                        if(code.equals("success")){
                            sendToast("注册成功");
                            finish();
                        }
                        else{
                            sendToast("注册失败");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registe);
		initView();
	}
	public void initView(){
		
		 	phoneEdit=(EditText)findViewById(R.id.phone_edit);
	        userNameEdit=(EditText)findViewById(R.id.username_edit);
	        passwordEdit=(EditText)findViewById(R.id.password_edit);
	        confirmPasswordEdit=(EditText)findViewById(R.id.confirm_password_edit);
	       
		Button regist=(Button)findViewById(R.id.regist);
		regist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				
				 String phone=phoneEdit.getText().toString().trim();
                 String name=userNameEdit.getText().toString().trim();
                 String password=passwordEdit.getText().toString().trim();
                 String confirmPassword=confirmPasswordEdit.getText().toString().trim();
                 if(phone.equals("")||name.equals("")||password.equals("")||confirmPassword.equals("")){
                     sendToast("请填写完整");
                 }
                 else{
                     if(!password.equals(confirmPassword)){
                         sendToast("密码填写不一致");
                     }
                     else{
                         registe(phone,name,password);
                     }
                 }
				
				
				
				
			}
		});
	}
	
	private void registe(final String phone, final String name, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetWork netWork=new NetWork();
                Map<String,String> map=new HashMap<String, String>();
                map.put("phone",phone);
                map.put("name",name);
                map.put("password",password);
                String result=netWork.doPost(map,Address.registeURL);
                if(result!=null){
                    sendMessage(registWhat,registeKey,result);
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
}
