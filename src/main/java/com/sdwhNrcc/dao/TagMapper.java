package com.sdwhNrcc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sdwhNrcc.entity.*;

public interface TagMapper {

	int add(Tag tag);

	int edit(Tag tag);

	List<Tag> select();

	int getCountById(@Param("id")String id);

}
