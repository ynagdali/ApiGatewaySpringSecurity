package com.codedecode.microservices.RegisterService.Controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codedecode.microservices.RegisterService.Entity.RegisterUser;
import com.codedecode.microservices.RegisterService.repositories.ReigsterRepo;


@RestController
public class RegisterCitizenController {
	
	@Autowired
	private ReigsterRepo repo; 
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping(path ="/registerNewCitizen/{name}")
	public List<RegisterUser> getCardDetails(@PathVariable String name, HttpServletResponse response) {
        List<RegisterUser> citizen = repo.searchByNameLike(name);
        response.setStatus(HttpServletResponse.SC_OK);
		return citizen;
    }
	
	
	 @PostMapping(path ="/registerNewCitizen")
	 public ResponseEntity<RegisterUser> addCitizen(@RequestBody RegisterUser newCitizen) {
		 String hashPwd = passwordEncoder.encode(newCitizen.getPassword());
		 newCitizen.setPassword(hashPwd);
		 RegisterUser citizen = repo.save(newCitizen);
		return new ResponseEntity<>(citizen, HttpStatus.OK);
	}
	
	
	
	
	
	
	
}
