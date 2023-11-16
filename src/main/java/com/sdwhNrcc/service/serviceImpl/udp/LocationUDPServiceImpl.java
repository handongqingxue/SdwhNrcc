package com.sdwhNrcc.service.serviceImpl.udp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.entity.udp.*;
import com.sdwhNrcc.dao.udp.*;
import com.sdwhNrcc.service.udp.*;

@Service
public class LocationUDPServiceImpl implements LocationUDPService {

	@Autowired
	private LocationUDPMapper locationUDPDao;

	@Override
	public int add(LocationUDP locationUDP) {
		// TODO Auto-generated method stub
		int count=locationUDPDao.getCountByUid(locationUDP.getUid(),locationUDP.getTenantId());
		if(count==0)
			locationUDPDao.add(locationUDP);
		else
			count=locationUDPDao.edit(locationUDP);
		return count;
	}

	@Override
	public List<LocationUDP> queryELList() {
		// TODO Auto-generated method stub
		return locationUDPDao.queryELList();
	}
}
