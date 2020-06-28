package com.example.boke.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.boke.entity.Tag;

@Mapper
public interface TagService {
		 
	  int saveTag(HashMap<String, Object> map);
	  List<Tag> listTags(Integer startIndex, Integer pageSize);
	  int updataTag(HashMap<String, Object> map);
	  int delTag(int id);
}
