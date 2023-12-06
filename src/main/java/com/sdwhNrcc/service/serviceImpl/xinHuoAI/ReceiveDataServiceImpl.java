package com.sdwhNrcc.service.serviceImpl.xinHuoAI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.entity.xinHuoAI.*;
import com.sdwhNrcc.dao.xinHuoAI.*;
import com.sdwhNrcc.service.xinHuoAI.*;

@Service
public class ReceiveDataServiceImpl implements ReceiveDataService {

	@Autowired
	private ReceiveDataMapper receiveDataDao;

	@Override
	public int add(ReceiveData receiveData, String databaseName) {
		// TODO Auto-generated method stub
		return receiveDataDao.add(receiveData, databaseName);
	}
}
