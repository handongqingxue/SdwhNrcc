package com.sdwhNrcc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.*;

public interface EntityMapper {

	int add(Entity entity);

	int edit(Entity entity);

	List<Entity> queryList();

	int getCountById(@Param("id")Integer id);

	List<Entity> queryEIList();

}
