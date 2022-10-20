package com.sdwhNrcc.service;

import java.util.List;

import com.sdwhNrcc.entity.*;

public interface EntityService {

	int add(List<Entity> entityList);

	List<Entity> queryList();

	/**
	 * 查询省平台所需的员工数据
	 * @return
	 */
	List<Entity> queryEIList();

}
