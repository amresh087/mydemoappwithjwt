package com.project.demo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.demo.model.category.Category;
import com.project.demo.model.category.Offer;
import com.project.demo.model.category.SubCategory;
import com.project.demo.model.user.User;
import com.project.demo.repository.CategoryRepository;
import com.project.demo.repository.OfferRepository;
import com.project.demo.repository.SubCategoryRepository;
import com.project.demo.repository.UserRepository;
import com.project.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	

	public User save(User user) {
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setLastLogin(user.getLastLogin());
		newUser.setUserType(user.getUserType());
		newUser.setEmailId(user.getEmailId());
		return userRepository.save(newUser);
	}

	@Transactional
	public int updatePassword(String password, Integer id) {
		return userRepository.updatePassword(bcryptEncoder.encode(password), id);
	}

	@Transactional
	public int updateLastLogin(String username) {
		return userRepository.updateLastLogin(new Date(), username);
	}

	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}

	public List<User> findAll() {

		List<User> users = (List<User>) userRepository.findAll();

		return users;
	}

	public List<User> findByMatch(String user) {

		List<User> users = (List<User>) userRepository.findByUserContaining(user);

		return users;
	}

	@Override
	public void deleteById(Integer id) {
		userRepository.deleteById(id);

	}

	public User loadUserByUsername(String username) {
		return userRepository.loadUserByUsername(username);
	}

	public User getLoginCountInDay(String username) {
		return userRepository.getLoginCountInDay(username);
	}
	
	public List<User> genrateReportFiles() {
		
		return userRepository.genrateReportAllUser();
		
		
	}
	
	public List<Offer> getAllOffer(Integer subcategoryId){
		List<Offer> offerlist= offerRepository.getAllPostByUserId(subcategoryId);
		
		
		return offerlist;
	}
	
	public List<Offer>  getAllOfferAfterTenday(){
		return offerRepository.getAllOfferAfterTenday();
	}
	
	public Offer saveOffer(Offer offer) {
		return offerRepository.save(offer);
	}
	
	public void deleteOffer(Integer offerId) {
		offerRepository.deleteById(offerId);
	}
	
	
	public List<Offer> getOfferByCategory(Integer categoryId) {
		return offerRepository.getOfferByCategory(categoryId);
	}
	
	public List<Offer> getOfferByUserId(Integer userId){
		return offerRepository.getOfferByUserId(userId);
		
	}
	
	
	public Category saveCategory(Category category) {;
		return categoryRepository.save(category);
		
	}
	
	public SubCategory saveSubCategory(SubCategory subCategory) {;
		return subCategoryRepository.save(subCategory);
	}
		 

}
