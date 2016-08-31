package adapter;

import java.util.List;

import bean.Comment;

import com.bumptech.glide.Glide;
import tools.Address;
import tourism.main.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter{
	String[] imgs;
	Context context;
	LayoutInflater inflater;
	
	public ImageAdapter(String img,Context context) {
		super();
		this.imgs = img.split(";");
		this.context = context;
		inflater=LayoutInflater.from(context);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imgs.length;
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
		
		view=inflater.inflate(R.layout.img_item, null);
		ImageView img=(ImageView)view.findViewById(R.id.img);
		 try {
	            Glide.with(context).load(Address.imgPath +imgs[position]).into(img);
	        }catch (Exception e){
	        	int x=1+1;
	        }
		
		
		return view;
		
	}

}
