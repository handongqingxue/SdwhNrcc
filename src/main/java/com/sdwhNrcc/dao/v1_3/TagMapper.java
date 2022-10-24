package com.sdwhNrcc.dao.v1_3;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.v1_3.*;

public interface TagMapper {

	int add(Tag tag);

	int edit(Tag tag);

	List<Tag> select();

	int getCountById(@Param("id")String id);

}
