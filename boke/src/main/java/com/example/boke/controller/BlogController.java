package com.example.boke.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Util.CommentReplyUtil;
import com.example.Util.MarkdownUtil;
import com.example.Util.MyUtil;
import com.example.af.spring.AfRestError;
import com.example.boke.entity.Blog;
import com.example.boke.entity.BlogTag;
import com.example.boke.entity.Comment;
import com.example.boke.entity.Type;
import com.example.boke.entity.User;
import com.example.boke.service.impl.BlogServiceImpl;
import com.example.boke.service.impl.BlogTagServiceImpl;
import com.example.boke.service.impl.CommentServiceImpl;
import com.example.boke.service.impl.UserServiceImpl;

import af.sql.c3p0.AfSimpleDB;

@Controller
@RequestMapping("/boke/user")
public class BlogController
{
	@Autowired
	RedisTemplate redisTemplate;

	@Autowired
	BlogServiceImpl BlogImpl;

	@Autowired
	BlogTagServiceImpl BlogTagImpl;

	@Autowired
	UserServiceImpl UserImpl;

	@Autowired
	CommentServiceImpl CmtImpl;
	
	@GetMapping("/index")
	public String index(Model model, Integer pageNumber, Integer blogSize, Integer method)
	{
		// 博客列表
		int i = 0;
		// 如果参数为null，则取默认值
		if (pageNumber == null)
			pageNumber = 1;
		else if (blogSize == 5 && method == 1)
			pageNumber += 1;
		else if (method == -1 && pageNumber > 1)
			pageNumber -= 1;

		int pageSize = 5;
		int startIndex = pageSize * (pageNumber - 1);

		// 查询
		List<HashMap<String, Object>> listBlogs = (List<HashMap<String, Object>>) BlogImpl.listBlog(startIndex,pageSize,null,1,null,1);
		
		model.addAttribute("listBlogs", listBlogs);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("blogSize", listBlogs.size());

		try
		{
			String s2 = "SELECT title FROM `blog`  where isPublished = 1 ";
			List<String[]> BlogCount = AfSimpleDB.query(s2);
			model.addAttribute("BlogCount", BlogCount.size());
		} catch (Exception e)
		{
			// TODO: handle exception
		}

		// 最新博客列表
		try
		{
			String s1 = "SELECT title,id FROM `blog`  where isPublished = 1  ORDER BY updataTime DESC LIMIT 0,5";
			List<String[]> newBlog = AfSimpleDB.query(s1);
			List<HashMap<String, Object>> newBlogList = new ArrayList<>();
			for (String[] s : newBlog)
			{
				HashMap<String, Object> m = new HashMap<String, Object>();
				m.put("title", s[0]);
				m.put("id", s[1]);
				newBlogList.add(m);
			}
			model.addAttribute("newBlogList", newBlogList);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 分类列表
		Set<Integer> Typekeys = redisTemplate.opsForHash().keys("TypeList");
		List<HashMap<String, Object>> listTypes = new ArrayList<>();
		Iterator<Integer> iterator = Typekeys.iterator();
		i = 0;
		while (iterator.hasNext() && i <= 5)
		{
			Integer key = iterator.next();
			HashMap<String, Object> m = new HashMap<String, Object>();

			List<Blog> lb = BlogImpl.listType(key);

			m.put("name", (String) redisTemplate.opsForHash().get("TypeList", key));
			m.put("Count", lb.size());
			m.put("id", key);
			
			listTypes.add(m);
			i = i + 1;
		}
		model.addAttribute("listTypes", listTypes);

		// 标签列表
		Set<Integer> Tagkeys = redisTemplate.opsForHash().keys("TagList");
		List<HashMap<String, Object>> listTags = new ArrayList<>();
		Iterator<Integer> iteratorTag = Tagkeys.iterator();
		i = 0;
		while (iteratorTag.hasNext() && i <= 8)
		{
			Integer key = iteratorTag.next();
			HashMap<String, Object> m = new HashMap<String, Object>();

			List<BlogTag> lb = BlogTagImpl.listTag(key);

			m.put("name", (String) redisTemplate.opsForHash().get("TagList", key));
			m.put("Count", lb.size());
			m.put("id", key);
			
			listTags.add(m);
			i = i + 1;
		}

		model.addAttribute("listTags", listTags);

		// 最新少博客列表
		List<HashMap<String, Object>> newSmallBlogList=BlogImpl.newSmallBlogList();
		model.addAttribute("newSmallBlogList", newSmallBlogList);

		return "boke/index";
	}

	@GetMapping("/search")
	public String search(Model model, String blogName)
	{
		int i = 0;
		// 查询
		List<HashMap<String, Object>> listBlogs = (List<HashMap<String, Object>>) BlogImpl.listBlog(0,1000,blogName,1,null,1);

		model.addAttribute("listBlogs", listBlogs);
		model.addAttribute("BlogCount", listBlogs.size());
		model.addAttribute("blogName", blogName);

		// 最新少博客列表
		List<HashMap<String, Object>> newSmallBlogList=BlogImpl.newSmallBlogList();
		model.addAttribute("newSmallBlogList", newSmallBlogList);

		return "boke/search";
	}

	@GetMapping("/blog")
	public String blog(Model model, Integer blogId) throws Exception
	{
		Blog blog=BlogImpl.getBlogs(blogId);
		
		model.addAttribute("Creator", UserImpl.getUserById(blog.getUserId()).getNickname());
		model.addAttribute("updataTime",MyUtil.data2str(blog.getUpdataTime()) ); 
		model.addAttribute("views", blog.getViews());
		model.addAttribute("blogId", blogId);
		model.addAttribute("flag", blog.getFlag());
		model.addAttribute("title", blog.getTitle());
		model.addAttribute("content", MarkdownUtil.markdownToHtmlExtensions(blog.getContent()));
		model.addAttribute("isAppreciation",blog.getIsAppreciation() ? 1 : 0 );
		
		List<BlogTag> BlogTaglist=BlogTagImpl.listTagByBlogId(blogId);
		List<HashMap<String, Object>> listTags=new ArrayList<HashMap<String,Object>>();
		for(BlogTag bt : BlogTaglist)
		{
			HashMap<String, Object> m=new HashMap<String, Object>();
			m.put("name",(String) redisTemplate.opsForHash().get("TagList", bt.getTagId()));
			listTags.add(m);
		}
		model.addAttribute("listTags", listTags);
		
		//评论区
		try {
			
		List<Comment> parentCommentList=CmtImpl.listcomment(blogId);
		List<Comment> commentlist=CommentReplyUtil.dealComment(parentCommentList);
		model.addAttribute("commentlist", commentlist);
		
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//增加一次views
		String s2 = " UPDATE blog SET `views`=`views`+1 WHERE id= "+blogId;
		AfSimpleDB.execute(s2);
		
		// 最新少博客列表
		List<HashMap<String, Object>> newSmallBlogList=BlogImpl.newSmallBlogList();
		model.addAttribute("newSmallBlogList", newSmallBlogList);
		
		
		return "boke/blog";
	}
}
