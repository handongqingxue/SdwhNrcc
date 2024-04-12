package com.sdwhNrcc.service.serviceImpl.sdwh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.sdwh.*;
import com.sdwhNrcc.entity.sdwh.*;
import com.sdwhNrcc.service.sdwh.*;

@Service
public class LoginUserCxSdServiceImpl implements LoginUserCxSdService {

	@Autowired
	private LoginUserCxSdMapper loginUserCxSdDao;
	
	@Override
	public int add(LoginUserCxSd lucs) {
		// TODO Auto-generated method stub
		int count=loginUserCxSdDao.getCountByUsername(lucs.getUsername());
		if(count==0)
			count=loginUserCxSdDao.add(lucs);
		else
			count=loginUserCxSdDao.edit(lucs);
		return count;
	}
	
	@Override
	public String getTokenByUsername(String username) {
		// TODO Auto-generated method stub
		return loginUserCxSdDao.getTokenByUsername(username);
	}

}
