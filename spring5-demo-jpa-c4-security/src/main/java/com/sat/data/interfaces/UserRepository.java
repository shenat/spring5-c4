package com.sat.data.interfaces;

import org.springframework.data.repository.CrudRepository;

import com.sat.bean.User;

public interface UserRepository extends CrudRepository<User,Long> {
	
	User findByUsername(String username);
}
