package com.sdwhNrcc.dao;

import org.apache.ibatis.annotations.Param;

public interface EpLoginUserMapper {

	String getCookieByUserId(@Param("userId")String userId);

}
