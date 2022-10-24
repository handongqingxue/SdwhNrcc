package com.sdwhNrcc.service.v1_3;

import java.util.List;

import com.sdwhNrcc.entity.v1_3.Tag;

public interface TagService {

	int add(List<Tag> tagList);

	List<Tag> select();

}
