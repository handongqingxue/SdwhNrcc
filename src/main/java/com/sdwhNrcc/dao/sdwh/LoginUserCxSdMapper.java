package com.sdwhNrcc.dao.sdwh;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.sdwh.*;

public interface LoginUserCxSdMapper {

	int add(LoginUserCxSd lucs);

	int edit(LoginUserCxSd lucs);

	String getTokenByUsername(@Param("username")String username);

	int getCountByUsername(@Param("username")String username);
}
