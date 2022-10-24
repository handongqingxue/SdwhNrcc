package com.sdwhNrcc.service.v1_3;

import com.sdwhNrcc.entity.v1_3.*;

public interface EpLoginUserService {

	int add(EpLoginUser elu);
	
	String getCookieByUserId(String userId);

}
