package com.sdwhNrcc.service.serviceImpl.sdwh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.sdwh.LoginUserMapper;
import com.sdwhNrcc.dao.v1_3.*;
import com.sdwhNrcc.entity.sdwh.LoginUser;
import com.sdwhNrcc.service.sdwh.LoginUserService;
import com.sdwhNrcc.service.v1_3.*;

@Service
public class LoginUserServiceImpl implements LoginUserService {

	@Autowired
	private LoginUserMapper loginUserDao;

	@Override
	public int add(LoginUser lu) {
		// TODO Auto-generated method stub
		int count=loginUserDao.getCountByUsername(lu.getUsername());
		if(count==0)
			count=loginUserDao.add(lu);
		else
			count=loginUserDao.edit(lu);
		return count;
	}
	
	@Override
	public String getTokenByUsername(String username) {
		// TODO Auto-generated method stub
		return loginUserDao.getTokenByUsername(username);
	}

}
