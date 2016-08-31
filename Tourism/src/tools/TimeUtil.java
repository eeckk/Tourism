package tools;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeUtil {
	/**
	 * @return
	 */
	public static String getAbsoluteTime(){
		SimpleDateFormat sdf= new SimpleDateFormat("yyMMddHHmmss");
		 return sdf.format(new Date());
	}
	
	
	public static String getFormatTime(String time){
        SimpleDateFormat sdf= new SimpleDateFormat("yyMMddHHmmss");
        Date date=null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf2=new SimpleDateFormat("yy年MM月dd日   HH:mm:ss");
        return sdf2.format(date);
    }
	
	private static String formatTime(int hour, int minute){
		String time="";
		if(hour<10){
			time+="0"+hour+":";
		}else{
			time+=hour+":";
		}
		
		if(minute<10){
			time+="0"+minute;
		}else{
		    time+=minute;
		}
		System.out.println("format(hour, minute)="+time);
		return time;
	}
}
