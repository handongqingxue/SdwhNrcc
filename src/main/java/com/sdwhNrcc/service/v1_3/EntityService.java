package com.sdwhNrcc.service.v1_3;

import java.util.List;

import com.sdwhNrcc.entity.v1_3.*;

public interface EntityService {

	int add(List<Entity> entityList);

	List<Entity> queryList();

	/**
	 * 查询省平台所需的员工数据
	 * @return
	 */
	List<Entity> queryEIList();

}
