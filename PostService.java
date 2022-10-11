package com.project.demo.service;

import java.util.List;

import com.project.demo.model.post.UserPost;
import com.project.demo.model.user.User;

public interface PostService {

	public UserPost savePost(UserPost postModel,User user);

	public List<UserPost> getAllPost();

	public UserPost getPostById(Integer id);
	
	public List<UserPost> getAllPostByUserName(Integer userid);
	
	public int updateMyPostById(Integer postId,UserPost userPost);

	

}
