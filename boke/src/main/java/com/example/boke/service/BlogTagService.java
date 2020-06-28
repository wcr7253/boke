package com.example.boke.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import com.example.boke.entity.Blog;
import com.example.boke.entity.BlogTag;

@Service
public interface BlogTagService {
		 
	  int saveBlogTag(Integer blogId,Integer tagId);
	  List<BlogTag> listTag(int tagId);
	  List<BlogTag> listTagByBlogId(int blogId);
}
