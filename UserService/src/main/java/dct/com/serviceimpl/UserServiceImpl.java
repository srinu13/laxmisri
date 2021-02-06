package dct.com.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import dct.com.constants.Constants;
import dct.com.entity.UserDetailsEntity;
import dct.com.entity.UserRoleEntity;
import dct.com.model.DCTResponse;
import dct.com.model.UserDetailsModel;
import dct.com.repository.ServiceRepository;
import dct.com.repository.UserRoleRepository;
import dct.com.service.UserService;

@Service
@SuppressWarnings("rawtypes")
public class UserServiceImpl implements UserService {
	@Autowired
	ServiceRepository serviceRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;

	@Override
	public DCTResponse loginUser(String userName,String password) {

		DCTResponse dctResponse=new DCTResponse();
		UserDetailsEntity loginInfo =  serviceRepository.findByUserNameAndPassword(userName,password);
		if(loginInfo==null) {
			dctResponse.setErrMessage("username or password is invalid");
			dctResponse.setResult(Constants.FAILURE);

		}else {
			dctResponse.setErrMessage("username or password is valid");
			dctResponse.setResult(Constants.SUCCESS);
			dctResponse.setRole(loginInfo.getUserRole());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			updateUserLoginTime(userName);
			
			
		}
		
		return dctResponse;	
	}
	
	
	@Override
	public DCTResponse createUser(UserDetailsModel dbDetail) {
		DCTResponse dctResponse=new DCTResponse();
		
	
		UserDetailsEntity userExists=serviceRepository.findByUserName(dbDetail.getUserName());
		UserDetailsEntity userbody=new UserDetailsEntity();
		if(userExists!=null) {
			dctResponse.setErrMessage("There is a user is registred with the username provided");
			dctResponse.setResult(Constants.FAILURE);
		}else {
		userbody.setUserName(dbDetail.getUserName());
		userbody.setPassword(dbDetail.getPassword());
		userbody.setRemarks(dbDetail.getRemarks());
		userbody.setStatus(dbDetail.getStatus());
		userbody.setEmailId(dbDetail.getEmailId());
		userbody.setUserRole(dbDetail.getUserRole());
		Date userDate=new Date();
		
		userbody.setCreationDate(userDate);	
		serviceRepository.save(userbody);
		
		dctResponse.setErrMessage("Create User Sucess");
		dctResponse.setResult(Constants.SUCCESS);

	}
		return dctResponse;
	}
	
	@Override
	public DCTResponse deleteUser(String name) {
		DCTResponse dctResponse=new DCTResponse();

		UserDetailsEntity userDetails=serviceRepository.findByUserName(name);
		if(userDetails!=null) {
			
			dctResponse.setErrMessage("User delete Sucess");
			dctResponse.setResult(Constants.SUCCESS);

			serviceRepository.deleteByUserName(userDetails.getUserName());
		}else {
			
			dctResponse.setErrMessage("Unauthorized User");
			dctResponse.setResult(Constants.FAILURE);
		}
		return dctResponse;
	
	}
		
	@Override
	public DCTResponse updateUser(String userName,String remarks,String status,String emailId,String userRole,String password ) {
		
		DCTResponse dctResponse=new DCTResponse();
		UserDetailsEntity updateUser =  serviceRepository.findByUserName(userName);	
		if(updateUser==null) {
			dctResponse.setErrMessage("Update User Failed");
			dctResponse.setResult(Constants.FAILURE);	
		}else {
			
			int res=serviceRepository.updateUser(userName,remarks, status, emailId,userRole,password);
			if(res==1) {
				dctResponse.setErrMessage("Update User success");
				dctResponse.setResult(Constants.SUCCESS);
			}else {
				dctResponse.setErrMessage("Error occured while updating the User Failed");
				dctResponse.setResult(Constants.FAILURE);	
			}

		}	
		return dctResponse;
		
	}

