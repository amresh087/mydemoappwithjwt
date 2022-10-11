package com.project.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.project.demo.model.investment.UserInvestment;
import com.project.demo.model.mail.Mail;
import com.project.demo.model.user.User;
import com.project.demo.service.InvestmentService;
import com.project.demo.service.UserService;
import com.project.demo.service.impl.MailServiceImpl;

@SpringBootApplication
@EnableScheduling
public class ProjectDemoApiApplication {

	
	@Autowired
	private InvestmentService investmentServiceimpl;
	
	@Autowired
	private UserService userServiceImpl;
	
	@Value("${spring.mail.username}")
	private String fromMail;
	
	@Autowired
    private MailServiceImpl emailService;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectDemoApiApplication.class, args);
	}
	
	
	@Scheduled(cron = "0 0 6 * * ?")
	//@Scheduled(cron = "0 */1 * ? * *")
	public void cronJobSch() throws Exception {
		System.out.println("-------job trigger ");
		
		List<UserInvestment> userInvestmentList=investmentServiceimpl.futureInstallment();
		for(UserInvestment userInvestment:userInvestmentList) {
			Integer userid=userInvestment.getUserId();
			User user=userServiceImpl.findById(userid).get();
			
				Mail mail = new Mail();
		        mail.setFrom(fromMail);
		        mail.setTo(user.getEmailId());
		        mail.setSubject("User Investment expiry mail");

		        Map model = new HashMap();
		        model.put("subject", "Your Policy will expiry soon! Please take action to avoid late charges!");
		        model.put("user", user.getUsername());
		        model.put("PolicyName", userInvestment.getPolicyName());
		        model.put("Tenure", userInvestment.getTenure());
		        model.put("Installment", userInvestment.getTotalInstallment());
		        model.put("ExpiryDate", userInvestment.getExpiryDate());
		        mail.setModel(model);

		        try {
					emailService.sendSimpleMessage(mail,"invest");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	

}
