package com.example.boke.controller;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.boke.entity.Blog;
import com.example.boke.entity.Photo;
import com.example.boke.entity.Tag;
import com.example.boke.entity.Type;

import com.alibaba.fastjson.JSONObject;
import com.example.Util.MyUtil;
import com.example.af.spring.AfRestData;
import com.example.boke.entity.User;
import com.example.boke.service.impl.BlogServiceImpl;
import com.example.boke.service.impl.BlogTagServiceImpl;
import com.example.boke.service.impl.PhotoServiceImpl;
import com.example.boke.service.impl.TagServiceImpl;
import com.example.boke.service.impl.TypeServiceImpl;

import af.sql.c3p0.AfSimpleDB;

@Controller
@RequestMapping("/boke/admin")
public class AdminController
{
	@Autowired
	BlogServiceImpl BlogImpl;
	
	@Autowired
	BlogTagServiceImpl BlogTagImpl;
	
	@Autowired
	TypeServiceImpl TypeImpl;
	
	@Autowired
	TagServiceImpl TagImpl;
	
	@Autowired
	PhotoServiceImpl photoImpl;
	
	@Autowired
	RedisTemplate redisTemplate;
	
	//登录
	@GetMapping("/login")
	public String login()
	{
		
		return "boke/admin/login";
	}
	
	@PostMapping("/login.do")
	public Object logindo(@RequestBody JSONObject j)
	{
		String username=j.getString("loginUsername").trim();
		String password=j.getString("loginPassword").trim();
		
		Subject subject=SecurityUtils.getSubject();

		try
		{
			subject.login(new UsernamePasswordToken(username,password));
		}
		catch(IncorrectCredentialsException e){
			 Session session = subject.getSession();
		     //把用户信息保存到session
		     session.removeAttribute("user");
		     System.out.println("user移除session");
		     
			throw new IncorrectCredentialsException("密码错误！");
			
		}catch(AuthenticationException e) {
			 Session session = subject.getSession();
		     //把用户信息保存到session
		     session.removeAttribute("user");
		     System.out.println("user移除session");
		     
		 	throw new AuthenticationException("验证失败");
			
		}
	
		return new AfRestData("");
	}
	
	@GetMapping("/loginout")
	public String loginout()
	{
		Subject subject = SecurityUtils.getSubject(); 
		subject.logout();
		return "boke/admin/login";
	}
	
