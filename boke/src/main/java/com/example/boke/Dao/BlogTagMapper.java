package com.example.boke.Dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.boke.entity.Blog;
import com.example.boke.entity.BlogTag;

@Mapper
public interface BlogTagMapper {
		 
	  int saveBlogTag(HashMap<String, Object> map);
	  List<BlogTag> listTag(int tagId);
	  List<BlogTag> listTagByBlogId(int blogId);
}
