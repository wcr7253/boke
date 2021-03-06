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
import com.example.boke.entity.BlogTag;
import com.example.boke.entity.Tag;
import com.example.boke.entity.Type;
import com.example.boke.entity.User;
import com.example.boke.service.impl.BlogServiceImpl;
import com.example.boke.service.impl.BlogTagServiceImpl;
import com.example.boke.service.impl.TagServiceImpl;
import com.example.boke.service.impl.TypeServiceImpl;
import com.example.boke.service.impl.UserServiceImpl;

import af.sql.c3p0.AfSimpleDB;

@Controller
@RequestMapping("/boke/user")
public class TagController
{
	@Autowired
	RedisTemplate redisTemplate;
	
	@Autowired
	UserServiceImpl UserImpl;
	
	@Autowired
	TagServiceImpl TagImpl;
	
	@Autowired
	BlogTagServiceImpl BlogTagImpl;
	
	@Autowired
	BlogServiceImpl BlogImpl;
	
	@GetMapping("/tag")
	public String tag(Model model,Integer tagId ,Integer pageNumber, Integer blogSize, Integer method)
	{
		int i=0;
		// 如果参数为null，则取默认值
		if (pageNumber == null)
			pageNumber = 1;
		else if (blogSize == 3 && method == 1)
			pageNumber += 1;
		else if (method == -1 && pageNumber > 1)
			pageNumber -= 1;

		int pageSize = 3;
		int startIndex = pageSize * (pageNumber - 1);
		int endIndex=startIndex+pageSize;	
				
		// 如果参数为null，则取默认值
		if (tagId == null)
			tagId=1;
		
		{
			
			List<Tag> listtag=TagImpl.listTags(0,10000);
			List<Tag> listTag=new ArrayList<Tag>();
			for(Tag t : listtag)
			{
				List<BlogTag> listblogtag=BlogTagImpl.listTag(t.getId());
				t.BlogCount=listblogtag.size();
				listTag.add(t);
			}
			model.addAttribute("listTag", listTag);
		}
		

		//博客列表listBlog
		List<BlogTag> listtag=BlogTagImpl.listTag(tagId);
		List<Blog> listblog=new ArrayList<Blog>();
		for(BlogTag b : listtag)
		{
			Blog blog=BlogImpl.getBlogsForP(b.getBlogId());
			listblog.add(blog);
		}
		
		List<Blog> listBlog=new ArrayList<Blog>();
		if(endIndex>listblog.size())
			endIndex=listblog.size();
		for(i=startIndex;i<endIndex;i++)
		{
			Blog g=listblog.get(i);
			User user=UserImpl.getUserById(g.getUserId());
			g.name=user.getNickname();
			g.typeName=(String) redisTemplate.opsForHash().get("TypeList",g.getTypeId());
			listBlog.add(g);
		}
		model.addAttribute("listBlog", listBlog);
		model.addAttribute("tagId", tagId);
		
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("blogSize", listBlog.size());
		
		// 最新少博客列表
		List<HashMap<String, Object>> newSmallBlogList=BlogImpl.newSmallBlogList();
		model.addAttribute("newSmallBlogList", newSmallBlogList);

		return "boke/tags";
	}
}
