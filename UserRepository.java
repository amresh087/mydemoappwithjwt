package com.project.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.demo.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);

	@Query(value = "Select * from User u where u.username like ?1%", nativeQuery = true)
	List<User> findByUserContaining(String user);
	
	
	@Modifying
	@Query(value ="update User u set u.password = ?1 where u.id = ?2",nativeQuery = true)
	int updatePassword(String password, Integer id);
	
	@Modifying
	@Query(value ="update User u set u.lastLogin = ?1,u.loginLimit = u.loginLimit + 1 where u.username = ?2")
	int updateLastLogin(Date lastLogin,String username);
	
	@Query(value = "Select * from User u where u.username = ?1", nativeQuery = true)
	User loadUserByUsername(String username);
	
	//@Query(value = "Select * from User u where u.username = ?1 and DATE(last_login)= CURDATE()", nativeQuery = true)
	@Query(value = "Select * from User u where u.username = ?1", nativeQuery = true)
	User getLoginCountInDay(String username);
	
	
	//@Query(value = "select u.id, u.username,u.email_id,u.last_login,u.login_limit,"+ "u.password,u.user_type,up.postname,up.postdescription,up.curr_date,up.last_modify, ui.policy_name,ui.tenure,ui.total_premium,ui.total_installment, ui.issued_date,ui.expiry_date from user u left join user_post up on(u.id=up.userid) left join user_investment ui on(u.id=ui.user_id)", nativeQuery = true)
	@Query(value = "select u from User u left join UserPost up on(u.id=up.userid) left join UserInvestment ui on(u.id=ui.userId)")
	List<User> genrateReportAllUser();
	


}
