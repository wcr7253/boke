package com.example.boke.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.boke.Dao.BlogTagMapper;
import com.example.boke.service.BlogTagService;
@Service
public class BlogTagServiceImpl implements BlogTagService
{
	@Autowired
	BlogTagMapper BlogTag;

	@Override
	public int saveBlogTag(Integer blogId,Integer tagId)
	{
		// TODO Auto-generated method stub
		HashMap<String, Object> map=new  HashMap<String, Object>();
		map.put("blogId", blogId);
		map.put("tagId", blogId);
		return BlogTag.saveBlogTag(map);
	}

	@Override
	public List<com.example.boke.entity.BlogTag> listTag(int tagId)
	{
		// TODO Auto-generated method stub
		return BlogTag.listTag(tagId);
	}

	@Override
	public List<com.example.boke.entity.BlogTag> listTagByBlogId(int blogId)
	{
		// TODO Auto-generated method stub
		return BlogTag.listTagByBlogId(blogId);
	}

	


}
