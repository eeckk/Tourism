package adapter;

import java.util.List;

import tourism.main.R;

import bean.Dingdan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DingdanAdapter extends BaseAdapter {

	List<Dingdan> dingdans;
	Context context;
	LayoutInflater inflater;
	
	public DingdanAdapter(List<Dingdan> dingdans, Context context) {
		super();
		this.dingdans = dingdans;
		this.context = context;
		inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dingdans.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.dingdan_item, null);
		TextView name=(TextView)view.findViewById(R.id.name);
		TextView time=(TextView)view.findViewById(R.id.time);
		TextView address=(TextView)view.findViewById(R.id.address);
		Dingdan dingdan=dingdans.get(position);
		name.setText(dingdan.getJingdianName());
		time.setText(dingdan.getTime());
		address.setText(dingdan.getAddress());
		return view;
	}

}
