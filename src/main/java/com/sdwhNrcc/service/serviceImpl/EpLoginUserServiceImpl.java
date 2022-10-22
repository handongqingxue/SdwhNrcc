package com.sdwhNrcc.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.*;
import com.sdwhNrcc.entity.*;
import com.sdwhNrcc.service.*;

@Service
public class EpLoginUserServiceImpl implements EpLoginUserService {

	@Autowired
	private EpLoginUserMapper epLoginUserDao;

	@Override
	public int add(EpLoginUser elu) {
		// TODO Auto-generated method stub
		int count=epLoginUserDao.getCountByUserId(elu.getUserId());
		if(count==0)
			count=epLoginUserDao.add(elu);
		else
			count=epLoginUserDao.edit(elu);
		return count;
	}

	@Override
	public String getCookieByUserId(String userId) {
		// TODO Auto-generated method stub
		return epLoginUserDao.getCookieByUserId(userId);
	}
}
