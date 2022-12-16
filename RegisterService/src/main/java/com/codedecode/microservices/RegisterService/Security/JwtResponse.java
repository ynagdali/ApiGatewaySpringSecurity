package com.codedecode.microservices.RegisterService.Security;

import org.springframework.http.HttpStatus;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private int id;
  private String username;
  private HttpStatus status;
  

  public HttpStatus getStatus() {
	return status;
}

public void setStatus(HttpStatus status) {
	this.status = status;
}

public JwtResponse(String accessToken, String username) {
    this.token = accessToken;
    this.username = username;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }



  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  
}