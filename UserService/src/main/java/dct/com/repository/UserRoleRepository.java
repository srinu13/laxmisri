package dct.com.repository;

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
public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Long>{

	
	List<UserRoleEntity> findAll();
		
   
}