	//博客管理
	@GetMapping("/blogs")
	public String blogs(Model model,String titleForSearch,String typeId
							,Integer pageNumber,Integer blogSize,Integer method)
	{	
		
		// 如果参数为null，则取默认值
		if (pageNumber == null)
			pageNumber = 1;
		else if(blogSize==10 && method==1)
			pageNumber+=1;
		else if(method==-1 && pageNumber>1)
			pageNumber-=1;
		
		int pageSize = 10;
		int startIndex = pageSize * (pageNumber - 1);
		
		// 查询
		HashMap<String, Object> Searchmap=new HashMap<String, Object>();
		Searchmap.put("startIndex", startIndex);
		Searchmap.put("pageSize", pageSize);
				
		//获取全部分类
		Map typesmap = redisTemplate.opsForHash().entries("TypeList");	
		List<Type> typesList=new ArrayList<Type>();
		int i=0;
		for(i=1;i<=typesmap.size();i++)
		{
			String s=(String)typesmap.get(i);
			if((String)typesmap.get(i) != null)
			{
				Type t=new Type();
				t.setId(i);
				t.setName((String)typesmap.get(i));
				typesList.add(t);
			}
		}
		model.addAttribute("typesList", typesList);
		
		//博客展现
		if(titleForSearch==null && typeId==null )
		{
			List<HashMap<String, Object>> blogsList=BlogImpl.listBlogs(Searchmap);

			model.addAttribute("blogsList", blogsList);
			model.addAttribute("pageNumber", pageNumber);
			model.addAttribute("blogSize", blogsList.size());
		}
		else
		{
			if(typeId != "")
			{
				
				Searchmap.put("typeId",Integer.valueOf(typeId).intValue());
				
			}
			
			Searchmap.put("titleForSearch",titleForSearch);

			List<HashMap<String, Object>> blogsList=BlogImpl.listBlogs(Searchmap);
			
			model.addAttribute("blogsList", blogsList);
			model.addAttribute("titleForSearch", titleForSearch);
		}
		
		//最新少博客列表
		try
		{
			String s1 = "SELECT title,id FROM `blog`  where isPublished = 1  ORDER BY updataTime DESC LIMIT 0,3";
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
				
		return "boke/admin/blogs";
	}
	
	
	//博客发布
	@GetMapping("/blogsinput")
	public String blogsinput(Model model,String blogId)
	{
		if(blogId != null)
		{
			Blog b=BlogImpl.getBlogs(Integer.parseInt(blogId));
			model.addAttribute("flag",b.getFlag()); 	
			model.addAttribute("title",b.getTitle()); 
			model.addAttribute("content",b.getContent());
			model.addAttribute("typeId",b.getTypeId());
			model.addAttribute("description",b.getDescription());
			
			//获取全部分类
			Map typesmap = redisTemplate.opsForHash().entries("TypeList");	
			List<Type> typesList=new ArrayList<Type>();
			int i=0;
			for(i=1;i<=typesmap.size();i++)
			{
				if((String)typesmap.get(i) != null)
				{
					Type t=new Type();
					t.setId(i);
					t.setName((String)typesmap.get(i));
					typesList.add(t);
				}
			}
			model.addAttribute("typesList", typesList);
			
			//获取全部标签
			Map tagsmap = redisTemplate.opsForHash().entries("TagList");	
			List<Tag> tagsList=new ArrayList<Tag>();
			for(i=1;i<=tagsmap.size();i++)
			{
				if((String)tagsmap.get(i) != null)
				{
					Tag t=new Tag();
					t.setId(i);
					t.setName((String)tagsmap.get(i));
					tagsList.add(t);
				}
			}
			model.addAttribute("tagsList", tagsList);
			model.addAttribute("blogId", blogId);
			

			//最新少博客列表
			try
			{
				String s1 = "SELECT title,id FROM `blog`  where isPublished = 1  ORDER BY updataTime DESC LIMIT 0,3";
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
			
			return "boke/admin/blogs-input-edit";
		}
		//获取全部分类
		Map typesmap = redisTemplate.opsForHash().entries("TypeList");	
		List<Type> typesList=new ArrayList<Type>();
		int i=0;
		for(i=1;i<=typesmap.size();i++)
		{
			if((String)typesmap.get(i) != null)
			{
				Type t=new Type();
				t.setId(i);
				t.setName((String)typesmap.get(i));
				typesList.add(t);
			}
		}
		model.addAttribute("typesList", typesList);
		
		//获取全部标签
		Map tagsmap = redisTemplate.opsForHash().entries("TagList");	
		List<Tag> tagsList=new ArrayList<Tag>();
		for(i=1;i<=tagsmap.size();i++)
		{
			if((String)tagsmap.get(i) != null)
			{
				Tag t=new Tag();
				t.setId(i);
				t.setName((String)tagsmap.get(i));
				tagsList.add(t);
			}
		}
		model.addAttribute("tagsList", tagsList);
		

		//最新少博客列表
		try
		{
			String s1 = "SELECT title,id FROM `blog`  where isPublished = 1  ORDER BY updataTime DESC LIMIT 0,3";
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
		return "boke/admin/blogs-input";
	}
	
	
	@PostMapping("/savaBlogs.do")
	public Object saveBlog(@RequestBody JSONObject j,HttpSession session)
	{
		try
		{
			String bId=j.getString("blogId").trim();
			if(bId != null)
			{
				int blogId=Integer.valueOf(bId).intValue();
				photoImpl.updataPhotoForDel(blogId);
				BlogImpl.delBlog(blogId);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		User user = (User) session.getAttribute("user");

		Blog blog = new Blog();
		blog.setTitle(j.getString("title").trim());
		blog.setContent(j.getString("content").trim());
		blog.setFlag(j.getString("flag").trim());
		blog.setViews(0);
		blog.setIsAppreciation(j.getInteger("appreciation") == 1 ? true : false);
		blog.setIsShareStatrment(j.getInteger("shareInfo") == 1 ? true : false);
		blog.setIsCommentabled(j.getInteger("comment") == 1 ? true : false);
		blog.setIsRecommand(j.getInteger("recommand") == 1 ? true : false);
		blog.setIsPublished(false);
		blog.setCreatTime(new Date());
		blog.setUpdataTime(new Date());
		blog.setTypeId(j.getInteger("typeId"));
		blog.setUserId(user.getId());
		blog.setFirstPictureId(j.getString("photoId").trim());
		blog.setDescription(j.getString("description").trim());
		BlogImpl.saveblogs(blog);

		try
		{
			String tag = j.getString("tagId").trim();
			List<Integer> tagList = MyUtil.tag(tag);
			for (int i = 0; i < tagList.size(); i++)
			{
				BlogTagImpl.saveBlogTag(blog.getId(), tagList.get(i));
			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}

		return new AfRestData("");
		
	}
	
	@PostMapping("/publishedBlogs.do")
	public Object publishedBlogs(@RequestBody JSONObject j,HttpSession session)
	{
		try
		{
			String bId=j.getString("blogId").trim();
			if(bId != null)
			{
				int blogId=Integer.valueOf(bId).intValue();
				photoImpl.updataPhotoForDel(blogId);
				BlogImpl.delBlog(blogId);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	
		
		User user=(User) session.getAttribute("user");
 		
		Blog blog=new Blog();
		blog.setTitle(j.getString("title").trim());
		blog.setContent(j.getString("content").trim());
		blog.setFlag(j.getString("flag").trim());
		blog.setViews(0);
		blog.setIsAppreciation(j.getInteger("appreciation") == 1 ? true : false);
		blog.setIsShareStatrment(j.getInteger("shareInfo") == 1 ? true : false);
		blog.setIsCommentabled(j.getInteger("comment") == 1 ? true : false);
		blog.setIsRecommand(j.getInteger("recommand") == 1 ? true : false);
		blog.setIsPublished(true);
		blog.setCreatTime(new Date());
		blog.setUpdataTime(new Date());
		blog.setTypeId(j.getInteger("typeId") );
		blog.setUserId(user.getId());
		blog.setFirstPictureId(j.getString("photoId").trim());
		blog.setDescription(j.getString("description").trim());
		BlogImpl.saveblogs(blog);
		
		try {
			String tag=j.getString("tagId").trim();
			List<Integer> tagList=MyUtil.tag(tag);
			for(int i=0;i<tagList.size();i++)
			{
				BlogTagImpl.saveBlogTag(blog.getId(),tagList.get(i));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		try
		{
			//把照片移动到目标文件夹
			Photo photo=photoImpl.getPhoto(j.getInteger("photoId"));
			movePhoto(photo);
			
			
			HashMap<String, Object> photoMap=new HashMap<String, Object>();
			photoMap.put("blogId", blog.getId());
			photoMap.put("photoId", j.getInteger("photoId"));
			
			photoImpl.updataPhoto(photoMap);
		} catch (Exception e){}
		
	
		return new AfRestData("");
		
	}
	
	@PostMapping("/deleteblog.do")
	public Object deleteblog(@RequestBody JSONObject j)
	{
		int blogId=j.getInteger("id");
	
		photoImpl.updataPhotoForDel(blogId);
		BlogImpl.delBlog(blogId);
		return new AfRestData("");
	}
	//分类管理
	
	@GetMapping("/types")
	public String types(Model model,Integer pageNumber,Integer typeSize,Integer method)
	{
		// 如果参数为null，则取默认值
		if (pageNumber == null)
			pageNumber = 1;
		else if(typeSize==10 && method==1)
			pageNumber+=1;
		else if(method==-1 && pageNumber>1)
			pageNumber-=1;
		
		int pageSize = 10;
		int startIndex = pageSize * (pageNumber - 1);
		// 查询
		List<Type> typesList=TypeImpl.listTypes(startIndex,pageSize);
		
	    Set<Integer> keys = redisTemplate.opsForHash().keys("TypeList");
	    Iterator<Integer>  iterator=keys.iterator();
	    while(iterator.hasNext())
	    {
	    	Integer key=iterator.next();
	    	redisTemplate.opsForHash().delete("TypeList",key);  
	    }
		//分类遍历 把标签存到Redis
		if(true)
		{
			List<Type> typeList=TypeImpl.listTypes(0,10000);
		
			Iterator<Type> iter=typeList.iterator();
			while(iter.hasNext())
			{
				Type t=iter.next();
				redisTemplate.opsForHash().put("TypeList", (Integer)t.getId(), (String)t.getName());
			}
		}
		 ///////////////
		
		model.addAttribute("typesList", typesList);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("typeSize", typesList.size());
		

		//最新少博客列表
		try
		{
			String s1 = "SELECT title,id FROM `blog`  where isPublished = 1  ORDER BY updataTime DESC LIMIT 0,3";
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
		
		return "boke/admin/types";
	}
	
	@GetMapping("/typeinput")
	public String typeinput(Model model)
	{

		//最新少博客列表
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
		
		return "boke/admin/types-input";
	}
	
	
	@PostMapping("/savetype.do")
	public Object savetype(@RequestBody JSONObject j)
	{
		String typeName=j.getString("typeName").trim();
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("name", typeName);
		
		int TypeId=TypeImpl.saveType(map);
		
		return new AfRestData("");
	}
	
	@PostMapping("/updatatype.do")
	public Object updatatype(@RequestBody JSONObject j)
	{
		int id=j.getInteger("id");
		String typeName=j.getString("name").trim();
		
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("name", typeName);
		
		TypeImpl.updataType(map);
				
		return new AfRestData("");
	}
	
	@PostMapping("/deletetype.do")
	public Object deletetype(@RequestBody JSONObject j)
	{
		int id=j.getInteger("id");
		TypeImpl.delType(id);
				
		return new AfRestData("");
	}
	
	//标签管理
	
	
	@GetMapping("/tags")
	public String tags(Model model,Integer pageNumber,Integer tagSize,Integer method)
	{
		// 如果参数为null，则取默认值
		if (pageNumber == null)
			pageNumber = 1;
		else if(tagSize==10 && method==1)
			pageNumber+=1;
		else if(method==-1 && pageNumber>1)
			pageNumber-=1;
		
		int pageSize = 10;
		int startIndex = pageSize * (pageNumber - 1);
		// 查询
	
		List<Tag> tagsList=TagImpl.listTags(startIndex,pageSize);
		
	    Set<Integer> keys = redisTemplate.opsForHash().keys("TagList");
	    Iterator<Integer>  iterator=keys.iterator();
	    while(iterator.hasNext())
	    {
	    	Integer key=iterator.next();
	    	redisTemplate.opsForHash().delete("TagList",key);  
	    }
		//标签遍历 把标签存到Redis
		if(true)
		{
			List<Tag> tagList=TagImpl.listTags(0,10000);
		
			Iterator<Tag> iter=tagList.iterator();
			while(iter.hasNext())
			{
				Tag t=iter.next();
				redisTemplate.opsForHash().put("TagList", (Integer)t.getId(), (String)t.getName());
			}
		}
		 ///////////////
		
		model.addAttribute("tagsList", tagsList);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("tagSize", tagsList.size());
		

		//最新少博客列表
		try
		{
			String s1 = "SELECT title,id FROM `blog`  where isPublished = 1  ORDER BY updataTime DESC LIMIT 0,3";
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
		
		return "boke/admin/tags";
	}
	
	
	@GetMapping("/taginput")
	public String taginput(Model model)
	{

		//最新少博客列表
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
		return "boke/admin/tags-input";
	}	
	
	@PostMapping("/savetag.do")
	public Object savetag(@RequestBody JSONObject j)
	{
		String tagName=j.getString("tagName").trim();
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("name", tagName);
		
		int TagId=TagImpl.saveTag(map);
		
		return new AfRestData("");
	}
	
	@PostMapping("/updatatag.do")
	public Object updatatag(@RequestBody JSONObject j)
	{
		int id=j.getInteger("id");
		String tagName=j.getString("name").trim();
		
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("name", tagName);
		
		TagImpl.updataTag(map);
		
		return new AfRestData("");
	}
	
	@PostMapping("/deletetag.do")
	public Object deletetag(@RequestBody JSONObject j)
	{
		int id=j.getInteger("id");
		TagImpl.delTag(id);
		return new AfRestData("");
	}
	
	private void movePhoto(Photo photo) throws Exception 
	{
		// 决定把文件存放在哪儿
		File tmpDir = new File("c:/tmp");
		// 决定文件的名称
		String realName = photo.getRealName();
		File tmpFile = new File(tmpDir, realName);
		
		File storeDir = new File(tmpDir, photo.getStorePath());
		File storeFile = new File(storeDir, photo.getName());
		FileUtils.moveFile(tmpFile, storeFile);
	}
}
