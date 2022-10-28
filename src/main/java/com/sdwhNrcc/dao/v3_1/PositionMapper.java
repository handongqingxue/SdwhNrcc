package com.sdwhNrcc.dao.v3_1;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v3_1.*;

public interface PositionMapper {

	int add(@Param("position")Position position, @Param("databaseName")String databaseName);

	int edit(@Param("position")Position position, @Param("databaseName")String databaseName);

	int getCountByTagId(@Param("tagId")String tagId, @Param("databaseName")String databaseName);

	List<Position> queryELList(@Param("databaseName")String databaseName);
}
