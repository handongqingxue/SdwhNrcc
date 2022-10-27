package com.sdwhNrcc.service.v3_1;

import java.util.List;
import java.util.Map;

import com.sdwhNrcc.entity.v3_1.*;

public interface KeyWarningService {

	int add(KeyWarning keyWarning);

	List<KeyWarning> queryEAList(int sync);

	int syncByIds(String syncIds);

}
