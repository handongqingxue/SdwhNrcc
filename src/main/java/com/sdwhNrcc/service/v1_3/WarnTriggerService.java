package com.sdwhNrcc.service.v1_3;

import java.util.List;

import com.sdwhNrcc.entity.v1_3.*;

public interface WarnTriggerService {

	int add(List<WarnTrigger> warnTriggerList, String databaseName);

}
