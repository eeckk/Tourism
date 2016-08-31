package tools;

public class Address {
	public static final String IPAddress="http://192.168.1.108";
	public static final String serverURL=IPAddress+":8080/TourismServer";
	public static final String login=serverURL+"/LoginServlet";
	public static final String imgPath=IPAddress+"/tourism/";
	public static String id;
	public static String getJingDianURL=serverURL+"/GetJingDianServlet";
	public  static final String registeURL=serverURL+"/RegistServlet";
	public  static final  String getInfo=serverURL+"/GetInfoServlet";
	public  static final String getDingDan=serverURL+"/GetDingDanServlet";
	public static final String yudingURL=serverURL+"/DingpiaoServlet";
	public  static final String changeInfo = serverURL+"/ChangeInfoServlet";
	public  static final String addComment = serverURL+"/AddCommentServlet";

}
