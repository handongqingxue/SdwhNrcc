package com.sdwhNrcc.service.serviceImpl.v3_1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.v3_1.*;
import com.sdwhNrcc.entity.v3_1.*;
import com.sdwhNrcc.service.v3_1.*;

@Service
public class StaffServiceImpl implements StaffService {

	@Autowired
	private StaffMapper staffDao;

	@Override
	public int add(List<Staff> staffList) {
		// TODO Auto-generated method stub
		int count=0;
		for (Staff staff : staffList) {
			if(staffDao.getCountById(staff.getId())==0)
				count+=staffDao.add(staff);
			else
				count+=staffDao.edit(staff);
		}
		return count;
	}

	@Override
	public List<Staff> queryList() {
		// TODO Auto-generated method stub
		return staffDao.queryList();
	}

	@Override
	public List<Staff> queryEIList() {
		// TODO Auto-generated method stub
		return staffDao.queryEIList();
	}
}
