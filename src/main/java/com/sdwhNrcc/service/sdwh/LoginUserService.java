package com.sdwhNrcc.service.sdwh;

import java.util.List;

import com.sdwhNrcc.entity.sdwh.LoginUser;
import com.sdwhNrcc.entity.v1_3.*;

public interface LoginUserService {
	
	int add(LoginUser lu);

	String getTokenByUsername(String username);
	
}
