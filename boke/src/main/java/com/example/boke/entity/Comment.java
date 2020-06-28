package com.example.boke.entity; 

import java.io.Serializable;
import java.util.Date;
import java.util.List; 

/** 本类由 POJO生成器 自动生成于 2020-05-31 09:46:50
    作者：阿发你好      官网: http://afanihao.cn 
*/ 

/** INSERT语句 ( 预处理方式 ) 
  INSERT INTO `comment`
        (`id`, `昵称`, `email`, `content`, `avatarId`, `createTime`, `isFatherLeaf`, `blogId`, `commentId`) 
  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) 
*/ 

/** INSERT语句 ( MyBatis方式 ) 
  INSERT INTO `comment`
        (`id`, `nickname`, `email`, `content`, `avatarId`, `createTime`, `isFatherLeaf`, `blogId`, `commentId`) 
  VALUES(#{id}, #{nickname}, #{email}, #{content}, #{avatarId}, #{createTime}, #{isFatherLeaf}, #{blogId}, #{commentId}) 

  自增主键: id
*/ 

public class Comment  implements Serializable
{ 
 
	public Integer id ; 
	public String nickname ; 
	public String email ; 
	public String content ; 
	public String avatarId ; 
	public Date createTime ; 
	public Integer blogId ; 
	public Integer commentId ; 
	
	public List<Comment> replyList;
	public String parentnickname;
	
	public Integer isAdmintor;
	
	public Integer getIsAdmintor()
	{
		return isAdmintor;
	}
	public void setIsAdmintor(Integer isAdmintor)
	{
		this.isAdmintor = isAdmintor;
	}
	public String getNickname()
	{
		return nickname;
	}
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	public void setId(Integer id)
	{
		this.id=id;
	}
	public Integer getId()
	{
		return this.id;
	}
	public void setEmail(String email)
	{
		this.email=email;
	}
	public String getEmail()
	{
		return this.email;
	}
	public void setContent(String content)
	{
		this.content=content;
	}
	public String getContent()
	{
		return this.content;
	}
	public void setAvatarId(String avatarId)
	{
		this.avatarId=avatarId;
	}
	public String getAvatarId()
	{
		return this.avatarId;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime=createTime;
	}
	public Date getCreateTime()
	{
		return this.createTime;
	}
	public void setBlogId(Integer blogId)
	{
		this.blogId=blogId;
	}
	public Integer getBlogId()
	{
		return this.blogId;
	}
	public void setCommentId(Integer commentId)
	{
		this.commentId=commentId;
	}
	public Integer getCommentId()
	{
		return this.commentId;
	}

} 
 