package com.example.boke.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.boke.entity.Blog;
import com.example.boke.entity.BlogTag;
import com.example.boke.entity.Tag;
import com.example.boke.entity.Type;
import com.example.boke.service.impl.TagServiceImpl;
import com.example.boke.service.impl.TypeServiceImpl;

import af.sql.c3p0.AfSimpleDB;

@Controller
@RequestMapping("/boke/user")
public class aboutController
{
	@Autowired
	TagServiceImpl TagImpl;
	
	@Autowired
	TypeServiceImpl TypeImpl;
	
	@GetMapping("/about")
	public String about(Model model) throws Exception
	{
		
		
		List<Tag> listtag=TagImpl.listTags(0,10000);
		model.addAttribute("listTag", listtag);

		List<Type> listtype=TypeImpl.listTypes(0,10000);
		model.addAttribute("listType", listtype);
		
		
		// 最新少博客列表
		String s1 = "SELECT title,id  FROM `blog`  where isPublished = 1  ORDER BY updataTime DESC LIMIT 0,3";
		List<String[]> newBlog = AfSimpleDB.query(s1);
		List<HashMap<String, Object>> newSmallBlogList = new ArrayList<>();
		for (String[] s : newBlog)
		{
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("title", s[0]);
			m.put("id", s[1]);
			newSmallBlogList.add(m);
		}
		model.addAttribute("newSmallBlogList", newSmallBlogList);
		return "boke/about";
	}
}
