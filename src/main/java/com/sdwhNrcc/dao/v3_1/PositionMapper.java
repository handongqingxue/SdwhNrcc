package com.sdwhNrcc.dao.v3_1;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v1_3.Location;

public interface PositionMapper {

	int add(Location location);

	int edit(Location location);

	int getCountByUid(@Param("uid")String uid);

	List<Location> queryELList();
}
