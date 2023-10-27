package com.sdwhNrcc.service.serviceImpl.v3_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.v3_1.*;
import com.sdwhNrcc.entity.v3_1.*;
import com.sdwhNrcc.service.v3_1.*;

@Service
public class ApiLogV3_1ServiceImpl implements ApiLogV3_1Service {
	
	@Autowired
	private ApiLogV3_1Mapper apiLogDao;

	@Override
	public int add(ApiLog apiLog, String databaseName) {
		// TODO Auto-generated method stub
		return apiLogDao.add(apiLog,databaseName);
	}

}
