package com.example.boke.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.boke.Dao.TypeMapper;
import com.example.boke.service.TypeService;

@Service
public class TypeServiceImpl implements TypeService
{
	@Autowired
	TypeMapper Type;
	
	@Override
	public int saveType(HashMap<String, Object> map)
	{
		// TODO Auto-generated method stub
		return Type.saveType(map);
	}

	@Override
	public int updataType(HashMap<String, Object> map)
	{
		// TODO Auto-generated method stub
		return Type.updataType(map);
	}

	@Override
	public int delType(int id)
	{
		// TODO Auto-generated method stub
		return Type.delType(id);
	}

	@Override
	public List<com.example.boke.entity.Type> listTypes(Integer startIndex,Integer pageSize)
	{
		HashMap<String ,Object> typemap=new HashMap<String, Object>();
		typemap.put("startIndex",startIndex);
		typemap.put("pageSize",pageSize);
		
		return Type.listTypes(typemap);
	}

	@Override
	public com.example.boke.entity.Type getTypesByid(int id)
	{
		// TODO Auto-generated method stub
		return Type.getTypesByid(id);
	}

	@Override
	public com.example.boke.entity.Type getTypesByName(String name)
	{
		// TODO Auto-generated method stub
		return Type.getTypesByName(name);
	}
	
	

}
