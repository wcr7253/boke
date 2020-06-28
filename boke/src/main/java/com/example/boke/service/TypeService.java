package com.example.boke.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.boke.entity.Type;
import com.example.boke.entity.User;

@Mapper
public interface TypeService {
		 
	  int saveType(HashMap<String, Object> map);
	  List<Type> listTypes(Integer startIndex,Integer pageSize);
	  int updataType(HashMap<String, Object> map);
	  int delType(int id);
	  Type getTypesByid(int id);
	  Type getTypesByName(String name);
}
