package com.project.demo.model.category;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class SubCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	 private String subcategoryName;
	
	@Column
	 private String subcategoryDescription;
	
	
	@Column
	private Date lastModify;
	
	public Integer getId() {
		return id;
	}

	@Column
	@CreationTimestamp
	private Date currDate;
	
	//@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="category_Id", nullable=true)
	private Category categoryId;
	
	
	//@JsonManagedReference
	//@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy="subCategoryId")
	private List<Offer> offer;
	
	
	
	
	public List<Offer> getOffer() {
		return offer;
	}


	public void setOffer(List<Offer> offer) {
		this.offer = offer;
	}


	public String getSubcategoryName() {
		return subcategoryName;
	}


	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}


	public String getSubcategoryDescription() {
		return subcategoryDescription;
	}


	public void setSubcategoryDescription(String subcategoryDescription) {
		this.subcategoryDescription = subcategoryDescription;
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


	


	public Category getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(Category categoryId) {
		this.categoryId = categoryId;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
