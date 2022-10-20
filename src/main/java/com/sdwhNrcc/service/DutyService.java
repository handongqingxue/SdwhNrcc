package com.sdwhNrcc.service;

import java.util.List;
import java.util.Map;

import com.sdwhNrcc.entity.*;

public interface DutyService {

	int add(List<Duty> dutyList);

	List<Map<String, Object>> summaryOnlineDuty();

}
