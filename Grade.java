package com.project.demo.model.category;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.demo.model.user.User;

@Entity
@Table
public class Grade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	 private Integer  userid;
	 private String gradename;
	 private String description;
	 
	 //@JsonManagedReference
	 @OneToMany(cascade = CascadeType.ALL, mappedBy="gradeId")
	 private List<Offer> offer;
	 
	 //@JsonManagedReference
	 @OneToMany(cascade = CascadeType.ALL, mappedBy="gradeId")
	 private List<User> user;
	 
	 
	 
		
	public List<User> getUser() {
		return user;
	}
	public void setUser(List<User> user) {
		this.user = user;
	}
	public Integer getId() {
		return id;
	}
	public List<Offer> getOffer() {
		return offer;
	}
	public void setOffer(List<Offer> offer) {
		this.offer = offer;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getGradename() {
		return gradename;
	}
	public void setGradename(String gradename) {
		this.gradename = gradename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	 
	 
	 
	 

}
