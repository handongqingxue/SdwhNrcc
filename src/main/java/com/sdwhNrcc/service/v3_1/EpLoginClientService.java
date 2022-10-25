package com.sdwhNrcc.service.v3_1;

import com.sdwhNrcc.entity.v3_1.*;

public interface EpLoginClientService {

	int add(EpLoginClient elc);
	
	String getTokenByClientId(String clientId);

}
