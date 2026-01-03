package dct.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dct.com.entity.UserDetailsEntity;
import dct.com.model.DCTResponse;
import dct.com.model.UserDetailsModel;
import dct.com.model.UserResponse;
import dct.com.serviceimpl.UserServiceImpl;


@RestController

public class UserDetailsController {
	@Autowired
	 UserServiceImpl userServiceImpl;
	
	
	public UserServiceImpl getUserServiceImpl() {
		return userServiceImpl;
	}


	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}


	
	@PostMapping("/login")
	public DCTResponse loginUser(@RequestBody UserDetailsEntity userInfo) {
		
		String userName=userInfo.getUserName();
		String password=userInfo.getPassword();
		//String role=userInfo.getUserRole();

		System.out.println("Inside controller  login method");
		return getUserServiceImpl().loginUser(userName,password);

	}
	
	
	@PostMapping("/createUser")
	public DCTResponse createUser(@RequestBody UserResponse userInfo) {
		System.out.println("Inside controller  addUser method");
		

		System.out.println("userInfo::::::::"+userInfo.getServiceToken());
		System.out.println("userInfo:::::::::::::"+userInfo.getDbDetail().getEmailId());		
		return getUserServiceImpl().createUser(userInfo.getDbDetail());
	}
	
	@RequestMapping(value ="/delete/{name}", method = RequestMethod.DELETE)
	public DCTResponse deleteUser(@PathVariable String name) {

		System.out.println("Inside controller  deleteUser method");
		return getUserServiceImpl().deleteUser(name);

	}
	
	
	@RequestMapping(value ="/update", method = RequestMethod.PUT)
	public DCTResponse updateUser(@RequestBody UserResponse userInfo) {
		
		System.out.println("Inside controller  update method"+userInfo.getDbDetail().getUserName());
		
		
		String userName=userInfo.getDbDetail().getUserName();
		String remarks=userInfo.getDbDetail().getRemarks();
		String status=userInfo.getDbDetail().getStatus();
		String emailId=userInfo.getDbDetail().getEmailId();
		String userRole=userInfo.getDbDetail().getUserRole();
		
		System.out.println("userRole:::"+userRole);
		String password=userInfo.getDbDetail().getPassword();
		return getUserServiceImpl().updateUser(userName,remarks,status,emailId,userRole,password);

	}

    @GetMapping("/user")
    public DCTResponse getAllUsers() {
    	
    	System.out.println("get all users Info");
           return getUserServiceImpl().getAllUsers();

    }
	
  
	
	/*@RequestMapping(value ="/signout/{emailId}", method = RequestMethod.GET)
	public DCTResponse logout(@PathVariable String emailId) {

		
		System.out.println("Inside controller  logout method");
		return getUserServiceImpl().logout(emailId);

	}*/
	
	@RequestMapping(value ="/logout", method = RequestMethod.GET)
	public DCTResponse logout() {

		DCTResponse dctResponse=new DCTResponse();
		dctResponse.setResult("logout success");
		System.out.println("Inside controller  logout method");
		return dctResponse;

	}


}
