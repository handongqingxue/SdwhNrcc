package com.sdwhNrcc.service.sdwh;

import com.sdwhNrcc.entity.sdwh.*;

public interface LoginUserCxSdService {

	int add(LoginUserCxSd lucs);

	String getTokenByUsername(String username);
}
