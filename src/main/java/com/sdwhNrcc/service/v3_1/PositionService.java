package com.sdwhNrcc.service.v3_1;

import java.util.List;

import com.sdwhNrcc.entity.v3_1.*;

public interface PositionService {

	int add(Position position, String databaseName);

	/**
	 * ��ѯƽ̨ͬ����Աλ�õ���Ϣ�б�
	 * @param databaseName
	 * @return
	 */
	List<Position> queryELList(String databaseName);

}
