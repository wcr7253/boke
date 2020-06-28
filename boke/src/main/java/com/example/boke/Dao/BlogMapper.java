package com.example.boke.Dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.boke.entity.Blog;

@Mapper
public interface BlogMapper {
		 
	  List<Blog> listBlogs(HashMap<String, Object> map);
	  int saveblogs(Blog blog);
	  Blog getBlogs(int blogId);
	  Blog getBlogsForP(int blogId);
	  int delBlog(int id);
	  List<Blog> listType(int typeId);
}
