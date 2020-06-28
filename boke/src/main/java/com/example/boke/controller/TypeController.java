package com.example.boke.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.boke.entity.Blog;
import com.example.boke.entity.Type;
import com.example.boke.entity.User;
import com.example.boke.service.impl.BlogServiceImpl;
import com.example.boke.service.impl.TypeServiceImpl;
import com.example.boke.service.impl.UserServiceImpl;

import af.sql.c3p0.AfSimpleDB;

@Controller
@RequestMapping("/boke/user")
public class TypeController
{
	@Autowired
	RedisTemplate redisTemplate;
	
	@Autowired
	UserServiceImpl UserImpl;
	
	@Autowired
	TypeServiceImpl TypeImpl;
	
	@Autowired
	BlogServiceImpl BlogImpl;
	
	@GetMapping("/type")
	public String type(Model model,Integer typeId, Integer pageNumber, Integer blogSize, Integer method)
	{
		
		// 如果参数为null，则取默认值
		if (pageNumber == null)
			pageNumber = 1;
		else if (blogSize == 5 && method == 1)
			pageNumber += 1;
		else if (method == -1 && pageNumber > 1)
			pageNumber -= 1;

		int pageSize = 5;
		int startIndex = pageSize * (pageNumber - 1);
				
		// 如果参数为null，则取默认值
		if (typeId == null)
			typeId=1;
		
		List<Type> listtype=TypeImpl.listTypes(0,10000);
		List<Type> listType=new ArrayList<Type>();
		for(Type t : listtype)
		{
			List<Blog> listblog=BlogImpl.listType(t.getId());
			t.BlogCount=listblog.size();
			listType.add(t);
		}
		model.addAttribute("listType", listType);
		//
		
		//博客列表listBlog
	
		List<Blog> listblog= (List<Blog>) BlogImpl.listBlog(startIndex,pageSize,null,1,typeId,0);
		List<Blog> listBlog=new ArrayList<Blog>();
		for(Blog g : listblog)
		{
			User user=UserImpl.getUserById(g.getUserId());
			g.name=user.getNickname();
			listBlog.add(g);
		}
		model.addAttribute("listBlog", listBlog);
		
		model.addAttribute("typeId", typeId);
		model.addAttribute("typeName", (String) redisTemplate.opsForHash().get("TypeList",typeId));
		
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("blogSize", listBlog.size());
		
		// 最新少博客列表
		try
		{
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
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "boke/type";
	}
}
