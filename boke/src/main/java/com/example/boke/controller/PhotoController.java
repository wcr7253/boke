package com.example.boke.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.Util.MyUtil;
import com.example.af.spring.AfRestData;
import com.example.af.spring.LayuiPhotoRestData;
import com.example.af.spring.LayuiRestData;
import com.example.boke.entity.Photo;
import com.example.boke.entity.User;
import com.example.boke.service.impl.PhotoServiceImpl;

@Controller
@RequestMapping("/photo")
public class PhotoController
{

	@PostMapping(value = "/upload.do")
	public Object upload(HttpServletRequest request,HttpSession session) throws Exception
	{
		MultipartHttpServletRequest mhr = (MultipartHttpServletRequest) request;

		String tag = mhr.getParameter("tag"); // 表单里的 name='tag'
		System.out.println("** tag: " + tag);

		MultipartFile mf = mhr.getFile("file"); // 表单里的 name='file'
		Map<String, Object> result = new HashMap<>();

		if (mf != null && !mf.isEmpty())
		{
			// 决定把文件存放在哪儿
			File dir = new File("c:/tmp");
			dir.mkdirs();

			// 决定文件的名称
			String realName = mf.getOriginalFilename();//真实的名字
			File tmpFile = new File(dir, realName);

			// 接收上传 ...
			mf.transferTo(tmpFile);
			
			System.out.println("** file: " + tmpFile.getAbsolutePath());
			
			// 保存photo信息
			Photo photo = savePhoto(realName,(User)session.getAttribute("user"));
			
			// 回应给客户端的消息
			result.put("PhotoId", photo.getId());
			
		}

		return new LayuiRestData(result);
	}

	
	
	@Autowired
	PhotoServiceImpl photoImpl;
	public Photo savePhoto(String realName,User user)
	{
		return photoImpl.savePhoto(realName,user);
	}
	
	 // 示例  /a/photo/view?path=x/x/x/x.jpg
	@GetMapping("/view")
	public void view(Integer blogId
			, HttpServletRequest request
			, HttpServletResponse response)	throws Exception
	{
		//查找对应照片
		Photo photo=photoImpl.getPhotoByBlogId(blogId);
		// 文件名，路径		
		File dir = new File("C:/tmp");		
		File storeDir = new File(dir, photo.getStorePath());
		File targetFile = new File(storeDir, photo.getName());
		
		String contentType = MyUtil.getContentType(photo.getSuffix());
		
		// 应答：设置 Content-Type 和 Content-Length 
		long contentLength = targetFile.length();
		response.setContentType(contentType);
		response.setHeader("Content-Length", String.valueOf(contentLength));
//		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));	
		
		// 应答：读取目标文件的数据, 发送给客户端
		InputStream inputStream = new FileInputStream(targetFile);
		OutputStream outputStream = response.getOutputStream();
		try{
			MyUtil.copy (inputStream, outputStream);
		}
		catch(Exception e){
			try{ inputStream.close();} catch(Exception e2){}
		}
		
		outputStream.close();
	}
	
}
