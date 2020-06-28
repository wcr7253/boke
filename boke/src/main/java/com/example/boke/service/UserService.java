package com.example.boke.service;

import org.apache.ibatis.annotations.Mapper;

import com.example.boke.entity.User;

@Mapper
public interface UserService {
		 
	  User getUser(String username);
	  User getUserById(int id);
}
