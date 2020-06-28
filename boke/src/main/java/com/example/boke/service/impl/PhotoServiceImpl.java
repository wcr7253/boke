package com.example.boke.service.impl;



import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Util.MyUtil;
import com.example.boke.Dao.PhotoMapper;
import com.example.boke.entity.Photo;
import com.example.boke.entity.User;
import com.example.boke.service.PhotoService;
@Service
public class PhotoServiceImpl implements PhotoService
{
	@Autowired
	PhotoMapper Photo;

	@Override
	public Photo savePhoto(String realName,User user)
	{
		Photo row =new Photo();
		row.guid = MyUtil.guid();
		row.storePath = user.storePath;
		row.name = row.guid + row.suffix; // 规范文件名
		row.realName = realName;
		row.suffix = MyUtil.getSuffix(row.realName);
		row.name = row.guid + row.suffix; // 规范文件名
		row.timeCreate = new Date();
		row.isdelete=0;
		Photo.savePhoto(row);
		
		return row;
	}

	@Override
	public int updataPhoto(HashMap<String, Object> map)
	{
		// TODO Auto-generated method stub
		return Photo.updataPhoto(map);
	}

	@Override
	public com.example.boke.entity.Photo getPhoto(int photoId)
	{
		// TODO Auto-generated method stub
		return Photo.getPhoto(photoId);
	}

	@Override
	public int updataPhotoForDel(int blogId)
	{
		// TODO Auto-generated method stub
		return Photo.updataPhotoForDel(blogId);
	}


	@Override
	public int delPhoto(int id)
	{
		// TODO Auto-generated method stub
		return Photo.delPhoto(id);
	}

	@Override
	public List<com.example.boke.entity.Photo> listPhoto()
	{
		// TODO Auto-generated method stub
		return Photo.listPhoto();
	}

	@Override
	public com.example.boke.entity.Photo getPhotoByBlogId(int blogId)
	{
		// TODO Auto-generated method stub
		return Photo.getPhotoByBlogId(blogId);
	}


}
