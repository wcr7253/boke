package com.example.boke.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Util.MyUtil;
import com.example.boke.Dao.BlogMapper;
import com.example.boke.entity.Blog;
import com.example.boke.entity.User;
import com.example.boke.service.BlogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import af.sql.c3p0.AfSimpleDB;
@Service

public class BlogServiceImpl implements BlogService
{  
	
	@Autowired
	BlogMapper Blog;
	
	@Autowired
	UserServiceImpl UserImpl;
	
	@Autowired
	RedisTemplate redisTemplate;

	@Override
	public List<HashMap<String, Object>> listBlogs(HashMap<String, Object> map,Integer pageNumber,Integer blogSize)
	{
		System.out.println("当前页是："+pageNumber+"显示条数是："+blogSize);
		
		Map typesmap = redisTemplate.opsForHash().entries("TypeList");	
		
		 //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNumber,blogSize);
        
		List<Blog> blogList=Blog.listBlogs(map);
		
		  //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
        PageInfo<Blog> bloglist = new PageInfo<Blog>(blogList,blogSize);
        
        List<Blog> blogslist=bloglist.getList();
        
		Iterator<Blog> iter=blogslist.iterator();
		List<HashMap<String, Object>> blogsList=new ArrayList<HashMap<String, Object>>();
		while(iter.hasNext())
		{
			Blog blog=iter.next();
	
			HashMap<String, Object> blogmap=new HashMap<String, Object>();
			blogmap.put("id", blog.getId());
			blogmap.put("title", blog.getTitle());
			blogmap.put("isRecommand", blog.getIsRecommand()? "是":"否" );
			blogmap.put("creatTime",MyUtil.data2str( blog.getCreatTime())); 
			blogmap.put("typeName",typesmap.get(blog.getTypeId()) ); 
			blogsList.add(blogmap);
		} 
		
		return blogsList;
	}

	
	@Override
	public int saveblogs(com.example.boke.entity.Blog blog)
	{
		// TODO Auto-generated method stub
		return Blog.saveblogs(blog);
	}


	@Override
	public com.example.boke.entity.Blog getBlogs(int blogId)
	{
		// TODO Auto-generated method stub
		return Blog.getBlogs(blogId);
	}
	

	@Override
	public int delBlog(int id)
	{
		// TODO Auto-generated method stub
		return Blog.delBlog(id);
	}

	@Override
	public List<com.example.boke.entity.Blog> listType(int typeId)
	{
		// TODO Auto-generated method stub
		return Blog.listType(typeId);
	}

	@Override
	public com.example.boke.entity.Blog getBlogsForP(int blogId)
	{
		// TODO Auto-generated method stub
		return Blog.getBlogsForP(blogId);
	}
	
	@Override
	public Object listBlog(Integer pageNumber,Integer pageSize, String blogName,Integer isPublished,Integer typeId,Integer T)
	{
		HashMap<String, Object> map=new HashMap<String, Object>();
		if(blogName != null)
			map.put("titleForSearch",blogName);
		if(typeId != null)
			map.put("typeId",typeId);
		map.put("isPublished",isPublished);
			
		 //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNumber,pageSize);
        
		List<Blog> listb=Blog.listBlogs(map);
		
		//3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
        PageInfo<Blog> Bloglist = new PageInfo<Blog>(listb,pageSize);
        
        if(T == 0)
        	return Bloglist.getList();
        List<Blog> listB=Bloglist.getList();
		List<HashMap<String, Object>> listBlogs = new ArrayList<>();
		for (int i = 0; i < listB.size(); i++)
		{
			Blog b = listB.get(i);
			HashMap<String, Object> m = new HashMap<String, Object>();

			m.put("id", b.getId());
			m.put("title", b.getTitle());
			m.put("description", b.getDescription());

			User user = UserImpl.getUserById(b.getUserId());
			m.put("name", user.getNickname());
			m.put("updataTime", MyUtil.data2str(b.getUpdataTime()));
			m.put("views", b.getViews());

			// Map typesmap = redisTemplate.opsForHash().entries("TypeList");
			m.put("type", (String) redisTemplate.opsForHash().get("TypeList", b.getTypeId()));
			listBlogs.add(m);
		}
		
		return listBlogs;
	}


	@Override
	public List<HashMap<String, Object>> newSmallBlogList()
	{
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
			return newSmallBlogList;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}


}
