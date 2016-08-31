package tools;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Comment;
import bean.Dingdan;
import bean.User;


public class Data {
	/*
	 * 操作数据库的类
	 */
	Statement stmt;
	Connection conn;

	public void connect() {
		// 1.注册驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 2.创建数据库的连接
		// useUnicode=true&characterEncoding=GBK：支持中文
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/tourism?useUnicode=true&characterEncoding=GBK","root", "root");
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	public String getTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}

	public void closeSql() {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int login(String phone, String password) {
		// TODO Auto-generated method stub
		int result=-1;
		String sql="select * from users where phone='"+phone+"' and password='"+password+"'";
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				result=res.getInt("id");
				return result;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}



	public String getImg(String poiId) {
		// TODO Auto-generated method stub
		String sql="select * from jingdian where poi_id="+poiId;
		String img="";
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				img=res.getString("img");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}



	public List<Comment> getComments(String poiId) {
		// TODO Auto-generated method stub
		List<Comment> comments=new ArrayList<Comment>();
		String sql="select * from comment where poi_id="+poiId;
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				Comment comment=new Comment();
				comment.setId(res.getInt("id"));
				comment.setContent(res.getString("content"));
				comment.setUserId(res.getInt("user_id"));
				comment.setPoiId(res.getInt("poi_id"));
				comment.setTime(res.getString("time"));
				comments.add(comment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
	}



	public User getUser(int userId) {
		// TODO Auto-generated method stub
		User user=new User();
		String sql="select * from users where id="+userId;
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				user.setId(res.getInt("id"));
				user.setMoney(res.getDouble("money"));
				user.setName(res.getString("user_name"));
				user.setPhone(res.getString("phone"));
				user.setPassword(res.getString("password"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}



	public boolean yuding(String poiId, String userId, String name,
			String address) {
		// TODO Auto-generated method stub
		boolean result=false;
		String sql="insert into orders(jingdian_name,time,poi_id,user_id,address,status) values(\""+name+"\",\""+getTime()+"\","+poiId+","+userId+",\""+address+"\",\"order\")";
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}



	public List<Dingdan> getDingDan(String userId) {
		// TODO Auto-generated method stub
		List<Dingdan> dingdans=new ArrayList<Dingdan>();
		String sql="select * from orders where user_id="+userId;
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				Dingdan dingdan=new Dingdan();
				dingdan.setId(res.getInt("id"));
				dingdan.setTime(res.getString("time"));
				dingdan.setPoiId(res.getInt("poi_id"));
				dingdan.setAddress(res.getString("address"));
				dingdan.setJingdianName(res.getString("jingdian_name"));
				dingdans.add(dingdan);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dingdans;
	}
	public List<Dingdan> getDingDan(String userId,String status) {
		// TODO Auto-generated method stub
		List<Dingdan> dingdans=new ArrayList<Dingdan>();
		String sql="select * from orders where user_id="+userId+" and status=\""+status+"\"";
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				Dingdan dingdan=new Dingdan();
				dingdan.setId(res.getInt("id"));
				dingdan.setTime(res.getString("time"));
				dingdan.setPoiId(res.getInt("poi_id"));
				dingdan.setAddress(res.getString("address"));
				dingdan.setJingdianName(res.getString("jingdian_name"));
				dingdans.add(dingdan);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dingdans;
	}


	public boolean updateInfo(String id, String name, String phone) {
		// TODO Auto-generated method stub
		boolean result=false;
		String sql="update users set user_name=\""+name+"\" , phone=\""+phone+"\" where id="+id;
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}



	public boolean addComment(String poiId, String content, String userId) {
		// TODO Auto-generated method stub
		boolean result=false;
		String sql="insert into comment (poi_id,time,content,user_id) values("+poiId+",\""+getTime()+"\",\""+content+"\","+userId+")";
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}



	public boolean updateDingdan(String id) {
		// TODO Auto-generated method stub
		boolean result=false;
		String sql="update orders set status=\"finish\" where id="+id;
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}



	public boolean getPhone(String phone) {
		// TODO Auto-generated method stub
		boolean result=false;
		String sql="select *from users where phone=\""+phone+"\"";
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				result=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}



	public String registe(String phone, String userName, String password) {
		// TODO Auto-generated method stub
		String sql="insert into users(user_name,phone,password,money)values(\""+userName+"\",\""+phone+"\",\""+password+"\","+100+")";
		String result="fail";
		try {
			stmt.execute(sql);
			result="success";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}



	public double selectUserMoney(String userId) {
		// TODO Auto-generated method stub
		double money=0;
		String sql="select * from users where id="+userId;
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				money=res.getDouble("money");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return money;
	}



	public double selectMartMoney(String poiId) {
		// TODO Auto-generated method stub
		double money=0;
		String sql="select * from jingdian where poi_id="+poiId;
		try {
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()){
				money=res.getDouble("money");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return money;
	}



	public boolean updateUserMoney(double d, String userId) {
		// TODO Auto-generated method stub
		
		boolean result=false;
		String sql="update users set money="+d+" where id="+userId;
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}



	public boolean updateJingdianMoney(double d, String poiId) {
		// TODO Auto-generated method stub
		boolean result=false;
		String sql="update jingdian set money="+d+" where poi_id="+poiId;
		try {
			stmt.execute(sql);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}



	


	

	



	
}
