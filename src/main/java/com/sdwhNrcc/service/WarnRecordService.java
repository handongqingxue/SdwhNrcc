package com.sdwhNrcc.service;

import java.util.List;
import java.util.Map;

import com.sdwhNrcc.entity.*;

public interface WarnRecordService {

	int add(List<WarnRecord> warnRecordList);

	List<WarnRecord> queryEAList(int sync);

	int syncByIds(String syncIds);

}
