package com.sdwhNrcc.service.serviceImpl.v1_3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.v1_3.*;
import com.sdwhNrcc.entity.v1_3.*;
import com.sdwhNrcc.service.v1_3.*;

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
