package com.example.boke.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Util.MyUtil;
import com.example.boke.entity.Blog;
import com.example.boke.service.impl.BlogServiceImpl;

import af.sql.c3p0.AfSimpleDB;

@Controller
@RequestMapping("/boke/user")
public class ArchivesController
{
	@Autowired
	RedisTemplate redisTemplate;
	
	@GetMapping("/archives")
	public String archives(Model model) throws Exception
	{
		Boolean flag=true;

		//总的博客数
		String s2 = "SELECT COUNT(*) FROM `blog`  where isPublished = 1";
		List<String[]> BlogCount=AfSimpleDB.query(s2);
		model.addAttribute("BlogCount", BlogCount.get(0)[0]);
		
		//按年份查询博客
		List<HashMap<String, Object>> listBlog=new ArrayList<>();
		
		String s3 = "SELECT * FROM `blog`  WHERE isPublished = 1 ORDER BY creatTime" ;
		List<Blog> AllBlog=AfSimpleDB.query(s3, Blog.class);
		List<Integer> year=new ArrayList<>();
		
		//找到有哪些年份
		for(Blog b : AllBlog)
		{
		
			if(year.size()==0)
				year.add( MyUtil.date2LocalDate(b.getCreatTime()).getYear());
			
			flag=true;
			for(Integer i : year)
			{
				if(i == MyUtil.date2LocalDate(b.getCreatTime()).getYear())
					flag=false;
			}
			if(flag==true)
				year.add( MyUtil.date2LocalDate(b.getCreatTime()).getYear());
		}
		
		for(Integer b : year)
		{
			HashMap<String, Object> m=new HashMap<String, Object>();
			
			//得到时间
			m.put("key", b);
			
			//按年份得到博客list
			String s4 = "SELECT * FROM `blog`  WHERE isPublished = 1 and year(creatTime)="+b +" ORDER BY creatTime" ;
			List<Blog> BlogByYearlist=AfSimpleDB.query(s4, Blog.class);
			
			m.put("listBlog", BlogByYearlist);
			listBlog.add(m);
		}
		model.addAttribute("listBlog", listBlog);
		
		
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
	
		return "boke/archives";
	}
}
