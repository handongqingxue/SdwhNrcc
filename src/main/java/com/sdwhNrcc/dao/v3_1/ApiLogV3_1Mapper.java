package com.sdwhNrcc.dao.v3_1;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v3_1.*;

public interface ApiLogV3_1Mapper {

	int add(@Param("apiLog")ApiLog apiLog, @Param("databaseName")String databaseName);
}
