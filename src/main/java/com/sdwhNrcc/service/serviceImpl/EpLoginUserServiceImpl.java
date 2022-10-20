package com.sdwhNrcc.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.*;
import com.sdwhNrcc.service.*;

@Service
public class EpLoginUserServiceImpl implements EpLoginUserService {

	@Autowired
	private EpLoginUserMapper epLoginUserDao;

	@Override
	public String getCookieByUserId(String userId) {
		// TODO Auto-generated method stub
		return epLoginUserDao.getCookieByUserId(userId);
	}
}
