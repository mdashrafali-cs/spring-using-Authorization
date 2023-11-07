package com.example.demo.config;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity1.UserInfo;

import java.util.Arrays;

public class UserInfoUserDetails implements UserDetails {

    private String name;
    private String password;
    private List<GrantedAuthority> authorities;
    
    public UserInfoUserDetails(UserInfo userInfo) {
    	name=userInfo.getName();
    	password=userInfo.getPassword();
    	authorities=Arrays.stream(userInfo.getRoles().split(","))
    			.map(SimpleGrantedAuthority::new)
    			.collect(Collectors.toList());
    	
    }
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return name;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
    
}
