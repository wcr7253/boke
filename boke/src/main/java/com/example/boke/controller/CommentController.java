package com.example.boke.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.example.af.spring.AfRestData;
import com.example.boke.entity.Comment;
import com.example.boke.entity.User;
import com.example.boke.service.impl.BlogServiceImpl;
import com.example.boke.service.impl.CommentServiceImpl;

@Controller
@RequestMapping("/boke/comment")
public class CommentController
{
	@Autowired
	CommentServiceImpl CmtImpl;
	
	@PostMapping("/savecomment.do")
	public Object savecomment(@RequestBody JSONObject j,HttpSession session )
	{
		CmtImpl.savecomment(j,session);
		return new AfRestData("");
	}
}
