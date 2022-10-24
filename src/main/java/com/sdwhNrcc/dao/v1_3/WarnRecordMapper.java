package com.sdwhNrcc.dao.v1_3;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v1_3.*;

public interface WarnRecordMapper {

	int add(WarnRecord warnRecord);

	int getCountById(@Param("id")Integer id);

	List<WarnRecord> queryEAList(@Param("sync")int sync);

	int syncByIds(List<String> idList);

}
