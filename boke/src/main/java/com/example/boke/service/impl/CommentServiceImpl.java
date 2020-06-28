package com.example.boke.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.boke.Dao.CommentMapper;
import com.example.boke.entity.Comment;
import com.example.boke.entity.User;
import com.example.boke.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService
{
	@Autowired
	CommentMapper Comment;

	@Override
	public int savecomment(JSONObject j,HttpSession session)
	{
		Comment comment=new Comment();
		comment.setContent(j.getString("content").trim());
		comment.setNickname(j.getString("nickname").trim());
		comment.setEmail(j.getString("email").trim());
		comment.setCommentId(j.getInteger("commentId"));
		comment.setBlogId(j.getInteger("blogId"));
		comment.setCreateTime(new Date());
		
		if( (User) session.getAttribute("user") != null)
		{
			comment.setIsAdmintor(1);
		}else
		{
			comment.setIsAdmintor(0);
		}
		
		return Comment.savecomment(comment);
	}

	@Override
	public List<com.example.boke.entity.Comment> listcomment(int blogId)
	{
		// TODO Auto-generated method stub
		return Comment.listcomment(blogId);
	}


	@Override
	public String getCommentName(int id)
	{
		// TODO Auto-generated method stub
		return Comment.getCommentName(id);
	}


}
