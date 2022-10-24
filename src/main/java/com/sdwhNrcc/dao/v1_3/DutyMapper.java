package com.sdwhNrcc.dao.v1_3;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v1_3.*;

public interface DutyMapper {

	int add(Duty duty);

	int getCountById(@Param("id")Integer id);

	int edit(Duty duty);

	List<Map<String, Object>> summaryOnlineDuty();

}
