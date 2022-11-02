package com.sdwhNrcc.dao.v3_1;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v3_1.*;

public interface DeptMapper {

	int add(@Param("dept")Dept dept, @Param("databaseName")String databaseName);

	int edit(@Param("dept")Dept dept, @Param("databaseName")String databaseName);

	int getCountByDeptId(@Param("deptId")Integer deptId, @Param("databaseName")String databaseName);
}
