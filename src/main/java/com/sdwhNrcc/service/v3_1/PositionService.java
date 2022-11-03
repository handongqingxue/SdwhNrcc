package com.sdwhNrcc.service.v3_1;

import java.util.List;

import com.sdwhNrcc.entity.v3_1.*;

public interface PositionService {

	int add(Position position, String databaseName);

	/**
	 * 查询平台同步人员位置的信息列表
	 * @param databaseName
	 * @return
	 */
	List<Position> queryELList(String databaseName);

}
