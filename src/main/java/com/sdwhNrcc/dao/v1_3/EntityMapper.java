package com.sdwhNrcc.dao.v1_3;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v1_3.*;

public interface EntityMapper {

	int add(Entity entity);

	int edit(Entity entity);

	List<Entity> queryList(@Param("databaseName")String databaseName);

	int getCountById(@Param("id")Integer id);

	List<Entity> queryEIList();

}
