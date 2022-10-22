package com.sdwhNrcc.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/api/syncDBManager")
public class SyncDBManager {
	//时间间隔(5分钟)
	private static final long PERIOD_DAY =24*60*60*1000;

	@RequestMapping(value = "/makeSync")
	public void makeSync() {
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 1);//凌晨1点
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime();//第一次执行定时任务的时间
		/**如果第一次执行定时任务的时间 小于当前的时间  
                          此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
        **/ 
		if(date.before(new Date())){
			//date=DateUtil.dayOper(DateUtil.getDate(new Date(), "yyyy-MM-dd"),1);
		}
		Timer timer = new Timer();
		timer.schedule(new SyncDBTask(), date, PERIOD_DAY);
	}
}
