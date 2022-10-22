package com.sdwhNrcc.service;

import java.util.List;

import com.sdwhNrcc.entity.*;

public interface LoginUserService {
	
	int add(LoginUser lu);

	String getTokenByUsername(String username);
	
}
