package com.example.Shiro.Realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.example.boke.entity.User;
import com.example.boke.service.impl.UserServiceImpl;

public class UserRealm extends AuthorizingRealm
{
	@Autowired
	UserServiceImpl UserImpl;
	
	@Autowired
	RedisTemplate redisTemplate;
	
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0)
	{
		// TODO Auto-generated method stub
		//获取一下用户身份信息
		SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
		
//		simpleAuthorizationInfo.addRole("admin");
//		
//		simpleAuthorizationInfo.addStringPermission("userInfo:add");
		return simpleAuthorizationInfo;
	}
	
	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException
	{
		String username=(String) authenticationToken.getPrincipal();
		
		User user=UserImpl.getUser(username);
		
		if(user!=null)
		{
			Subject subject=SecurityUtils.getSubject(); 
		     Session session = subject.getSession();
		     //把用户信息保存到session
		    session.setAttribute("user", user);
			System.out.println("user录入session");
			return new SimpleAuthenticationInfo(user.username,user.password,ByteSource.Util.bytes(user.salt),this.getName());
		}
		return null;
	}

}
