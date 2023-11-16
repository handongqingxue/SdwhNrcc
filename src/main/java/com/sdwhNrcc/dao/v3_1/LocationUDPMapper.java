package com.sdwhNrcc.dao.v3_1;

import org.apache.ibatis.annotations.Param;

public interface LocationUDPMapper {

	int getCountByUid(@Param("uid")String uid, @Param("tenantId")String tenantId, @Param("databaseName")String databaseName);
}
