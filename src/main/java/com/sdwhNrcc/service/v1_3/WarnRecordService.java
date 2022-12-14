package com.sdwhNrcc.service.v1_3;

import java.util.List;
import java.util.Map;

import com.sdwhNrcc.entity.v1_3.*;

public interface WarnRecordService {

	int add(List<WarnRecord> warnRecordList, String databaseName);

	List<WarnRecord> queryEAList(int sync, String databaseName);

	int syncByIds(String syncIds, String databaseName);

}
