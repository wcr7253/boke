package com.example.boke.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.boke.Dao.TypeMapper;
import com.example.boke.entity.Blog;
import com.example.boke.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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
	public List<com.example.boke.entity.Type> listTypes(Integer pageNumber,Integer pageSize)
	{
	   //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNumber,pageSize);
        
        List<com.example.boke.entity.Type> TypeList= Type.listTypes();
        
        //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
        PageInfo<com.example.boke.entity.Type> typelist = new PageInfo<com.example.boke.entity.Type>(TypeList,pageSize);
        
		return typelist.getList();
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
