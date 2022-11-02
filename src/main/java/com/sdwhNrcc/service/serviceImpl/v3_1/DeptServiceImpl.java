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
public class DeptServiceImpl implements DeptService {

	@Autowired
	private DeptMapper deptDao;
	
	@Override
	public int add(List<Dept> deptList, String databaseName) {
		// TODO Auto-generated method stub
		int count=0;
		for (Dept dept : deptList) {
			if(deptDao.getCountByDeptId(dept.getDeptId(),databaseName)==0) {
				switch (databaseName) {
				case Constant.DATABASE_NAME_ZBXQHGYXGS:
					dept.setDeptLzqId(StringUtil.createUUID());
					break;
				}
				count+=deptDao.add(dept, databaseName);
			}
			else
				count+=deptDao.edit(dept, databaseName);
		}
		return count;
	}

}
