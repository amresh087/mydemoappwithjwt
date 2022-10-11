package com.project.demo.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.demo.model.mail.Mail;
import com.project.demo.model.post.UserPost;
import com.project.demo.model.user.User;
import com.project.demo.repository.PostModelDao;
import com.project.demo.service.PostService;

import freemarker.template.TemplateException;

@Service
public class PostModelServiceimpl implements PostService {
	@Autowired
	private PostModelDao postModelDao;
	
	@Value("${spring.mail.username}")
	private String fromMail;
	
	@Autowired
    private MailServiceImpl emailService;

	@Override
	public UserPost savePost(UserPost postModel,User user) {
		postModel.setUserid(user.getId());
		//postModel.setPostUserId(user);
		UserPost userPost=postModelDao.save(postModel);
		
		Mail mail = new Mail();
        mail.setFrom(fromMail);
        mail.setTo(user.getEmailId());
        mail.setSubject("Post creation mail");

        Map model = new HashMap();
        model.put("subject", "You have created below new post!!");
        model.put("user", user.getUsername());
        model.put("PostName", postModel.getPostname());
        model.put("PostDescription", postModel.getPostdescription());
        model.put("PostCreatedDate", postModel.getCurrDate());
        mail.setModel(model);

        try {
			emailService.sendSimpleMessage(mail,"post");
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
			
		return userPost;
	}

	@Override
	public List<UserPost> getAllPost() {

		return postModelDao.findAll();
	}

	
	public List<UserPost> getAllPostByUserName(Integer userid){
		return postModelDao.getAllPostByUserId(userid);
	}
	

	@Override
	public UserPost getPostById(Integer id) {
		return postModelDao.findById(id).get();
	}
	
	@Transactional
	public int updateMyPostById(Integer postId,UserPost userPost) {
		String postName=userPost.getPostname();
		String postDescription=userPost.getPostdescription();
		return postModelDao.updateMyPostById(userPost.getUserid(),postId,postName,postDescription);
	}

	

	

}
