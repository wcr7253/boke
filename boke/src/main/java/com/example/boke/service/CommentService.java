package com.example.boke.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Mapper;

import com.alibaba.fastjson.JSONObject;
import com.example.boke.entity.Blog;
import com.example.boke.entity.Comment;

@Mapper
public interface CommentService {
		 
	  int savecomment(JSONObject j,HttpSession session);
	  List<Comment> listcomment(int blogId);
	  String getCommentName(int id);
}
