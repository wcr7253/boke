package com.example.af.spring;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;


// 当注册多个 异常处理器时，@Order决定顺序，Order越低的越先执行
@ControllerAdvice
@Order(-100)
public class AfExceptionResolver 
{
	@ExceptionHandler(Exception.class)
	public ModelAndView resolveException(HttpServletRequest request
			, HttpServletResponse response
			, Object handler
			, Exception exception)
	{

		String uri = request.getRequestURI();
		if(uri.endsWith(".do"))
		{
			// REST 出错处理
			Map<String,Object> model = new HashMap<>();
			View view = new AfRestError(exception);
			System.err.println("访问" + request.getRequestURI() + " 发生错误, 错误信息:" + exception.getMessage());
			
			
			return new ModelAndView(view, model);
		}
		else
		{
			// MVC 处理
			 Map<String,Object> model = new HashMap<>();
			 model.put("message", exception.getMessage());
				
			 StringWriter stringWriter = new StringWriter();
			 exception.printStackTrace(new PrintWriter(stringWriter));
			 model.put("detail", stringWriter.toString());
				
			 return new ModelAndView("error", model);
		}
	}

}
