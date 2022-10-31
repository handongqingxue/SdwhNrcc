package com.sdwhNrcc.dao.v1_3;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v1_3.*;

public interface LocationMapper {

	int add(@Param("location")Location location, @Param("databaseName")String databaseName);

	int edit(@Param("location")Location location, @Param("databaseName")String databaseName);

	int getCountByUid(@Param("uid")String uid, @Param("databaseName")String databaseName);

	List<Location> queryELList(@Param("databaseName")String databaseName);

}
