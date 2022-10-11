package com.project.demo.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.config.GeneratePdfReport;
import com.project.demo.model.investment.UserInvestment;
import com.project.demo.model.user.User;
import com.project.demo.service.InvestmentService;

@RestController
@CrossOrigin()
public class InvestmentController {
	
	@Autowired
	private InvestmentService investmentServiceimpl;
	
	@Autowired
	private HttpServletRequest request;
	
	@PostMapping(value = "/addInvestment")
	public ResponseEntity<String> addInvestment(@RequestBody UserInvestment investment) {
		try {
			User user = null;
			if (request != null && request.getAttribute("userSession") != null) {
				user = (User) request.getAttribute("userSession");
			}
			investmentServiceimpl.saveInvestment(investment, user);
			return new ResponseEntity<String>("Investment is addeed successfully", HttpStatus.CREATED);
		} catch (DataAccessException ex) {
			return new ResponseEntity<>("Duplicate value not allowed", HttpStatus.ALREADY_REPORTED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/allInvestments")
	public ResponseEntity<List<UserInvestment>> getAllInvestments() {
		try {
			List<UserInvestment> investments = new ArrayList<>();
			investmentServiceimpl.findAllInvestments().forEach(investments::add);
			return new ResponseEntity<>(investments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/allMyInvestments")
	public ResponseEntity<List<UserInvestment>> getMyInvestments() {
		try {
			List<UserInvestment> investments = new ArrayList<>();
			User user = null;
			if (request != null && request.getAttribute("userSession") != null) {
				user = (User) request.getAttribute("userSession");
			}
			investmentServiceimpl.findInvestmentsByUserId(user.getId()).forEach(investments::add);
			return new ResponseEntity<>(investments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value = "/updateInvestmentsById/{investmentId}")
	public ResponseEntity<String> updateInvestmentsById(@PathVariable("investmentId") Integer investmentId,
			@RequestBody UserInvestment investment) {
		UserInvestment invest = investmentServiceimpl.updateInvestmentsById(investmentId, investment);
		if (invest != null)
			return new ResponseEntity<String>("Updated successfully", HttpStatus.OK);
		else
			return new ResponseEntity<String>("Not Updated successfully", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@GetMapping("/myFutureInstallment")
	public ResponseEntity<InputStreamResource> myFutureInstallment() {
		
			User user = null;
			if (request != null && request.getAttribute("userSession") != null) {
				user = (User) request.getAttribute("userSession");
			}
			List<UserInvestment> userInvestments= investmentServiceimpl.myFutureInstallment(user.getId());
			
			ByteArrayInputStream bis = GeneratePdfReport.installmentReport(userInvestments,user.getUsername());
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=usersreport.pdf");
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
					.body(new InputStreamResource(bis));
			
		
	}
	

}
