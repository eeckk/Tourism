package tourism.main;

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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddComment extends Activity {
	String neirong;
	private EditText neirongText;
	private Button send;
	int poiId,id;
	final int addCommentWhat=1,sendToastWhat=2;
	final String addCommentKey="addCommentKey",sendToastKey="sendToastKey";
	Handler handler=new Handler(){
		public void handleMessage(Message message) {
			switch (message.what) {
			case sendToastWhat:
				String toast=message.getData().getString(sendToastKey);
				Toast.makeText(AddComment.this,toast,Toast.LENGTH_SHORT).show();
				break;

			case addCommentWhat:
				String result=message.getData().getString(addCommentKey);
				try {
					JSONObject jsonObject=new JSONObject(result);
					boolean code=jsonObject.getBoolean("code");
					if(code){
						sendToast("评论成功");
						setResult(1);
						finish();
					}
					else{
						sendToast("评论失败");
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
		setContentView(R.layout.activity_ping_lun__add);
		Intent intent=getIntent();
		poiId=intent.getIntExtra("poiId", -1);
		id=intent.getIntExtra("id",-1);
		neirongText=(EditText)findViewById(R.id.neirong);
		send=(Button)findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				neirong=neirongText.getText().toString();
				if(!neirong.equals("")){
					addComment(neirong);
				}
			}
		});
	}

	public void addComment(final String comment){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				NetWork netWork=new NetWork();
				Map<String,String>map=new HashMap<String,String>();
				map.put("content",comment );
				map.put("poiId",poiId+"");
				map.put("id",id+"");
				map.put("userId",Address.id);
				String result=netWork.doPost(map, Address.addComment);
				if(result!=null){
					sendMessage(addCommentWhat,addCommentKey,result);
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
