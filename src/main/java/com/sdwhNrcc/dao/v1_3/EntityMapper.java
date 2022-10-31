package com.sdwhNrcc.dao.v1_3;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v1_3.*;

public interface EntityMapper {

	int add(@Param("entity")Entity entity, @Param("databaseName")String databaseName);

	int edit(@Param("entity")Entity entity, @Param("databaseName")String databaseName);

	List<Entity> queryList(@Param("databaseName")String databaseName);

	int getCountById(@Param("id")Integer id, @Param("databaseName")String databaseName);

	List<Entity> queryEIList(@Param("databaseName")String databaseName);

}
