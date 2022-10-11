package com.project.demo.model.category;

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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table
public class Offer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String offerName;
	@Column
	private String description;
	@Column
	private Double price;
	@Column
	private Integer discount;
	@Column
	private String primeuser;
	@Column
	@CreationTimestamp
	private Date createdDate;
	@Column
	private Date updatedDate;
	@Column
	private Date expiredDate;
	
	//@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="sub_category_Id", nullable=true)
	private SubCategory subCategoryId;
	
	//@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="grade_Id", nullable=true)
	private Grade gradeId;
	
	
	

	public Grade getGradeId() {
		return gradeId;
	}




	public void setGradeId(Grade gradeId) {
		this.gradeId = gradeId;
	}




	public SubCategory getSubCategoryId() {
		return subCategoryId;
	}




	public void setSubCategoryId(SubCategory subCategoryId) {
		this.subCategoryId = subCategoryId;
	}




	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public String getOfferName() {
		return offerName;
	}




	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public Double getPrice() {
		return price;
	}




	public void setPrice(Double price) {
		this.price = price;
	}




	public Integer getDiscount() {
		return discount;
	}




	public void setDiscount(Integer discount) {
		this.discount = discount;
	}




	public String getPrimeuser() {
		return primeuser;
	}




	public void setPrimeuser(String primeuser) {
		this.primeuser = primeuser;
	}




	public Date getCreatedDate() {
		return createdDate;
	}




	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}




	public Date getUpdatedDate() {
		return updatedDate;
	}




	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}




	public Date getExpiredDate() {
		return expiredDate;
	}




	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}




	
	


}
