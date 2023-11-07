package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repo.AuthenticationResponse;
import com.example.demo.config.UserInfoUserDetailsService;
import com.example.demo.entity.AuthRequeste;
import com.example.demo.entity.Product;
import com.example.demo.entity1.UserInfo;
import com.example.demo.service.JwtService;
import com.example.demo.service.pService;


import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins ="*")
@RequestMapping("/products")
public class Controller {

	@Autowired
	private pService service;
	
	@Autowired
	private UserInfoUserDetailsService userDetailsService;
	
	@Autowired
	private JwtService jwtservice;
	
	@Autowired
	private AuthenticationManager authenticationManager;

    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }
	
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Product> getall() {
		return service.getall();
	}
    
	@PostMapping("/post")
	public String post(@RequestBody Product product) {
	return	service.post(product);
	}
	

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Product>  getProductById(@PathVariable Long id) {
    	Product p1=service.getProduct(id);
    	if(p1 != null) {
        return ResponseEntity.ok(p1);
    	}else {
    		return ResponseEntity.notFound().build();
    	}
    }
    
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequeste authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
    	return jwtservice.generataToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }


    @PostMapping("/authenticate1")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthRequeste authRequest, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        try {
        	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return null;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        final String jwt = jwtservice.generataToken(userDetails.getUsername());

        return new AuthenticationResponse(jwt);

    }

    
    
}
