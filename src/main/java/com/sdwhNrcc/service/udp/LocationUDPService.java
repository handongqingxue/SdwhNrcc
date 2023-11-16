package com.sdwhNrcc.service.udp;

import java.util.List;

import com.sdwhNrcc.entity.udp.*;

public interface LocationUDPService {

	int add(LocationUDP locationUDP);

	List<LocationUDP> queryELList();
}
