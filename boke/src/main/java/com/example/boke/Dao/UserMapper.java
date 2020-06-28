package com.example.boke.Dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.boke.entity.User;

@Mapper
public interface UserMapper {
		 
	  User getUser(String username);
	  User getUserById(int id);
}
