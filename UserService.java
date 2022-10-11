package com.project.demo.service;

import java.util.List;
import java.util.Optional;

import com.project.demo.model.category.Category;
import com.project.demo.model.category.Offer;
import com.project.demo.model.category.SubCategory;
import com.project.demo.model.user.User;

public interface UserService {

	 User save(User user);

	int updatePassword(String password, Integer id);

	List<User> findAll();

	List<User> findByMatch(String username);

	Optional<User> findById(Integer id);

	void deleteById(Integer id);
	
	User loadUserByUsername(String username);
	
	int updateLastLogin(String username);
	
	User getLoginCountInDay(String username);
	
	List<User> genrateReportFiles();
	
	List<Offer> getAllOffer(Integer subcategoryId);
	
	Offer saveOffer(Offer offer);
	
	void deleteOffer(Integer offerId);
	
	List<Offer> getOfferByCategory(Integer categoryId);
	
	List<Offer>  getAllOfferAfterTenday();
	
	List<Offer> getOfferByUserId(Integer userId);
	
	Category saveCategory(Category category);
	
	
	SubCategory saveSubCategory(SubCategory subCategory);
	
	
	

}
