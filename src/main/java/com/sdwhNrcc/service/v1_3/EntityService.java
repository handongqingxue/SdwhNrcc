package com.sdwhNrcc.service.v1_3;

import java.util.List;

import com.sdwhNrcc.entity.v1_3.*;

public interface EntityService {

	int add(List<Entity> entityList);

	List<Entity> queryList();

	/**
	 * ��ѯʡƽ̨�����Ա������
	 * @return
	 */
	List<Entity> queryEIList();

}
