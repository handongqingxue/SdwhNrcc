package com.sdwhNrcc.service;

import com.sdwhNrcc.entity.*;

public interface EpLoginUserService {

	int add(EpLoginUser elu);
	
	String getCookieByUserId(String userId);

}
