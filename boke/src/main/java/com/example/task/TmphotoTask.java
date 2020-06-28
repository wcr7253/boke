package com.example.task;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.boke.entity.Photo;
import com.example.boke.service.impl.PhotoServiceImpl;

import af.sql.c3p0.AfSimpleDB;

@Component
public class TmphotoTask
{
	@Autowired
	PhotoServiceImpl photoImpl;
	
	int EXPIRED =1000 * 3600 * 3; // 3小时以上的临时文件
	
	@Scheduled(cron="0 0 0 1/1 * ?")
	public void clearPhoto()
	{
		File tmpDir = new File("C:/tmp");	
		List<Photo> listPhoto=photoImpl.listPhoto();
		
		if(! tmpDir.exists()) return;
		File[] files = tmpDir.listFiles();
		if(files == null || files.length ==0) 
			return;
		
		// 清理过期的文件
		long now = System.currentTimeMillis();
		for(File file: files)
		{
			if(now - file.lastModified() > EXPIRED  && file.isFile())
			{
				try {
					System.out.println("** 清理过期文件: " + file.getAbsolutePath());
					FileUtils.deleteQuietly( file );
				}catch(Exception e) {}
			}
		}
		
		for(Photo p: listPhoto)
		{
			if(p.isdelete==0)
			{
				File storeDir = new File(tmpDir, p.getStorePath());
				File storeFile = new File(storeDir, p.getName());
				try {
					System.out.println("** 清理过期文件: " + storeFile.getAbsolutePath());
					FileUtils.deleteQuietly( storeFile );
					
					// 删除数据库记录
					String s1 = "DELETE  FROM `photo` where id =" +p.getId().intValue();
					AfSimpleDB.execute(s1);	
				}catch(Exception e) {}		
			}
		}
		
	}
}
