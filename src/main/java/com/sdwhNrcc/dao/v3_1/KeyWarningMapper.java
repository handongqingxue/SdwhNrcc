package com.sdwhNrcc.dao.v3_1;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v3_1.*;

public interface KeyWarningMapper {

	int add(@Param("keyWarning")KeyWarning keyWarning, @Param("databaseName")String databaseName);

	List<KeyWarning> queryEAList(@Param("sync")int sync, @Param("databaseName")String databaseName);

	int syncByIds(@Param("idList")List<String> idList, @Param("databaseName")String databaseName);
}
