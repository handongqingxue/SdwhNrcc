package com.sdwhNrcc.service.v3_1;

import java.util.List;

import com.sdwhNrcc.entity.v3_1.*;

public interface StaffService {

	int add(List<Staff> staffList, String databaseName);

	List<Staff> queryList(String databaseName);

	/**
	 * 查询省平台所需的员工数据
	 * @return
	 */
	List<Staff> queryEIList(String databaseName);

}
