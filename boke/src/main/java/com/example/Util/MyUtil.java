package com.example.Util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.example.boke.entity.Blog;
import com.example.boke.entity.Type;
import com.example.boke.service.impl.TypeServiceImpl;

@ControllerAdvice
@Order(-99)
public class MyUtil
{
	
	public static LocalDate date2LocalDate(Date date)
	{
        if(null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
	public static Date localDate2Date(LocalDate localDate)
	{
	        if(null == localDate) {
	            return null;
	        }
	        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
	        return Date.from(zonedDateTime.toInstant());
	 }
	public static String localDate2String(LocalDate date,String s)
	{
	     DateTimeFormatter fmt = DateTimeFormatter.ofPattern(s);
	     String dateStr = date.format(fmt);
	     return dateStr;
	}
	
	public static String data2str(Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String s=formatter.format(date);
		return s;
	}
	public static String data2str(Date date,String str)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(str);
		String s=formatter.format(date);
		return s;
	}
	// 创建guid
	public static String guid()
	{
		 String s = UUID.randomUUID().toString(); 
	     String s2 = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
	     return s2.toUpperCase();
	}
	// 获取文件名后缀，例如 ".mp4"
	public static String getSuffix(String filePath)
	{
		int p1 = filePath.lastIndexOf('.');
		if (p1 > 0)
		{
			String suffix = filePath.substring(p1);
			if (suffix.length() < 10) // 后缀长度必须小于10
			{
				// 后缀中不能有路径分隔符
				if (suffix.indexOf('/') < 0 && suffix.indexOf('\\') < 0)
					return suffix.toLowerCase();
			}
		}
		return "";
	}

	// 根据后缀名，推算 Content-Type
	public static String getContentType(String suffix)
	{
		suffix = suffix.toLowerCase();
		if (suffix.equals(".jpg"))
			return "image/jpeg";
		if (suffix.equals(".jpeg"))
			return "image/jpeg";
		if (suffix.equals(".png"))
			return "image/png";
		if (suffix.equals(".gif"))
			return "image/gif";
		if (suffix.equals(".html"))
			return "text/html";
		if (suffix.equals(".txt"))
			return "text/plain";
		if (suffix.equals(".js"))
			return "application/javascript";
		if (suffix.equals(".mp4"))
			return "video/mp4";

		return "application/octet-stream"; // 一般的二进制文件类型
	}
	
	// 拷贝字节流，从in中读取字节，写入到out中
	public static long copy(InputStream in, OutputStream out) throws Exception
	{
		long count = 0;
		byte[] buf = new byte[8192];
		while (true)
		{
			int n = in.read(buf);
			if (n < 0)
				break;
			if (n == 0)
				continue;
			out.write(buf, 0, n);

			count += n;
		}
		return count;
	}
	public static List<Integer> tag(String tag)
	{
		List<Integer> list=new ArrayList<Integer>();
		
		String[] line=tag.split(",");
		int i;
		for(i=0;i<line.length;i++)
		{
			list.add(Integer.parseInt(line[i]));
		}
		return list;
	}
}
