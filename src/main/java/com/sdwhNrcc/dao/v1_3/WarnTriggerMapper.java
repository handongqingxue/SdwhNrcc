package com.sdwhNrcc.dao.v1_3;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v1_3.*;

public interface WarnTriggerMapper {

	int add(@Param("warnTrigger")WarnTrigger warnTrigger, @Param("databaseName")String databaseName);

}
