package com.example.demo.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;
@Repository
public interface Repo extends JpaRepository<Product, Long> {

}
