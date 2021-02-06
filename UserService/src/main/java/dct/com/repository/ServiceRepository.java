package dct.com.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dct.com.entity.UserDetailsEntity;
import dct.com.entity.UserRoleEntity;

@Service
public interface ServiceRepository extends CrudRepository<UserDetailsEntity, Long>{

	UserDetailsEntity findByUserNameAndPassword(String username,String password);
	UserDetailsEntity findById(Long id);
	UserDetailsEntity findByUserName(String username);
	
	@Transactional
	Long deleteByUserName(String name);
	
	List<UserDetailsEntity> findAll();
	UserDetailsEntity findByUserRole(String userrole);
	
//	List<UserRoleEntity> findAll();
		
   /*@Modifying	
   @Transactional
   @Query("update UserDetails c SET c.userName=:userName,c.password=:password,c.remarks=:remarks,c.status=:status,c.emailId=:emailId,c.userRole=:userRole WHERE c.userName=userName")
   int updateUser(@Param("userName") String userName,@Param("password") String password,@Param("remarks") String remarks,@Param("status") String status,@Param("emailId") String emailId,@Param("userRole") String userRole);
*/
	
	@Modifying	
	@Transactional
	@Query("UPDATE UserDetailsEntity c SET c.remarks=:remarks,c.status=:status,c.emailId =:emailId,c.userRole =:userRole, c.password =:password WHERE c.userName=:userName")
	int updateUser(@Param("userName") String userName,@Param("remarks") String remarks,@Param("status") String status,@Param("emailId") String emailId,@Param("userRole") String userRole,@Param("password") String password);
		
	
	@Modifying	
	@Transactional
	@Query("UPDATE UserDetailsEntity c SET c.lastLoginDate=:lastLoginDate WHERE c.userName=:userName")
	int updateUserLoginTime(@Param("userName") String userName, @Param("lastLoginDate")Date lastLoginDate);
	
	   /*@Modifying	
	   @Transactional
	   @Query("UPDATE UserDetails d SET d.logout= :logout WHERE d.emailId=:emailId")
	   int updateLogout(@Param("emailId") String emailId,@Param("logout") boolean logout);
*/


	 /*List<String> getUserRoles(UserDetails userDetails);*/
}