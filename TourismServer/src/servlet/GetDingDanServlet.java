package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Dingdan;

import tools.Data;

public class GetDingDanServlet extends HttpServlet {

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Data data=new Data();
		data.connect();
		StringBuffer buffer=new StringBuffer("{\"dingdans\":[");
		String userId=request.getParameter("userId");
		String status=request.getParameter("status");
		List<Dingdan> dingdans;
		if(status!=null)
		 dingdans=data.getDingDan(userId,status);
		else{
			dingdans=data.getDingDan(userId);
		}
		for(int i=0;i<dingdans.size();i++){
			Dingdan dingdan=dingdans.get(i);
			buffer.append("{\"name\":\""+dingdan.getJingdianName()+"\"");
			buffer.append(",\"id\":"+dingdan.getId());
			buffer.append(",\"poi_id\":"+dingdan.getPoiId());
			buffer.append(",\"address\":\""+dingdan.getAddress()+"\"");
			buffer.append(",\"time\":\""+dingdan.getTime()+"\"}");
			if(i<dingdans.size()-1){
				buffer.append(",");
			}
		}
		buffer.append("]}");
		out.print(buffer);
		out.flush();
		out.close();
		data.closeSql();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
