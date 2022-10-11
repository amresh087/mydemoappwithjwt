package com.project.demo.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.model.post.UserPost;
import com.project.demo.model.user.User;
import com.project.demo.service.PostService;

@RestController
@CrossOrigin()
public class PostController {
	@Autowired
	private PostService postModelServiceimpl;
	
	@Autowired
	private HttpServletRequest request;
	
	@PostMapping(value="/addPost")
	public ResponseEntity<String> saveData(@RequestBody UserPost userPost) {
		
		try {
		
		User user=null;
		if(request!=null && request.getAttribute("userSession")!=null) {
			user=(User)request.getAttribute("userSession");
		}
		postModelServiceimpl.savePost(userPost,user);
	} catch (DataAccessException ex) {
		return new ResponseEntity<>("Duplicate value not allowed", HttpStatus.ALREADY_REPORTED);
	} catch (Exception e) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

		return new ResponseEntity<String>("Post is created successfully", HttpStatus.CREATED);
		
	}
	
	@GetMapping(value="/getAllPost")
	private List<UserPost> getAllPostDetails(){
		return postModelServiceimpl.getAllPost();
	}
	
	@GetMapping(value="/showAllMyPost")
	private List<UserPost> showAllMyPostDetails(){
		
		User user=null;
		if(request!=null && request.getAttribute("userSession")!=null) {
			user=(User)request.getAttribute("userSession");
		}
		return postModelServiceimpl.getAllPostByUserName(user.getId());
	}
	
	
	
	@GetMapping(value="/showMyPostById/{postId}")
	public UserPost showMyPostById(@PathVariable("postId")Integer postId) {
		
		return postModelServiceimpl.getPostById(postId);
	}
	
	
	@PutMapping(value="/updateMyPostById/{postId}")
	public ResponseEntity<String> updateMyPostById(@PathVariable("postId")Integer postId,@RequestBody UserPost userPost) {
		int i=0;
		try {
			User user=null;
			if(request!=null && request.getAttribute("userSession")!=null) {
				user=(User)request.getAttribute("userSession");
				userPost.setUserid(user.getId());
				//userPost.setPostUserId(user);
			}
			
			i=	postModelServiceimpl.updateMyPostById(postId,userPost);
			
	} catch (DataAccessException ex) {
		ex.printStackTrace();
		return new ResponseEntity<>("DB issue", HttpStatus.INTERNAL_SERVER_ERROR);
	} catch (Exception e) {
		return new ResponseEntity<>("Server Side issue", HttpStatus.INTERNAL_SERVER_ERROR);
	}
		if(i>0)
		return new ResponseEntity<String>("Post is updated successfully", HttpStatus.OK);
		else
			return new ResponseEntity<String>("Post is not updated successfully", HttpStatus.INTERNAL_SERVER_ERROR);
			
		
	}
	
	
	
	
	

}
