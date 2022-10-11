package com.project.demo.model.post;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.project.demo.model.user.User;

@Entity
@Table
public class UserPost {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private Integer userid;
	
	
	//@Column(unique = true,nullable=false)
	@Column
    private String postname;
	
    @Column
	private String postdescription;
	
	
	@Column
	private Date lastModify;
	
	@Column
	@CreationTimestamp
	private Date currDate;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="post_user_id", nullable=true)
	private User postUserId;
	
	
	
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getPostdescription() {
		return postdescription;
	}

	public void setPostdescription(String postdescription) {
		this.postdescription = postdescription;
	}

	public Date getLastModify() {
		return lastModify;
	}

	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}

	public Date getCurrDate() {
		return currDate;
	}

	public void setCurrDate(Date currDate) {
		this.currDate = currDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


}
