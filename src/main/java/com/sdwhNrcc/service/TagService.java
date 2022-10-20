package com.sdwhNrcc.service;

import java.util.List;

import com.sdwhNrcc.entity.Tag;

public interface TagService {

	int add(List<Tag> tagList);

	List<Tag> select();

}
