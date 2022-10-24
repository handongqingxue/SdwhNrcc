package com.sdwhNrcc.service.serviceImpl.v1_3;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdwhNrcc.dao.v1_3.*;
import com.sdwhNrcc.entity.v1_3.*;
import com.sdwhNrcc.service.v1_3.*;
import com.sdwhNrcc.util.*;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagMapper tagDao;

	@Override
	public int add(List<Tag> tagList) {
		// TODO Auto-generated method stub
		int count=0;
		for (Tag tag : tagList) {
			count=tagDao.getCountById(tag.getId());
			if(count==0)
				count=tagDao.add(tag);
			else
				count=tagDao.edit(tag);
		}
		return count;
	}

	@Override
	public List<Tag> select() {
		// TODO Auto-generated method stub
		return tagDao.select();
	}
}
