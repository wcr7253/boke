package com.example.boke.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.boke.Dao.UserMapper;
import com.example.boke.entity.User;
import com.example.boke.service.UserService;
@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	UserMapper User;
	
	@Override
	public User getUser(String username)
	{
		// TODO Auto-generated method stub
		return User.getUser(username);
	}

	@Override
	public User getUserById(int id)
	{
		// TODO Auto-generated method stub
		return User.getUserById(id);
	}

}
