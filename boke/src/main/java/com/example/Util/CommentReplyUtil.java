package com.example.Util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.boke.entity.Comment;
import com.example.boke.service.impl.CommentServiceImpl;

import af.sql.c3p0.AfSimpleDB;
/**
 * @author wcr
 * */

public class CommentReplyUtil
{
	
	public static List<Comment> dealComment(List<Comment> parentCommentList) throws Exception
	{
		int i=0;
		int j=0;
		//返回数据
		List<Comment> parentcommentList=new ArrayList<Comment>();
		
		//把每条父评论的子评论遍历出来
		for(Comment comment : parentCommentList)
		{
			List<Comment> replylist=new ArrayList<Comment>();
			//把子评论的父评论name
			String s1 = "SELECT * FROM `comment` where blogId= "+ comment.getBlogId()+" and commentId != -1  ORDER BY createTime";
			List<Comment> replyList =AfSimpleDB.query(s1, Comment.class)  ;//comment.getBlogId()
		
			for(i=0;i<replyList.size();i++)
			{
				Comment c=replyList.get(i);
				String s2 = "SELECT `nickname` FROM `comment` where id=" + c.getCommentId();
				String[] nname = AfSimpleDB.get(s2);
				c.parentnickname = nname[0];
				if(c.getCommentId() == comment.id)
					replylist.add(c);
				for(j=0;j<replylist.size();j++)
				{
					Comment r=replylist.get(j);
					if(c.getCommentId() == r.getId())
						replylist.add(c);
				}
			}
					
			
			comment.replyList=replylist;
			parentcommentList.add(comment);
			
		}
		return parentcommentList;
	}
}
