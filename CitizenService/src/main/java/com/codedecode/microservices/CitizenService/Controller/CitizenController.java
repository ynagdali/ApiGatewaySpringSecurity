package com.codedecode.microservices.CitizenService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codedecode.microservices.CitizenService.Entity.Citizen;
import com.codedecode.microservices.CitizenService.repositories.CitizenRepo;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codedecode.microservices.CitizenService.Security.*;
import com.codedecode.microservices.CitizenService.Security.JwtResponse;

@RestController
@RequestMapping("/citizen")
public class CitizenController {
	
	@Autowired
	private CitizenRepo repo; 
	
	
	
	/*
	 * @Autowired PasswordEncoder encoder;
	 */
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	
	
	

	@RequestMapping(path ="/test")
	public ResponseEntity<String> test() {
		System.out.println("here inside code");
		return new ResponseEntity<>("hello", HttpStatus.OK);
	}
	
	@RequestMapping(path ="/id/{id}")
	public ResponseEntity<java.util.List<Citizen>> getById(@PathVariable Integer id) {
		
		List<Citizen> listCitizen = repo.findByVaccinationCenterId(id);
		return new ResponseEntity<>(listCitizen, HttpStatus.OK);
	}
	
	@PostMapping(path ="/add")
	public ResponseEntity<?> addCitizen(@RequestBody Citizen newCitizen) {
		
		//code for authentication
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(newCitizen.getName(), "password" ));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		Citizen citizenDetail = (Citizen) authentication.getPrincipal();		
		
		//JwtResponse
		
		return ResponseEntity.ok(new JwtResponse(jwt, 
				citizenDetail.getId(), 
				citizenDetail.getName()));
												
		
		//Citizen citizen = repo.save(newCitizen);
		//return new ResponseEntity<>(citizen, HttpStatus.OK);
	}
	
	
	
}
