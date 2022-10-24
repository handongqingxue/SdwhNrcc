package com.sdwhNrcc.dao.v1_3;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v1_3.*;

public interface LocationMapper {

	int add(Location location);

	int edit(Location location);

	int getCountByUid(@Param("uid")String uid);

	List<Location> queryELList();

}
