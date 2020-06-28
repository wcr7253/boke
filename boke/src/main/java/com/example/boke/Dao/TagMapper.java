package com.example.boke.Dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.boke.entity.Tag;

@Mapper
public interface TagMapper {
		 
	  int saveTag(HashMap<String, Object> map);
	  List<Tag> listTags(HashMap<String, Object> map);
	  int updataTag(HashMap<String, Object> map);
	  int delTag(int id);
}
