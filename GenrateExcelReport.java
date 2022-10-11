package com.project.demo.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.project.demo.model.investment.UserInvestment;
import com.project.demo.model.post.UserPost;
import com.project.demo.model.user.User;



@Service
public class GenrateExcelReport {
	
	public  void excelReport(List<User> lUser) throws IOException 
    {
      
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet=workbook.createSheet(" User Data ");
        XSSFRow row;
        Map<String, Object[]> userData= new TreeMap<String, Object[]>();
  
        
        int count=1;
        
        for(User user:lUser) {
        	
        	
        	
        	for(UserPost userPost:user.getUserPost()) {
        		
        		
        		if(user.getId()==userPost.getUserid()) {
        		
        		userData.put(""+user.getUsername(), new Object[] {String.valueOf(user.getId()) ,
            			user.getUsername(),
            			user.getEmailId(),
            			user.getLoginLimit()+"",
            			user.getUserType(),
            			userPost.getId()+"",
            			userPost.getPostname(),
            			userPost.getPostdescription(),
            			userPost.getCurrDate()+""
            			
            			
            			
            	}
            	);
        		
        		
        		
        		
        		}
        	}
        	
        	
	for(UserInvestment userInvestment:user.getUserInvestment()) {
        		
		if(user.getId()==userInvestment.getUserId()) {
        		userData.put(""+user.getUsername(), new Object[] {String.valueOf(user.getId()) ,
            			user.getUsername(),
            			user.getEmailId(),
            			user.getLoginLimit()+"",
            			user.getUserType(),
            			userInvestment.getId()+"",
            			userInvestment.getPolicyName(),
            			userInvestment.getTenure()+""
            			
            			
            			
            			
            	}
            	);
        		
        		
        	}
	}       	
        	count++;
        }
  
        
  
        Set<String> keyid = userData.keySet();
        
        
        int rowid = 0;
  
        // writing the data into the sheets...
  
        for (String key : keyid) {
  
            row = spreadsheet.createRow(rowid++);
           
            Object[] objectArr = userData.get(key);
            int cellid = 0;
  
            for (Object obj : objectArr) {
   
            	
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
            
            FileOutputStream out = new FileOutputStream(new File("C:/Users/renu_verma/Desktop/Report/"+key+"Reportsheetsheet.xlsx"));
            
            workbook.write(out);
            out.close();  
            
        }
        
    }

}
