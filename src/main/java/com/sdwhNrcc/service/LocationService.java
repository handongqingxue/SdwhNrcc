package com.sdwhNrcc.service;

import java.util.List;

import com.sdwhNrcc.entity.*;

public interface LocationService {

	int add(Location location);

	List<Location> queryELList();

}
