package com.sdwhNrcc.dao.v1_3;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v1_3.*;

public interface TagMapper {

	int add(@Param("tag")Tag tag, @Param("databaseName")String databaseName);

	int edit(@Param("tag")Tag tag, @Param("databaseName")String databaseName);

	List<Tag> select(@Param("databaseName")String databaseName);

	int getCountById(@Param("id")String id, @Param("databaseName")String databaseName);

}
