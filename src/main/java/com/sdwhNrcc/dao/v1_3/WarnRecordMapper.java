package com.sdwhNrcc.dao.v1_3;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v1_3.*;

public interface WarnRecordMapper {

	int add(@Param("warnRecord")WarnRecord warnRecord, @Param("databaseName")String databaseName);

	int getCountById(@Param("id")Integer id, @Param("databaseName")String databaseName);

	List<WarnRecord> queryEAList(@Param("sync")int sync, @Param("databaseName")String databaseName);

	int syncByIds(@Param("idList")List<String> idList, @Param("databaseName")String databaseName);

}
