package com.sdwhNrcc.dao.v3_1;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v3_1.*;

public interface StaffMapper {

	int add(Staff staff);

	int edit(Staff staff);

	List<Staff> queryList();

	int getCountById(@Param("id")Integer id);

	List<Staff> queryEIList();
}
