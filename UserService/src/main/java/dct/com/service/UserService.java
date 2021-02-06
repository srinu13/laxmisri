package dct.com.service;


import java.util.List;

import org.springframework.stereotype.Service;

import dct.com.entity.UserDetailsEntity;
import dct.com.model.DCTResponse;
import dct.com.model.UserDetailsModel;

@Service
@SuppressWarnings("rawtypes")
public interface UserService {

	public DCTResponse createUser(UserDetailsModel dbDetail);
	public DCTResponse loginUser(String userName,String password);
	public DCTResponse deleteUser(String name);	
	public DCTResponse getAllUsers();
	public DCTResponse updateUser(String userName,String remarks,String status,String emailId,String userRole,String password);
/*	public DCTResponse logout( String emailId);
 * 
*/	
	
	public List<String> getUserRoles(UserDetailsEntity userDetails);
	public String updateUserLoginTime(String userName);
}
	
