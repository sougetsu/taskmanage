package com.sdmx.taskmanage.vo;

import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	
	public static int getDate(Date startDate,Date endDate){
		int workingDays = 0;
		Calendar sDate = Calendar.getInstance();
		sDate.setTime(startDate);
		Calendar eDate = Calendar.getInstance();
		eDate.setTime(endDate);
		System.out.println(sDate.getTimeInMillis());
		System.out.println(eDate.getTimeInMillis());
		while (eDate.getTimeInMillis() - sDate.getTimeInMillis() >= 0){
	        if(sDate.get(Calendar.DAY_OF_WEEK)!= Calendar.SATURDAY && sDate.get(Calendar.DAY_OF_WEEK)!= Calendar.SUNDAY) {
	            workingDays ++;
	        }
	        sDate.add(Calendar.DAY_OF_YEAR, 1);
	    }
		return workingDays;
	}
}
