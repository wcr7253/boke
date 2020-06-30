package com.example.boke.Dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.boke.entity.Type;
import com.example.boke.entity.User;

@Mapper
public interface TypeMapper {
		 
	  int saveType(HashMap<String, Object> map);
	  List<Type> listTypes();
	  int updataType(HashMap<String, Object> map);
	  int delType(int id);
	  Type getTypesByid(int id);
	  Type getTypesByName(String name);
}
