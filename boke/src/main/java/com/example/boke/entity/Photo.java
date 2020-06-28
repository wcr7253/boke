package com.example.boke.entity; 

import java.io.Serializable;
import java.util.Date; 

/** 本类由 POJO生成器 自动生成于 2020-05-31 09:46:50
    作者：阿发你好      官网: http://afanihao.cn 
*/ 

/** INSERT语句 ( 预处理方式 ) 
  INSERT INTO `photo`
        (`id`, `guid`, `blogId`, `storePath`, `realName`, `suffix`, `name`, `timeCreate`) 
  VALUES(?, ?, ?, ?, ?, ?, ?, ?) 
*/ 

/** INSERT语句 ( MyBatis方式 ) 
  INSERT INTO `photo`
        (`id`, `guid`, `blogId`, `storePath`, `realName`, `suffix`, `name`, `timeCreate`) 
  VALUES(#{id}, #{guid}, #{blogId}, #{storePath}, #{realName}, #{suffix}, #{name}, #{timeCreate}) 

  自增主键: 无
*/ 

public class Photo implements Serializable
{ 
 
	public Long id ; 
	public int isdelete ; 	
	public String guid ; 
	public Integer blogId ; 
	public String storePath ; 
	public String realName ; 
	public String suffix ; 
	public String name ; 
	public Date timeCreate ; 


	public int getIsdelete()
	{
		return isdelete;
	}
	public void setIsdelete(int isdelete)
	{
		this.isdelete = isdelete;
	}
	public void setId(Long id)
	{
		this.id=id;
	}
	public Long getId()
	{
		return this.id;
	}
	public void setGuid(String guid)
	{
		this.guid=guid;
	}
	public String getGuid()
	{
		return this.guid;
	}
	public void setBlogId(Integer blogId)
	{
		this.blogId=blogId;
	}
	public Integer getBlogId()
	{
		return this.blogId;
	}
	public void setStorePath(String storePath)
	{
		this.storePath=storePath;
	}
	public String getStorePath()
	{
		return this.storePath;
	}
	public void setRealName(String realName)
	{
		this.realName=realName;
	}
	public String getRealName()
	{
		return this.realName;
	}
	public void setSuffix(String suffix)
	{
		this.suffix=suffix;
	}
	public String getSuffix()
	{
		return this.suffix;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return this.name;
	}
	public void setTimeCreate(Date timeCreate)
	{
		this.timeCreate=timeCreate;
	}
	public Date getTimeCreate()
	{
		return this.timeCreate;
	}

} 
 