package com.sdwhNrcc.service.v1_3;

import java.util.List;
import java.util.Map;

import com.sdwhNrcc.entity.v1_3.*;

public interface DutyService {

	int add(List<Duty> dutyList);

	List<Map<String, Object>> summaryOnlineDuty();

}
