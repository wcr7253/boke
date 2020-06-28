package com.example.boke.entity; 

import java.io.Serializable;
import java.util.Date; 

/** 本类由 POJO生成器 自动生成于 2020-05-31 09:46:50
    作者：阿发你好      官网: http://afanihao.cn 
*/ 

/** INSERT语句 ( 预处理方式 ) 
  INSERT INTO `blog_tag`
        (`blogId`, `tagId`) 
  VALUES(?, ?) 
*/ 

/** INSERT语句 ( MyBatis方式 ) 
  INSERT INTO `blog_tag`
        (`blogId`, `tagId`) 
  VALUES(#{blogId}, #{tagId}) 

  自增主键: 无
*/ 

public class BlogTag implements Serializable
{ 
 
	public Integer blogId ; 
	public Integer tagId ; 


	public void setBlogId(Integer blogId)
	{
		this.blogId=blogId;
	}
	public Integer getBlogId()
	{
		return this.blogId;
	}
	public void setTagId(Integer tagId)
	{
		this.tagId=tagId;
	}
	public Integer getTagId()
	{
		return this.tagId;
	}

} 
 