package com.project.demo.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.demo.model.investment.UserInvestment;
import com.project.demo.model.user.User;
import com.project.demo.repository.InvestmentDao;
import com.project.demo.service.InvestmentService;
@Service
public class InvestmentServiceImpl implements InvestmentService{
	
	@Autowired
	private InvestmentDao investmentDao;
	
	@Override
	public UserInvestment  saveInvestment(UserInvestment investment,User user) {
		investment.setUserId(user.getId());
		Integer tenure=investment.getTenure();
		String emi=investment.getEmi();
		int  totalInstallment=0;
		
		if(tenure!=null && emi!=null) {
			
			if("yearly".equalsIgnoreCase(emi)) {
				totalInstallment=tenure*1;
				
			}else if("halfyearly".equalsIgnoreCase(emi)) {
				totalInstallment=tenure*2;
			}else if("monthly".equalsIgnoreCase(emi)) {
				totalInstallment=tenure*12;
			}
			investment.setTotalInstallment(totalInstallment);
			
			Date today = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			cal.add(Calendar.YEAR,tenure);
			Date expiryDate = cal.getTime();
			investment.setExpiryDate(expiryDate);
			
			
		}
		UserInvestment inves=investmentDao.save(investment);
		return inves;
	}
	
	public List<UserInvestment> findAllInvestments(){
		return investmentDao.findAll();
	}
	
	
	public List<UserInvestment> findInvestmentsByUserId(Integer userId){
		return investmentDao.findInvestmentsByUserId(userId);
	}
	
	public UserInvestment updateInvestmentsById(Integer investmentId, UserInvestment investment) {
		UserInvestment dbInvestment = investmentDao.findById(investmentId).get();

		if (dbInvestment != null) {
			if (investment.getExpiryDate() != null)
				dbInvestment.setExpiryDate(investment.getExpiryDate());

			if (investment.getPolicyName() != null)
				dbInvestment.setPolicyName(investment.getPolicyName());

			if (investment.getTenure() != null)
				dbInvestment.setTenure(investment.getTenure());

			if (investment.getTotalPremium() != null)
				dbInvestment.setTotalPremium(investment.getTotalPremium());

			if (investment.getEmi() != null)
				dbInvestment.setEmi(investment.getEmi());

		}

		Integer tenure = dbInvestment.getTenure();
		String emi = dbInvestment.getEmi();
		int totalInstallment = 0;

		if (tenure != null && emi != null) {

			if ("yearly".equalsIgnoreCase(emi)) {
				totalInstallment = tenure * 1;

			} else if ("halfyearly".equalsIgnoreCase(emi)) {
				totalInstallment = tenure * 2;
			} else if ("monthly".equalsIgnoreCase(emi)) {
				totalInstallment = tenure * 12;
			}
			dbInvestment.setTotalInstallment(totalInstallment);
		}

		return investmentDao.save(dbInvestment);

	}
	
	public List<UserInvestment> myFutureInstallment(Integer userId){
		return investmentDao.myFutureInstallment(userId);
	}
	
	
	public List<UserInvestment> futureInstallment(){
		return investmentDao.futureInstallment();
	}
	

}
