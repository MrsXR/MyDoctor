package cn.gem.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class GetCalendar {

	 //private static String mYear; // 当前年
	private static String mMonth; // 月
	private static String mDay;

	//判断是否是今天
	public String isToday(){

		Calendar cal =Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MM-dd");
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String date=df.format(cal.getTime()); //获取本周一的日期

		//这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		//增加一个星期，才是我们中国人理解的本周日的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		date+="至"+df.format(cal.getTime());
		return date;
	}


	//下一个星期
	public String isNextToday(){

		Calendar cal =Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		SimpleDateFormat df = new SimpleDateFormat("MM-dd");
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String date=df.format(cal.getTime()); //获取本周一的日期

		//这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		//增加一个星期，才是我们中国人理解的本周日的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		date+="至"+df.format(cal.getTime());
		return date;
	}


	//获取当前周的日期时间(对应星期)
	public  List<String> getNewDate(){

		List<String> getNew=new ArrayList<>();
		Calendar cal =Calendar.getInstance();

		//将当前时间改为今天
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		//mYear = String.valueOf(cal.get(Calendar.YEAR));// 获取当前年份
		mMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);// 获取当前月份
		mDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
		String date =mMonth + "-" + mDay;

		getNew.add("日期:");
		SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        getNew.add(df.format(cal.getTime())); //获取本周一的日期
        
        
        cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        getNew.add(df.format(cal.getTime()));
        
        cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        getNew.add(df.format(cal.getTime()));
        
        cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        getNew.add(df.format(cal.getTime()));
        
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        getNew.add(df.format(cal.getTime()));
        
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        getNew.add(df.format(cal.getTime()));
        
		//这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		//增加一个星期，才是我们中国人理解的本周日的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		getNew.add(df.format(cal.getTime()));


		for(int i=0;i<getNew.size();i++){
			if (getNew.get(i).equals(date)) {
				getNew.set(i,"今天");
			}
		}

		return getNew;
		
	}
	
	 /** 
     * 获取今天往后一周的日期（几月几号） 
     */  
    public  List<String> getSecondDate() {

		List<String> getNew=new ArrayList<>();
		Calendar cal =Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_YEAR, 1);//加一个星期，计算下一个星期的天数
		SimpleDateFormat df = new SimpleDateFormat("MM-dd");

		getNew.add("日期:");

		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		getNew.add(df.format(cal.getTime())); //获取本周一的日期


		cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		getNew.add(df.format(cal.getTime()));

		cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		getNew.add(df.format(cal.getTime()));

		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		getNew.add(df.format(cal.getTime()));

		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		getNew.add(df.format(cal.getTime()));

		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		getNew.add(df.format(cal.getTime()));

		//这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		//增加一个星期，才是我们中国人理解的本周日的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		getNew.add(df.format(cal.getTime()));
		return getNew;
	}


}
