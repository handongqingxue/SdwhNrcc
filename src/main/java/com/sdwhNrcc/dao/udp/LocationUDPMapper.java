package com.sdwhNrcc.dao.udp;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.udp.*;

public interface LocationUDPMapper {

	int getCountByUid(@Param("uid")String uid, @Param("tenantId")String tenantId);

	int add(@Param("locationUDP")LocationUDP locationUDP);

	int edit(@Param("locationUDP")LocationUDP locationUDP);

	List<LocationUDP> queryELList();
}
