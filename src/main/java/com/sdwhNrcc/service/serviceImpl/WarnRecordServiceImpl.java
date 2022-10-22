package com.sdwhNrcc.service.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.*;
import com.sdwhNrcc.entity.*;
import com.sdwhNrcc.service.*;
import com.sdwhNrcc.util.DateUtil;

@Service
public class WarnRecordServiceImpl implements WarnRecordService {

	@Autowired
	private WarnRecordMapper warnRecordDao;

	@Override
	public int add(List<WarnRecord> warnRecordList) {
		// TODO Auto-generated method stub
		int count=0;
		for (WarnRecord warnRecord : warnRecordList) {
			if(warnRecordDao.getCountById(warnRecord.getId())>0)
				continue;
			Long raiseTime = warnRecord.getRaiseTime();
			if(!StringUtils.isEmpty(String.valueOf(raiseTime)))
				warnRecord.setRaiseTimeYMD(DateUtil.convertLongToString(raiseTime));
			Long startTime = warnRecord.getStartTime();
			if(!StringUtils.isEmpty(String.valueOf(startTime)))
				warnRecord.setStartTimeYMD(DateUtil.convertLongToString(startTime));
			count+=warnRecordDao.add(warnRecord);
		}
		return count;
	}

	@Override
	public List<WarnRecord> queryEAList(int sync) {
		// TODO Auto-generated method stub
		return warnRecordDao.queryEAList(sync);
	}

	@Override
	public int syncByIds(String syncIds) {
		// TODO Auto-generated method stub
		List<String> idList = Arrays.asList(syncIds.split(","));
		return warnRecordDao.syncByIds(idList);
	}

}
