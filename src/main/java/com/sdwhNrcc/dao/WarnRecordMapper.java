package com.sdwhNrcc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.*;

public interface WarnRecordMapper {

	int add(WarnRecord warnRecord);

	int getCountById(@Param("id")Integer id);

	List<WarnRecord> queryEAList();

}
