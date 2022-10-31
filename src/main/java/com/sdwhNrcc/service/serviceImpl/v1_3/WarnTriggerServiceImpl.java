package com.sdwhNrcc.service.serviceImpl.v1_3;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.v1_3.*;
import com.sdwhNrcc.entity.v1_3.*;
import com.sdwhNrcc.service.v1_3.*;
import com.sdwhNrcc.util.DateUtil;

@Service
public class WarnTriggerServiceImpl implements WarnTriggerService {

	@Autowired
	private WarnTriggerMapper warnTriggerDao;

	@Override
	public int add(List<WarnTrigger> warnTriggerList, String databaseName) {
		// TODO Auto-generated method stub
		int count=0;
		for (WarnTrigger warnTrigger : warnTriggerList) {
			count+=warnTriggerDao.add(warnTrigger, databaseName);
		}
		return count;
	}

}
