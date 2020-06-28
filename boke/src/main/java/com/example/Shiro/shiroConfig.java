package com.example.Shiro;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.Shiro.Realm.UserRealm;
import com.example.Util.MyUtil;
import com.example.boke.entity.User;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class shiroConfig
{
	
	@Bean(name="shiroFilterFactoryBean")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager)
	{
		ShiroFilterFactoryBean filterFactoryBean=new ShiroFilterFactoryBean();
	
		filterFactoryBean.setSecurityManager(securityManager);
		
		
		 //登录界面        
		filterFactoryBean.setLoginUrl("/boke/admin/login");
        //未授权界面
	//	filterFactoryBean.setUnauthorizedUrl("/login");
		// 登录成功后要跳转的链接
		//filterFactoryBean.setSuccessUrl("/index");
		
		//拦截器.Map必须是有序的
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
      
        // 配置不会被拦截的链接 顺序判断
       
	     filterChainDefinitionMap.put("/boke/admin/login","anon");
	     filterChainDefinitionMap.put("/boke/admin/login.do","anon"); 
	     filterChainDefinitionMap.put("/boke/admin/**", "user");
	 
     
	      filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return filterFactoryBean;
	}
	
	
	
    @Bean
	public DefaultWebSecurityManager SecurityManager()
	{
		DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
		
		securityManager.setRealm(getRealm());	
	
		//注入缓存管理器
		securityManager.setCacheManager(ehCacheManager());
		
		//注入Session管理器
		securityManager.setSessionManager(getSessionManager());
				
		return securityManager;
	}
    
	@Bean
	public UserRealm getRealm()
	{
		UserRealm userRealm=new UserRealm();
		
		userRealm.setCredentialsMatcher(getCredentialsMatcher());
		
		//开启授权对象缓存
		userRealm.setAuthorizationCachingEnabled(true);
		userRealm.setAuthorizationCacheName("authorizationCache");
		
		//开启认证对象缓存
		userRealm.setAuthenticationCachingEnabled(true);
		userRealm.setAuthenticationCacheName("authenticationCache");
		
		
		return userRealm;
	}
	
	@Bean
	public CredentialsMatcher getCredentialsMatcher()
	{
		HashedCredentialsMatcher credentialsMatcher=new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("Md5");
		credentialsMatcher.setHashIterations(520);
		return credentialsMatcher;
	}
	
	//thymeleaf使用shiro标签
	@Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect()
	{
		return new ShiroDialect();
	}
	
	@Bean
    public EhCacheManager ehCacheManager(){
    
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }
	
	@Bean
	public SessionManager getSessionManager()
	{
		DefaultWebSessionManager webSessionManager =new DefaultWebSessionManager();
		
		return webSessionManager;
	}
}
