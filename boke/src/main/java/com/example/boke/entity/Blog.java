package com.example.boke.entity; 

import java.io.Serializable;
import java.util.Date; 

/** 本类由 POJO生成器 自动生成于 2020-05-31 09:46:50
    作者：阿发你好      官网: http://afanihao.cn 
*/ 

/** INSERT语句 ( 预处理方式 ) 
  INSERT INTO `blog`
        (`id`, `title`, `content`, `firstPictureId`, `flag`, `views`, `isAppreciation`, `isShareStatrment`, `isCommentabled`, `isRecommand`, `isPublished`, `creatTime`, `updataTime`, `typeId`, `userId`) 
  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) 
*/ 

/** INSERT语句 ( MyBatis方式 ) 
  INSERT INTO `blog`
        (`id`, `title`, `content`, `firstPictureId`, `flag`, `views`, `isAppreciation`, `isShareStatrment`, `isCommentabled`, `isRecommand`, `isPublished`, `creatTime`, `updataTime`, `typeId`, `userId`) 
  VALUES(#{id}, #{title}, #{content}, #{firstPictureId}, #{flag}, #{views}, #{isAppreciation}, #{isShareStatrment}, #{isCommentabled}, #{isRecommand}, #{isPublished}, #{creatTime}, #{updataTime}, #{typeId}, #{userId}) 

  自增主键: id
*/ 

public class Blog implements Serializable
{ 
	
	public Integer id ; 
	public String title ; 
	public String content ; 
	public String description ; 
	public String firstPictureId ; 
	public String flag ; 
	public Integer views ; 
	public Boolean isAppreciation ; 
	public Boolean isShareStatrment ; 
	public Boolean isCommentabled ; 
	public Boolean isRecommand ; 
	public Boolean isPublished ; 
	public Date creatTime ; 
	public Date updataTime ; 
	public Integer typeId ; 
	public Integer userId ; 

	public String name ; 
	public String typeName ; 
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public void setId(Integer id)
	{
		this.id=id;
	}
	public Integer getId()
	{
		return this.id;
	}
	public void setTitle(String title)
	{
		this.title=title;
	}
	public String getTitle()
	{
		return this.title;
	}
	public void setContent(String content)
	{
		this.content=content;
	}
	public String getContent()
	{
		return this.content;
	}
	public void setFirstPictureId(String firstPictureId)
	{
		this.firstPictureId=firstPictureId;
	}
	public String getFirstPictureId()
	{
		return this.firstPictureId;
	}
	public void setFlag(String flag)
	{
		this.flag=flag;
	}
	public String getFlag()
	{
		return this.flag;
	}
	public void setViews(Integer views)
	{
		this.views=views;
	}
	public Integer getViews()
	{
		return this.views;
	}
	public void setIsAppreciation(Boolean isAppreciation)
	{
		this.isAppreciation=isAppreciation;
	}
	public Boolean getIsAppreciation()
	{
		return this.isAppreciation;
	}
	public void setIsShareStatrment(Boolean isShareStatrment)
	{
		this.isShareStatrment=isShareStatrment;
	}
	public Boolean getIsShareStatrment()
	{
		return this.isShareStatrment;
	}
	public void setIsCommentabled(Boolean isCommentabled)
	{
		this.isCommentabled=isCommentabled;
	}
	public Boolean getIsCommentabled()
	{
		return this.isCommentabled;
	}
	public void setIsRecommand(Boolean isRecommand)
	{
		this.isRecommand=isRecommand;
	}
	public Boolean getIsRecommand()
	{
		return this.isRecommand;
	}
	public void setIsPublished(Boolean isPublished)
	{
		this.isPublished=isPublished;
	}
	public Boolean getIsPublished()
	{
		return this.isPublished;
	}
	public void setCreatTime(Date creatTime)
	{
		this.creatTime=creatTime;
	}
	public Date getCreatTime()
	{
		return this.creatTime;
	}
	public void setUpdataTime(Date updataTime)
	{
		this.updataTime=updataTime;
	}
	public Date getUpdataTime()
	{
		return this.updataTime;
	}
	public void setTypeId(Integer typeId)
	{
		this.typeId=typeId;
	}
	public Integer getTypeId()
	{
		return this.typeId;
	}
	public void setUserId(Integer userId)
	{
		this.userId=userId;
	}
	public Integer getUserId()
	{
		return this.userId;
	}

} 
 