package com.sdwhNrcc.service.v1_3;

import java.util.List;

import com.sdwhNrcc.entity.v1_3.*;

public interface LocationService {

	int add(Location location);

	List<Location> queryELList();

}
