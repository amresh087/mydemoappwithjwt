package com.project.demo.model.investment;

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
public class UserInvestment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private Integer userId;
	
	@Column
    private String policyName;
	
    @Column
	private Integer tenure;
    
    @Column
    private Double totalPremium;
    
    @Column
    private String emi;
    
    @Column
    private Integer totalInstallment;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Investment_user_id", nullable=true)
	private User InvestmentUserId;
	

	public Integer getTotalInstallment() {
		return totalInstallment;
	}

	public void setTotalInstallment(Integer totalInstallment) {
		this.totalInstallment = totalInstallment;
	}

	@Column
	private Date expiryDate;
	
	@Column
	@CreationTimestamp
	private Date issuedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public Integer getTenure() {
		return tenure;
	}

	public void setTenure(Integer tenure) {
		this.tenure = tenure;
	}

	public Double getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(Double totalPremium) {
		this.totalPremium = totalPremium;
	}

	public String getEmi() {
		return emi;
	}

	public void setEmi(String emi) {
		this.emi = emi;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	

}
