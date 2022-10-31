package com.sdwhNrcc.service.serviceImpl.v1_3;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.v1_3.*;
import com.sdwhNrcc.entity.v1_3.*;
import com.sdwhNrcc.service.v1_3.*;

@Service
public class EntityServiceImpl implements EntityService {

	@Autowired
	private EntityMapper entityDao;

	@Override
	public int add(List<Entity> entityList,String databaseName) {
		// TODO Auto-generated method stub
		int count=0;
		for (Entity entity : entityList) {
			if(entityDao.getCountById(entity.getId(),databaseName)==0)
				count+=entityDao.add(entity,databaseName);
			else
				count+=entityDao.edit(entity,databaseName);
		}
		return count;
	}

	@Override
	public List<Entity> queryList(String databaseName) {
		// TODO Auto-generated method stub
		return entityDao.queryList(databaseName);
	}

	@Override
	public List<Entity> queryEIList(String databaseName) {
		// TODO Auto-generated method stub
		return entityDao.queryEIList(databaseName);
	}
}
