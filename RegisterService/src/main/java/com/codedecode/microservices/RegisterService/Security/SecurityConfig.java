package com.codedecode.microservices.RegisterService.Security;

import javax.servlet.http.HttpServletResponse; // @EnableWebSecurity
import javax.sql.DataSource;

import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.codedecode.microservices.RegisterService.Entity.RegisterUser;
import com.codedecode.microservices.RegisterService.repositories.ReigsterRepo;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
	
	@Autowired
	JwtUserDetailsService userDetailsService;

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		//http.addFilterBefore(new SimpleCORSFilter(), BasicAuthenticationFilter.class);
		http.addFilterBefore(new AuthTokenFilter(userDetailsService), BasicAuthenticationFilter.class);
		
		http.authorizeRequests().antMatchers("/authenticate").authenticated()
		.antMatchers("/authUser").permitAll().antMatchers("/registerNewCitizen").permitAll()
				.and().formLogin().and().httpBasic();
		http.authenticationProvider(authenticationProvider());
		return http.build();

	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	  @Bean
	  public UserDetailsService userDetailsService(DataSource dataSource) {
	    return new JdbcUserDetailsManager(dataSource);
	  }
	  
	  @Bean
	  public DaoAuthenticationProvider authenticationProvider() {
	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	      authProvider.setUserDetailsService(userDetailsService);
	      authProvider.setPasswordEncoder(passwordEncoder());
	      return authProvider;
	  }
	  
	  @Bean
	  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
	    return authConfiguration.getAuthenticationManager();
	  }

}