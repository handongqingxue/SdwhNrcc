package com.sdwhNrcc.dao.v1_3;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v1_3.*;

public interface DutyMapper {

	int add(@Param("duty")Duty duty, @Param("databaseName")String databaseName);

	int getCountById(@Param("id")Integer id, @Param("databaseName")String databaseName);

	int edit(@Param("duty")Duty duty, @Param("databaseName")String databaseName);

	List<Map<String, Object>> summaryOnlineDuty(@Param("databaseName")String databaseName);

}
