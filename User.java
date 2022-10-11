package com.project.demo.model.user;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.demo.model.category.Grade;
import com.project.demo.model.investment.UserInvestment;
import com.project.demo.model.post.UserPost;

@Entity
@Table(name = "User")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true, length = 50, nullable = false)
	private String username;
	

	@Column
	@JsonIgnore
	private String password;

	@Column
	private String userType;

	@Column
	@CreationTimestamp
	private Date lastLogin;

	@Column(unique = true, length = 50, nullable = false)
	private String emailId;
	
	@Column
	private long loginLimit;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="postUserId")
	private List<UserPost> userPost;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="InvestmentUserId")
	private List<UserInvestment> userInvestment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="grade_Id", nullable=true)
	private Grade gradeId;
	
	
	

	public Grade getGradeId() {
		return gradeId;
	}

	public void setGradeId(Grade gradeId) {
		this.gradeId = gradeId;
	}

	public List<UserPost> getUserPost() {
		return userPost;
	}

	public void setUserPost(List<UserPost> userPost) {
		this.userPost = userPost;
	}

	public List<UserInvestment> getUserInvestment() {
		return userInvestment;
	}

	public void setUserInvestment(List<UserInvestment> userInvestment) {
		this.userInvestment = userInvestment;
	}

	public long getLoginLimit() {
		return loginLimit;
	}

	public void setLoginLimit(long loginLimit) {
		this.loginLimit = loginLimit;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
