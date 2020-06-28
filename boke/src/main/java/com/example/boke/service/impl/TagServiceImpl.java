package com.example.boke.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.boke.Dao.TagMapper;
import com.example.boke.service.TagService;

@Service
public class TagServiceImpl implements TagService
{
	@Autowired
	TagMapper Tag;

	@Override
	public int saveTag(HashMap<String, Object> map)
	{
		return Tag.saveTag(map);
	}

	@Override
	public List<com.example.boke.entity.Tag> listTags(Integer startIndex,Integer pageSize)
	{
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("startIndex", startIndex);
		map.put("pageSize", pageSize);
		
		return Tag.listTags(map);
	}

	@Override
	public int updataTag(HashMap<String, Object> map)
	{

		return Tag.updataTag(map);
	}

	@Override
	public int delTag(int id)
	{
		// TODO Auto-generated method stub
		return Tag.delTag(id);
	}

	
	

}
