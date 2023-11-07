package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Repo.Repo;
import com.example.demo.Repo.UserRepo;
import com.example.demo.entity.Product;
import com.example.demo.entity1.UserInfo;

@Service
public class pService {

	@Autowired
	private Repo repo;
	
	@Autowired
	private UserRepo repository;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

	public List<Product> getall() {
	List<Product>subject=new ArrayList<>();
	repo.findAll().forEach(subject::add);
		return subject;
	}

	public String post(Product product) {
		repo.save(product);
		return "update";
		
	}

	public String addUser(UserInfo userInfo) {
	 
	        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
	        repository.save(userInfo);
	        return "user added to system ";
	}

	public Product getProduct(Long  id) {
	Optional<Product>p2=repo.findById(id);
	return p2.orElse(null);
	}
}
