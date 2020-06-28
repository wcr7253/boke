package com.example.af.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class LayuiRestData implements AfRestView
{	
	Object data;
	int count=0;
	
	JSONObject json = new JSONObject(true);
	public LayuiRestData()
	{
	}
	
	public LayuiRestData(Object data)
	{
		this.data = data;
	}
	public LayuiRestData(Object data,int count)
	{
		this.data = data;
		this.count = count;
		json.put("count", count);
	}
	@Override
	public void render(Map<String, ?> model
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception
	{
		
		json.put("code", 0);
		json.put("msg", "");
	
		if(data != null)
		{
			if(data instanceof JSON) // 本身就是 JSONObject 或 JSONArray
				json.put("data", data);
			else
				json.put("data", JSON.toJSON(data));
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		response.getWriter().print( JSON.toJSONString(json,SerializerFeature.PrettyFormat) );
	}

}
