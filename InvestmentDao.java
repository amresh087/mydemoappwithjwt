package com.project.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.demo.model.investment.UserInvestment;

@Repository
public interface InvestmentDao extends JpaRepository<UserInvestment, Integer>{
	@Query(value="SELECT u from UserInvestment u WHERE u.userId=?1")
	List<UserInvestment> findInvestmentsByUserId(Integer userid);
	

	@Query(value = "Select * from user_investment u where u.user_id = ?1 and DATE(expiry_date)>= CURDATE()", nativeQuery = true)
	List<UserInvestment> myFutureInstallment(Integer userId);
	
	
	@Query(value = "Select * from user_investment u where expiry_date>= ( CURDATE() - INTERVAL 5 DAY )", nativeQuery = true)
	List<UserInvestment> futureInstallment();
	
	
	
}
