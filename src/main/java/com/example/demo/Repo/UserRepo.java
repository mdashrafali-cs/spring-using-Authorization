package com.example.demo.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity1.UserInfo;

@Repository
public interface UserRepo extends JpaRepository<UserInfo, Integer> {

	Optional<UserInfo> findByName(String username);

}
