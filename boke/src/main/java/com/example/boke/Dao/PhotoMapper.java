package com.example.boke.Dao;


import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.boke.entity.Photo;

@Mapper
public interface PhotoMapper {
		 
		int savePhoto(Photo photo);
		int updataPhoto(HashMap<String, Object> map);
		Photo getPhoto(int photoId);
		int updataPhotoForDel(int blogId);
		List<Photo> listPhoto();
		int delPhoto(int id);
		Photo getPhotoByBlogId(int blogId);
}