	@Override
	public String updateUserLoginTime(String userName) {		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date loginDate=new Date();
		sdf.format(loginDate);
		int res = serviceRepository.updateUserLoginTime(userName,loginDate);
		if (res == 1) {
			return "success";
		} else {
			return "Fail";
		}	
		
	}

	
/*	@Override
	public DCTResponse logout(String emailId)
 {
		DCTResponse dctResponse=new DCTResponse();
		int count=serviceRepository.updateLogout(emailId,false);
		if(count==1) {
			
			dctResponse.setErrMessage("User logout Sucess");
			dctResponse.setResult(Constants.SUCCESS);

		}else {
			
			dctResponse.setErrMessage("Error occured while logout");
			dctResponse.setResult(Constants.FAILURE);
		}
		return dctResponse;
	
	}*/
	
	
	@SuppressWarnings("unchecked")

    public DCTResponse getAllUsers() {

		  try {
        	      DCTResponse response = new DCTResponse();
                  List<UserDetailsEntity> userInfo = serviceRepository.findAll();
                  List<UserRoleEntity> userroles = userRoleRepository.findAll();
                  List res = new ArrayList<>();            
                  List rolesInfo = new ArrayList<>();    
                  if (userInfo.isEmpty()) {
                        response.setErrMessage("Error Occured");
                        response.setErrMessage("No User's Available Please add the User's");
                        response.setResult(Constants.FAILURE);
                        return response;

                  } else {
                	  System.out.println("User deatils exist");
                        for (UserDetailsEntity users : userInfo) {                    
                        	         
                                      Map userValues = new HashMap<>();
                                      userValues.put(Constants.EMAILID, users.getEmailId());
                                      userValues.put(Constants.REMARKS, users.getRemarks());
                                      userValues.put(Constants.USERNAME, users.getUserName());
                                      userValues.put(Constants.ROLE, users.getUserRole());
                                      userValues.put(Constants.STATUS, users.getStatus());
                                    /*  System.out.println("Before Printing");
                                      System.out.println("Login Time In date format "+ users.getCreationDate());
                                      System.out.println("Last Login Time In date format "+ users.getLastLoginDate());*/
                                      
                                     
                                      if(null!=users.getCreationDate())
                                      {
                                    	  System.out.println("Login Time "+ users.getCreationDate().toString());
                                    	  userValues.put(Constants.CREATEDDATE, users.getCreationDate().toString());
                                      }
                                      
                                      if(null!=users.getLastLoginDate())
                                      {
                                    	  System.out.println("Last Login Time "+ users.getLastLoginDate().toString());
                                    	  userValues.put(Constants.LASTLOGINDATE, users.getLastLoginDate().toString());
                                          
                                      }
                                      
                                    
                                     res.add(userValues);
                                     
                                                                      
                               }
                        response.setUserList(res);
                  
                      if(userroles.isEmpty())
                      {
                    	  response.setErrMessage("Error Occured");
                          response.setErrMessage("No User Roles Available Please add the User Roles");
                          response.setResult(Constants.FAILURE);
                          return response;
                      }
                      else
                      {
                    	for(UserRoleEntity userRoleEntity: userroles)  
                    	{
                    		
                    		rolesInfo.add(userRoleEntity.getUserRole());    					
                    	}
                    	response.setRoleList(rolesInfo);
                      }
                        
		
				response.setResult(Constants.SUCCESS);
				return response;

                  }
		  }catch (Exception e){
 

  	      DCTResponse response = new DCTResponse();

            response.setErrMessage("error ouccred ");
            response.setResult(Constants.FAILURE);

            return response;

     }
	}
	
	public List<String> getUserRoles(UserDetailsEntity userDetails)
	{
		List<String> userRoles = null;
		try
		{
			//userRoles = serviceRepository.getUserRoles(userDetails);
		}
		catch(Exception e)
		{
			
		}
		return userRoles;
	}
}
     

     