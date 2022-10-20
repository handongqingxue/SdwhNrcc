package com.sdwhNrcc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.*;

public interface DutyMapper {

	int add(Duty duty);

	int getCountById(@Param("id")Integer id);

	int edit(Duty duty);

	List<Map<String, Object>> summaryOnlineDuty();

}
