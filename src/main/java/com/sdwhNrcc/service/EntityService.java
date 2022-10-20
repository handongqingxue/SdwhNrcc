package com.sdwhNrcc.service;

import java.util.List;

import com.sdwhNrcc.entity.*;

public interface EntityService {

	int add(List<Entity> entityList);

	List<Entity> queryList();

}
