package com.codedecode.microservices.RegisterService.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codedecode.microservices.RegisterService.Entity.RegisterUser;
import com.codedecode.microservices.RegisterService.Security.JwtResponse;
import com.codedecode.microservices.RegisterService.Security.JwtUserDetailsService;
import com.codedecode.microservices.RegisterService.Security.TokenUtil;

@RestController
public class AuthenticateCitizenController {


	@Autowired
	JwtUserDetailsService customUserDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping(path ="/authUser")
	public String getCardDetails() {
        return "Here are the card details from the DB auth user";
    }
	
	@GetMapping(path ="/authenticate")
	public String getAuthenticatedRequest() {
        return "Here are the card details from the DB auth user";
    }
	
	//@RequestMapping(value = "/authenticate", method = { RequestMethod.POST })
	@PostMapping(path ="/authUser")
	public ResponseEntity<JwtResponse> authenticate(@RequestBody RegisterUser authenticationRequest) {
		try {
			String username = authenticationRequest.getName();
			String password = authenticationRequest.getPassword();
			
			System.out.println("Username->"+username);
			System.out.println("password->"+password);
			
			//to mark this user as authenticated 
			/*
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
			System.out.println("token->"+token);
			Authentication authentication = this.authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			*/
			
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
			
			System.out.println("username-->password-->"+userDetails.getPassword());
			System.out.println(userDetails.getUsername());
			

			/*
			 * for (GrantedAuthority authority : userDetails.getAuthorities()) {
			 * roles.add(authority.toString()); }
			 */
			
			

			return new ResponseEntity<JwtResponse>(new JwtResponse(TokenUtil.createToken(userDetails) ,
					userDetails.getUsername()), HttpStatus.OK);

		} catch (BadCredentialsException bce) {
			return new ResponseEntity<JwtResponse>(new JwtResponse("",""), HttpStatus.UNPROCESSABLE_ENTITY);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}
	
	/*
	@PostMapping(path ="/authUser")
	public ResponseEntity<RegisterUser> loginUserAndGenerateToken(@RequestBody RegisterUser newCitizen) {
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	*/

}
