package tourism.main;

import java.util.ArrayList;
import java.util.HashMap;

import jingdian.JingDianActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	Button jingdian,dingdan,wode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		jingdian=(Button)findViewById(R.id.jingdian);
		dingdan=(Button)findViewById(R.id.dingdan);
		wode=(Button)findViewById(R.id.mine);
		jingdian.setOnClickListener(new ClickListener());
		dingdan.setOnClickListener(new ClickListener());
		wode.setOnClickListener(new ClickListener());
	}
	public class ClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch (arg0.getId()) {
			case R.id.jingdian:
				
				intent.setClass(MainActivity.this,JingDianActivity.class);
				startActivity(intent);
				break;
			case R.id.dingdan:
				intent.setClass(MainActivity.this,MyDingDan.class);
				intent.putExtra("from", "main");
				startActivity(intent);
				break;
			case R.id.mine:
				intent.setClass(MainActivity.this,MineActivity2.class);
				startActivity(intent);
				
				break;
			}
		}
	


	}

	 /**
     * 动态设置ListView的高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
