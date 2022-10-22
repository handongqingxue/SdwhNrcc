package com.sdwhNrcc.dao;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.*;

public interface EpLoginUserMapper {

	int add(EpLoginUser elu);

	int edit(EpLoginUser elu);

	String getCookieByUserId(@Param("userId")String userId);

	int getCountByUserId(@Param("userId")String userId);

}
