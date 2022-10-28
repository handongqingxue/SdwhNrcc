package com.sdwhNrcc.dao.v3_1;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v3_1.*;

public interface StaffMapper {

	int add(@Param("staff")Staff staff, @Param("databaseName")String databaseName);

	int edit(@Param("staff")Staff staff, @Param("databaseName")String databaseName);

	List<Staff> queryList(@Param("databaseName")String databaseName);

	int getCountById(@Param("id")Integer id, @Param("databaseName")String databaseName);

	List<Staff> queryEIList(@Param("databaseName")String databaseName);
}
