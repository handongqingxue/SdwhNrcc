package com.sdwhNrcc.dao.v3_1;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v3_1.*;

public interface PositionMapper {

	int add(Position position);

	int edit(Position position);

	int getCountByTagId(@Param("tagId")String tagId);

	List<Position> queryELList();
}
