package com.sdwhNrcc.service.serviceImpl.v1_3;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.v1_3.*;
import com.sdwhNrcc.entity.v1_3.*;
import com.sdwhNrcc.service.v1_3.*;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationMapper locationDao;

	@Override
	public int add(Location location,String databaseName) {
		// TODO Auto-generated method stub
		int count=locationDao.getCountByUid(location.getUid(),databaseName);
		if(count==0)
			locationDao.add(location,databaseName);
		else
			count=locationDao.edit(location,databaseName);
		return count;
	}

	@Override
	public List<Location> queryELList(String databaseName) {
		// TODO Auto-generated method stub
		return locationDao.queryELList(databaseName);
	}
}
