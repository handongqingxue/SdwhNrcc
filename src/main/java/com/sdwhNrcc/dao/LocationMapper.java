package com.sdwhNrcc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.*;

public interface LocationMapper {

	int add(Location location);

	int edit(Location location);

	int getCountByUid(@Param("uid")String uid);

	List<Location> queryELList();

}
