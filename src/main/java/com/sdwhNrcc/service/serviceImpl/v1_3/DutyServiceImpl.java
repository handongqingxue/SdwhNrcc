package com.sdwhNrcc.service.serviceImpl.v1_3;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.v1_3.*;
import com.sdwhNrcc.entity.v1_3.*;
import com.sdwhNrcc.service.v1_3.*;

@Service
public class DutyServiceImpl implements DutyService {

	@Autowired
	private DutyMapper dutyDao;

	@Override
	public int add(List<Duty> dutyList) {
		// TODO Auto-generated method stub
		int count=0;
		for (Duty duty : dutyList) {
			count=dutyDao.getCountById(duty.getId());
			if(count==0)
				count=dutyDao.add(duty);
			else
				count=dutyDao.edit(duty);
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> summaryOnlineDuty() {
		// TODO Auto-generated method stub
		return dutyDao.summaryOnlineDuty();
	}
}
