package com.sdwhNrcc.dao.v3_1;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v3_1.*;

public interface EpLoginClientMapper {

	int add(EpLoginClient elc);

	int edit(EpLoginClient elc);

	String getTokenByClientId(@Param("client_id")String client_id);

	int getCountByClientId(@Param("client_id")String client_id);
}
