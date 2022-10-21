package com.sdwhNrcc.service.serviceImpl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.*;
import com.sdwhNrcc.entity.*;
import com.sdwhNrcc.service.*;
import com.sdwhNrcc.util.DateUtil;

@Service
public class WarnTriggerServiceImpl implements WarnTriggerService {

	@Autowired
	private WarnTriggerMapper warnTriggerDao;

	@Override
	public int add(List<WarnTrigger> warnTriggerList) {
		// TODO Auto-generated method stub
		int count=0;
		for (WarnTrigger warnTrigger : warnTriggerList) {
			count+=warnTriggerDao.add(warnTrigger);
		}
		return count;
	}

}
