package com.example.boke.service;


import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.boke.entity.Photo;
import com.example.boke.entity.User;

@Mapper
public interface PhotoService {
		 
		Photo savePhoto(String realName,User user);
		int updataPhoto(HashMap<String, Object> map);
		Photo getPhoto(int photoId);
		int updataPhotoForDel(int blogId);
		List<Photo> listPhoto();
		int delPhoto(int id);
		Photo getPhotoByBlogId(int blogId);
}
