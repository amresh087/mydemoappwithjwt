
package com.project.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.config.GeneratePdfReport;
import com.project.demo.config.UserExcelExporter;
import com.project.demo.model.category.Category;
import com.project.demo.model.category.Offer;
import com.project.demo.model.category.SubCategory;
import com.project.demo.model.user.User;
import com.project.demo.model.user.UserData;
import com.project.demo.service.InvestmentService;
import com.project.demo.service.PostService;
import com.project.demo.service.UserService;
import com.project.demo.service.impl.GenrateExcelReport;
import com.project.demo.util.ResposeMapping;

@RestController
@CrossOrigin()
public class UserController {
	@Autowired
	private UserService userServiceImpl;
	
	@Autowired
	private PostService postModelServiceimpl;
	
	@Autowired
	private InvestmentService investmentServiceimpl;
	
	@Autowired
	private GenrateExcelReport genrateExcelReport;
	
	@Autowired
	private HttpServletRequest request;

	@GetMapping(value = "get")
	public String getDat() {
		return "welcome";
	}

	@PostMapping(value = "/addUser", produces = "application/json")
	public ResponseEntity<String> addUser(@RequestBody UserData userDetails) {
		try {
			User user = new User();
			user.setUsername(userDetails.getUserName());
			user.setUserType(userDetails.getUserType());
			user.setPassword(userDetails.getPassword());
			user.setEmailId(userDetails.getEmailId());
			userServiceImpl.save(user);
		} catch (DataAccessException ex) {
			System.out.println(ex.getCause().getMessage());
			return new ResponseEntity<>("Duplicate value not allowed", HttpStatus.ALREADY_REPORTED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("user saved successfully", HttpStatus.CREATED);
	}

	@PutMapping(value = "/updatePassword", produces = "application/json")
	public ResponseEntity<String> updatePassword(@RequestParam("id") Integer id, @RequestBody UserData userDetails) {

		int i = userServiceImpl.updatePassword(userDetails.getPassword(), id);
		if (i > 0) {
			return new ResponseEntity<>("Password Update successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Password Not Update successfully", HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping(value = "/deleteUser", produces = "application/json")
	public ResponseEntity<HttpStatus> deleteUser(@RequestParam("id") Integer id) {
		try {
			userServiceImpl.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/allUsers")
	public ResponseEntity<List<User>> getAllUser() {
		try {
			List<User> users = new ArrayList<User>();
			userServiceImpl.findAll().forEach(users::add);
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getMatchUser")
	public ResponseEntity<List<User>> getMatchUser(@RequestParam("username") String username) {
		try {
			List<User> users = new ArrayList<User>();
			userServiceImpl.findByMatch(username).forEach(users::add);
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getUserById", produces = "application/json")
	public ResponseEntity<User> getUserById(@RequestParam("id") Integer id) {
		Optional<User> userData = userServiceImpl.findById(id);
		if (userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/pdfreport")
	public ResponseEntity<InputStreamResource> citiesReport() throws IOException {
		List<User> cities = (List<User>) userServiceImpl.findAll();
		ByteArrayInputStream bis = GeneratePdfReport.citiesReport(cities);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=usersreport.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@GetMapping("/excelreport")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		List<User> listUsers = (List<User>) userServiceImpl.findAll();
		UserExcelExporter excelExporter = new UserExcelExporter(listUsers);
		excelExporter.export(response);
	}
	
	
	
	@GetMapping(value = "/genrateReportFiles", produces = "application/json")
	public ResponseEntity<String> genrateReportFiles() {
		try {
		
		List<User> lUser = userServiceImpl.genrateReportFiles();
		
		for(User user:lUser) {
			user.setUserPost(postModelServiceimpl.getAllPostByUserName(user.getId()));
			user.setUserInvestment(investmentServiceimpl.findInvestmentsByUserId(user.getId()));
		}
			genrateExcelReport.excelReport(lUser);
			return new ResponseEntity<>("Files are genrated", HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>("Files are not genrated",HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		
	}
	
	
	
	@GetMapping(value = "/getOfferBysubCategory", produces = "application/json")
	public List<Offer> getOfferBysubCategory(@RequestParam("subcategoryId") Integer subcategoryId) {
		return  userServiceImpl.getAllOffer(subcategoryId);
	}
	
	
	@GetMapping(value = "/getOfferByCategory", produces = "application/json")
	public List<Map<String,Object>> getOfferByCategory(@RequestParam("categoryId") Integer categoryId) {
		return  ResposeMapping.mappingData(userServiceImpl.getOfferByCategory(categoryId));
		 
	}
	
	@GetMapping(value = "/getAllOfferAfterTenday", produces = "application/json")
	public List<Map<String,Object>> getAllOfferAfterTenday() {
		return  ResposeMapping.mappingData(userServiceImpl.getAllOfferAfterTenday());
		 
	}
	
	
	@GetMapping(value = "/getOfferByUserId", produces = "application/json")
	public List<Map<String,Object>> getOfferByUserId() {
		
		User user = null;
		if (request != null && request.getAttribute("userSession") != null) {
			user = (User) request.getAttribute("userSession");
		}
		return  ResposeMapping.mappingData(userServiceImpl.getOfferByUserId(user.getId()));
		 
	}
	
	
	@PostMapping(value = "/addOffer", produces = "application/json")
	public ResponseEntity<Offer> addOffer(@RequestBody Offer offer) {
		Offer off=	userServiceImpl.saveOffer(offer);
		return new ResponseEntity<Offer>(off, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping(value = "/removeOffer", produces = "application/json")
	public ResponseEntity<String> removeOffer(@RequestParam("offerId") Integer offerId) {
	userServiceImpl.deleteOffer(offerId);
		return new ResponseEntity<String>("deleted record", HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/addCategory", produces = "application/json")
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		category.setCreatedDate(new Date());
		Category categ=	userServiceImpl.saveCategory(category);
		return new ResponseEntity<Category>(categ, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/updateCategory", produces = "application/json")
	public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
		
		category.setUpdatedDate(new Date());
		Category categ=	userServiceImpl.saveCategory(category);
		return new ResponseEntity<Category>(categ, HttpStatus.CREATED);
	}
	
	
	
	@PostMapping(value = "/addSubCategory", produces = "application/json")
	public ResponseEntity<SubCategory> addSubCategory(@RequestBody SubCategory subCategory) {
		SubCategory subcateg=	userServiceImpl.saveSubCategory(subCategory);
		return new ResponseEntity<SubCategory>(subcateg, HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "/updateSubCategory", produces = "application/json")
	public ResponseEntity<SubCategory> updateSubCategory(@RequestBody SubCategory subCategory) {
		
		
		SubCategory subcateg=	userServiceImpl.saveSubCategory(subCategory);
		return new ResponseEntity<SubCategory>(subcateg, HttpStatus.CREATED);
	}



	
	

}
