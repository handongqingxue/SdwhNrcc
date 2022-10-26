package com.sdwhNrcc.service.serviceImpl.v3_1;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.v3_1.*;
import com.sdwhNrcc.entity.v3_1.*;
import com.sdwhNrcc.service.v3_1.*;

@Service
public class PositionServiceImpl implements PositionService {

	@Autowired
	private PositionMapper positionDao;

	@Override
	public int add(Position position) {
		// TODO Auto-generated method stub
		int count=positionDao.getCountByTagId(position.getTagId());
		if(count==0)
			positionDao.add(position);
		else
			count=positionDao.edit(position);
		return count;
	}

	@Override
	public List<Position> queryELList() {
		// TODO Auto-generated method stub
		return positionDao.queryELList();
	}
}
