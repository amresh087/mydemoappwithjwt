package com.project.demo.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.project.demo.model.category.Category;
import com.project.demo.model.category.Grade;
import com.project.demo.model.category.Offer;
import com.project.demo.model.category.SubCategory;

public class ResposeMapping {
	
	public  static List<Map<String,Object>> mappingData(List<Offer> olist) {
		 System.out.println(olist.size());
		
		  
		  List<Map<String,Object>> al=new ArrayList<>();
		 
		   
			for (Offer offer : olist) {
				 Map<String,Object> alldata= new LinkedHashMap<String,Object>();
				alldata.put("id", offer.getId());
				alldata.put("offerName", offer.getOfferName());
				alldata.put("description", offer.getDescription());
				alldata.put("discount", offer.getDiscount());
				alldata.put("Price", offer.getPrice());
				alldata.put("primeuser", offer.getPrimeuser());
				alldata.put("createdDate", offer.getCreatedDate());

				SubCategory subCategory = offer.getSubCategoryId();
						Map<String, Object> categoryMap = new LinkedHashMap<>();
						Category category = subCategory.getCategoryId();
						categoryMap.put("id", category.getId());
						categoryMap.put("categoryName", category.getCategoryName());
						categoryMap.put("categoryDescription", category.getCategoryDescription());
						categoryMap.put("createdDate", category.getCreatedDate());
						List<Map<String, Object>> subCategoryList=new ArrayList<>();
						List<SubCategory> scList=category.getSubCategory();
						for(SubCategory sc:scList) {
							Map<String, Object> subCategoryMap = new LinkedHashMap<>();
							subCategoryMap.put("id",sc.getId());
							subCategoryMap.put("subcategoryName",sc.getSubcategoryName());
							subCategoryMap.put("subcategoryDescription",sc.getSubcategoryDescription());
							subCategoryMap.put("createdDate",sc.getCurrDate());
							subCategoryList.add(subCategoryMap);
						}
						categoryMap.put("subCategory",subCategoryList );
				alldata.put("Category", categoryMap);
				
				 Map<String,Object> gradeMap= new LinkedHashMap<String,Object>();
				 Grade grade=offer.getGradeId();
				 gradeMap.put("id", grade.getId());
				 gradeMap.put("gradeName", grade.getGradename());
				 gradeMap.put("description", grade.getDescription());
				 gradeMap.put("userId", grade.getUserid());
				alldata.put("grade", gradeMap);
				
				al.add(alldata);
			}
		  
		  
		  
		  return al;
		
	}

}
