package com.sdwhNrcc.dao.xinHuoAI;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.xinHuoAI.*;

public interface ReceiveDataMapper {

	int add(@Param("receiveData")ReceiveData receiveData, @Param("databaseName")String databaseName);
}
