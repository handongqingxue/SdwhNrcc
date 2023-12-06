package com.sdwhNrcc.service.xinHuoAI;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.xinHuoAI.*;

public interface ReceiveDataService {

	int add(@Param("receiveData")ReceiveData receiveData, @Param("databaseName")String databaseName);
}
