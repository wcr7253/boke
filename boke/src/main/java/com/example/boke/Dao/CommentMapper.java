package com.example.boke.Dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.boke.entity.Blog;
import com.example.boke.entity.Comment;

@Mapper
public interface CommentMapper {
		 
	  int savecomment(Comment comment);
	  List<Comment> listcomment(int blogId);
	  String getCommentName(int id);
}
