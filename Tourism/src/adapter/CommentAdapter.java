package adapter;

import java.util.List;

import tourism.main.R;

import bean.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter{

	List<Comment>comments;
	Context context;
	
	LayoutInflater inflater;
	public CommentAdapter(List<Comment> comments, Context context) {
		super();
		this.comments = comments;
		this.context = context;
		this.inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Comment comment=comments.get(arg0);
		arg1=inflater.inflate(R.layout.comment_item, null);
		TextView name=(TextView)arg1.findViewById(R.id.name);
		TextView content=(TextView)arg1.findViewById(R.id.content);
		TextView time=(TextView)arg1.findViewById(R.id.time);
		name.setText(comment.getUserame());
		time.setText(comment.getTime());
		content.setText(comment.getContent());
		return arg1;
	}

}
