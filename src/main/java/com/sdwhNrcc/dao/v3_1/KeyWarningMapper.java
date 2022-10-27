package com.sdwhNrcc.dao.v3_1;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v3_1.*;

public interface KeyWarningMapper {

	int add(KeyWarning keyWarning);

	List<KeyWarning> queryEAList(@Param("sync")int sync);

	int syncByIds(List<String> idList);
}
