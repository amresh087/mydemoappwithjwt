package com.project.demo.service;

import java.util.List;

import com.project.demo.model.investment.UserInvestment;
import com.project.demo.model.user.User;

public interface InvestmentService {
	
	public UserInvestment saveInvestment(UserInvestment investment,User user);
	
	List<UserInvestment> findAllInvestments();
	
	List<UserInvestment> findInvestmentsByUserId(Integer userId);
	
	UserInvestment updateInvestmentsById(Integer investmentId,UserInvestment investment);
	
	List<UserInvestment> myFutureInstallment(Integer userId);
	
	
	List<UserInvestment> futureInstallment();
	
	

}
