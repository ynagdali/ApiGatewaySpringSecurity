package com.codedecode.microservices.RegisterService.Security;


	

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import com.codedecode.microservices.RegisterService.Entity.RegisterUser;
import com.codedecode.microservices.RegisterService.repositories.ReigsterRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
    private ReigsterRepo registerRepo;
	
	// InMemoryUserDetailsManager in

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = null;
        List<GrantedAuthority> authorities = null;
        List<RegisterUser> customer = registerRepo.findByName(username);
        if (customer.size() == 0) {
        	System.out.println("herecode");
            throw new UsernameNotFoundException("User details not found for the user : " + username);
        } else{
        	System.out.println("herecode 22");
        	username = customer.get(0).getName();
            password = customer.get(0).getPassword();
            authorities = new ArrayList<>();
        }
        System.out.println("herecode #-->password->"+password+"--->username-->"+username);
        UserDetails ud = 
        		new User(username,password, authorities);
        System.out.println("ud-->"+ud);
        return ud;
    }

}
