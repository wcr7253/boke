package com.example.boke.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.boke.entity.Blog;

@Mapper
public interface BlogService {
		 
	  List<HashMap<String, Object>> listBlogs(HashMap<String, Object> map);
	  Object listBlog(Integer startIndex,Integer pageSize, String blogName,Integer isPublished,Integer typeId,Integer T);
	  int saveblogs(Blog blog);
	  Blog getBlogs(int blogId);
	  Blog getBlogsForP(int blogId);
	  int delBlog(int id);
	  List<Blog> listType(int typeId);
	  List<HashMap<String, Object>> newSmallBlogList();
}
