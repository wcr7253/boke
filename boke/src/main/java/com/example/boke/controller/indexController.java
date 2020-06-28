package com.example.boke.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController
{
	@GetMapping({"/","/index"})
	public String index()
	{
		return "forward:/boke/user/index";
	}
	
	@GetMapping({"/admin"})
	public String indexadmin()
	{
		return "forward:/boke/admin/blogs";
	}
}
