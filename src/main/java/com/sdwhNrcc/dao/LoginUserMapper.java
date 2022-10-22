package com.sdwhNrcc.dao;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.LoginUser;

public interface LoginUserMapper {

	int add(LoginUser lu);

	int edit(LoginUser lu);

	String getTokenByUsername(@Param("username")String username);

	int getCountByUsername(@Param("username")String username);

}
