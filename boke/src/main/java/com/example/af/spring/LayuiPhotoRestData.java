package com.example.af.spring;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class LayuiPhotoRestData implements AfRestView
{	
	Object data;
	String title;  //相册标题
	Integer id; //相册id
	Integer start= 0; //初始显示的图片序号，默认0
	public LayuiPhotoRestData()
	{
	}
	
	public LayuiPhotoRestData(String title,	Integer id,Object data)
	{
		this.title=title;
		this.id=id;
		this.data = data;
	}
		
	@Override
	public void render(Map<String, ?> model
			, HttpServletRequest request
			, HttpServletResponse response) throws Exception
	{
		JSONObject json = new JSONObject(true);
		json.put("title", title);
		json.put("id", id);
		json.put("start", start);
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
