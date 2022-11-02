package com.sdwhNrcc.service.serviceImpl.v3_1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.v3_1.*;
import com.sdwhNrcc.entity.v3_1.*;
import com.sdwhNrcc.service.v3_1.*;
import com.sdwhNrcc.util.Constant;
import com.sdwhNrcc.util.StringUtil;

@Service
public class StaffServiceImpl implements StaffService {

	@Autowired
	private StaffMapper staffDao;

	@Override
	public int add(List<Staff> staffList, String databaseName) {
		// TODO Auto-generated method stub
		int count=0;
		for (Staff staff : staffList) {
			if(staffDao.getCountById(staff.getId(),databaseName)==0) {
				switch (databaseName) {
				case Constant.DATABASE_NAME_ZBXQHGYXGS:
					staff.setLzqId(StringUtil.createUUID());
					break;
				}
				count+=staffDao.add(staff, databaseName);
			}
			else
				count+=staffDao.edit(staff, databaseName);
		}
		return count;
	}

	@Override
	public List<Staff> queryList(String databaseName) {
		// TODO Auto-generated method stub
		return staffDao.queryList(databaseName);
	}

	@Override
	public List<Staff> queryEIList(String databaseName) {
		// TODO Auto-generated method stub
		return staffDao.queryEIList(databaseName);
	}
}
