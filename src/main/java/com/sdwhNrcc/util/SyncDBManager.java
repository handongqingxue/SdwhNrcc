package com.sdwhNrcc.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/api/syncDBManager")
public class SyncDBManager {
	//ʱ����(5����)
	private static final long PERIOD_DAY =24*60*60*1000;

	@RequestMapping(value = "/makeSync")
	public void makeSync() {
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 1);//�賿1��
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime();//��һ��ִ�ж�ʱ�����ʱ��
		/**�����һ��ִ�ж�ʱ�����ʱ�� С�ڵ�ǰ��ʱ��  
                          ��ʱҪ�� ��һ��ִ�ж�ʱ�����ʱ���һ�죬�Ա���������¸�ʱ���ִ�С��������һ�죬���������ִ�С�
        **/ 
		if(date.before(new Date())){
			//date=DateUtil.dayOper(DateUtil.getDate(new Date(), "yyyy-MM-dd"),1);
		}
		Timer timer = new Timer();
		timer.schedule(new SyncDBTask(), date, PERIOD_DAY);
	}
}
