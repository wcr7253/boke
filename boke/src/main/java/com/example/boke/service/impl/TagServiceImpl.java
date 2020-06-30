package com.example.boke.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.boke.Dao.TagMapper;
import com.example.boke.service.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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
	public List<com.example.boke.entity.Tag> listTags(Integer pageNumber,Integer pageSize)
	{
		 //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNumber,pageSize);
		
        List<com.example.boke.entity.Tag> TagList= Tag.listTags();
        
        //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
        PageInfo<com.example.boke.entity.Tag> taglist = new PageInfo<com.example.boke.entity.Tag>(TagList,pageSize);
        
        
		return taglist.getList();
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
