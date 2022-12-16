package com.codedecode.microservices.RegisterService.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;

import com.codedecode.microservices.RegisterService.Entity.RegisterUser;

public interface ReigsterRepo extends JpaRepository<RegisterUser, Integer>{
	
	public List<RegisterUser> findByName(String name);
	
	@Query("SELECT m FROM RegisterUser m WHERE m.name LIKE %:name%")
	public List<RegisterUser> searchByNameLike(@Param("name") String name);
	
	

}
