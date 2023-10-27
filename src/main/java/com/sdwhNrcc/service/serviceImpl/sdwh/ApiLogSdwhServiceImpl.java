package com.sdwhNrcc.service.serviceImpl.sdwh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.sdwh.*;
import com.sdwhNrcc.entity.sdwh.*;
import com.sdwhNrcc.service.sdwh.*;

@Service
public class ApiLogSdwhServiceImpl implements ApiLogServiceSdwh {
	
	@Autowired
	private ApiLogSdwhMapper apiLogDao;

	@Override
	public int add(ApiLog apiLog) {
		// TODO Auto-generated method stub
		return apiLogDao.add(apiLog);
	}

}
