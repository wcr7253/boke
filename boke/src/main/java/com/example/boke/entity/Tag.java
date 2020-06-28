package com.example.boke.entity; 

import java.io.Serializable;
import java.util.Date; 

/** 本类由 POJO生成器 自动生成于 2020-05-31 09:46:50
    作者：阿发你好      官网: http://afanihao.cn 
*/ 

/** INSERT语句 ( 预处理方式 ) 
  INSERT INTO `tag`
        (`id`, `name`) 
  VALUES(?, ?) 
*/ 

/** INSERT语句 ( MyBatis方式 ) 
  INSERT INTO `tag`
        (`id`, `name`) 
  VALUES(#{id}, #{name}) 

  自增主键: id
*/ 

public class Tag implements Serializable
{ 
 
	public Integer id ; 
	public String name ; 
	public Integer BlogCount;

	public void setId(Integer id)
	{
		this.id=id;
	}
	public Integer getId()
	{
		return this.id;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return this.name;
	}

} 
 