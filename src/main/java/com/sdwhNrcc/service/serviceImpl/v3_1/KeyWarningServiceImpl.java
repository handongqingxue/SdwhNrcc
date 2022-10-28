package com.sdwhNrcc.service.serviceImpl.v3_1;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.v3_1.*;
import com.sdwhNrcc.entity.v3_1.*;
import com.sdwhNrcc.service.v3_1.*;
import com.sdwhNrcc.util.DateUtil;

@Service
public class KeyWarningServiceImpl implements KeyWarningService {

	@Autowired
	private KeyWarningMapper keyWarningDao;

	@Override
	public int add(KeyWarning keyWarning,String databaseName) {
		// TODO Auto-generated method stub
		Long raiseTime = keyWarning.getRaiseTime();
		if(!StringUtils.isEmpty(String.valueOf(raiseTime)))
			keyWarning.setRaiseTimeYMD(DateUtil.convertLongToString(raiseTime));
		int count=keyWarningDao.add(keyWarning,databaseName);
		return count;
	}

	@Override
	public List<KeyWarning> queryEAList(int sync,String databaseName) {
		// TODO Auto-generated method stub
		return keyWarningDao.queryEAList(sync,databaseName);
	}

	@Override
	public int syncByIds(String syncIds,String databaseName) {
		// TODO Auto-generated method stub
		List<String> idList = Arrays.asList(syncIds.split(","));
		return keyWarningDao.syncByIds(idList,databaseName);
	}

}
