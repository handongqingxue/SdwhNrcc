package com.sdwhNrcc.service.v3_1;

import java.util.List;

import com.sdwhNrcc.entity.v3_1.*;

public interface StaffService {

	int add(List<Staff> staffList);

	List<Staff> queryList();

	/**
	 * ��ѯʡƽ̨�����Ա������
	 * @return
	 */
	List<Staff> queryEIList();

}
