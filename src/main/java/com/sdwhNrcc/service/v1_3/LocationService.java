package com.sdwhNrcc.service.v1_3;

import java.util.List;

import com.sdwhNrcc.entity.v1_3.*;

public interface LocationService {

	int add(Location location, String databaseName);

	List<Location> queryELList(String databaseName);

}
