package com.example.boke.entity; 

import java.io.Serializable;
import java.util.Date; 

/** 本类由 POJO生成器 自动生成于 2020-05-31 09:46:50
    作者：阿发你好      官网: http://afanihao.cn 
*/ 

/** INSERT语句 ( 预处理方式 ) 
  INSERT INTO `user`
        (`id`, `nickname`, `username`, `password`, `email`, `avatarId`, `type`, `createTime`, `updateTime`) 
  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) 
*/ 

/** INSERT语句 ( MyBatis方式 ) 
  INSERT INTO `user`
        (`id`, `nickname`, `username`, `password`, `email`, `avatarId`, `type`, `createTime`, `updateTime`) 
  VALUES(#{id}, #{nickname}, #{username}, #{password}, #{email}, #{avatarId}, #{type}, #{createTime}, #{updateTime}) 

  自增主键: id
*/ 

public class User implements Serializable
{ 
 
	public Integer id ; 
	public String nickname ; 
	public String username ; 
	public String password ; 
	public String cellPhone ; 
	public String qq ; 
	public String salt ; 
	public String email ; 
	public String avatarId ; 
	public Integer type ; 
	public String storePath ; 
	
	public String getCellPhone()
	{
		return cellPhone;
	}
	public void setCellPhone(String cellPhone)
	{
		this.cellPhone = cellPhone;
	}
	public String getQq()
	{
		return qq;
	}
	public void setQq(String qq)
	{
		this.qq = qq;
	}
	public String getStorePath()
	{
		return storePath;
	}
	public void setStorePath(String storePath)
	{
		this.storePath = storePath;
	}
	public String getSalt()
	{
		return salt;
	}
	public void setSalt(String salt)
	{
		this.salt = salt;
	}
	public Date createTime ; 
	public Date updateTime ; 

	
	public void setId(Integer id)
	{
		this.id=id;
	}
	public Integer getId()
	{
		return this.id;
	}
	public void setNickname(String nickname)
	{
		this.nickname=nickname;
	}
	public String getNickname()
	{
		return this.nickname;
	}
	public void setUsername(String username)
	{
		this.username=username;
	}
	public String getUsername()
	{
		return this.username;
	}
	public void setPassword(String password)
	{
		this.password=password;
	}
	public String getPassword()
	{
		return this.password;
	}
	public void setEmail(String email)
	{
		this.email=email;
	}
	public String getEmail()
	{
		return this.email;
	}
	public void setAvatarId(String avatarId)
	{
		this.avatarId=avatarId;
	}
	public String getAvatarId()
	{
		return this.avatarId;
	}
	public void setType(Integer type)
	{
		this.type=type;
	}
	public Integer getType()
	{
		return this.type;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime=createTime;
	}
	public Date getCreateTime()
	{
		return this.createTime;
	}
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime=updateTime;
	}
	public Date getUpdateTime()
	{
		return this.updateTime;
	}

} 
 