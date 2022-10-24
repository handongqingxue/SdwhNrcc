package com.sdwhNrcc.dao.v1_3;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v1_3.*;

public interface EpLoginUserMapper {

	int add(EpLoginUser elu);

	int edit(EpLoginUser elu);

	String getCookieByUserId(@Param("userId")String userId);

	int getCountByUserId(@Param("userId")String userId);

}
