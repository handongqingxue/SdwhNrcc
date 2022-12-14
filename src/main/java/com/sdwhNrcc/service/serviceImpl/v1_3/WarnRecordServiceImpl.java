package com.sdwhNrcc.service.serviceImpl.v1_3;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.v1_3.*;
import com.sdwhNrcc.entity.v1_3.*;
import com.sdwhNrcc.service.v1_3.*;
import com.sdwhNrcc.util.DateUtil;

@Service
public class WarnRecordServiceImpl implements WarnRecordService {

	@Autowired
	private WarnRecordMapper warnRecordDao;

	@Override
	public int add(List<WarnRecord> warnRecordList, String databaseName) {
		// TODO Auto-generated method stub
		int count=0;
		for (WarnRecord warnRecord : warnRecordList) {
			if(warnRecordDao.getCountById(warnRecord.getId(),databaseName)>0)
				continue;
			Long raiseTime = warnRecord.getRaiseTime();
			if(!StringUtils.isEmpty(String.valueOf(raiseTime)))
				warnRecord.setRaiseTimeYMD(DateUtil.convertLongToString(raiseTime));
			Long startTime = warnRecord.getStartTime();
			if(!StringUtils.isEmpty(String.valueOf(startTime)))
				warnRecord.setStartTimeYMD(DateUtil.convertLongToString(startTime));
			count+=warnRecordDao.add(warnRecord,databaseName);
		}
		return count;
	}

	@Override
	public List<WarnRecord> queryEAList(int sync, String databaseName) {
		// TODO Auto-generated method stub
		return warnRecordDao.queryEAList(sync,databaseName);
	}

	@Override
	public int syncByIds(String syncIds, String databaseName) {
		// TODO Auto-generated method stub
		List<String> idList = Arrays.asList(syncIds.split(","));
		return warnRecordDao.syncByIds(idList,databaseName);
	}

}
