package com.db.sysgob.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.sysgob.entity.User;
import com.db.sysgob.entity.UserBasicInfo;
import com.db.sysgob.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public boolean create(User user){
		boolean result = false;
		
		try {
			userRepository.newUser(user);
			result = true;
		} catch (Exception e) {
			e.getMessage();
		}
		
		return result;
	}
	
	public boolean modify(User user){
		boolean result = false;
		
		try {
			userRepository.updateUser(user);
			result = true;
		} catch (Exception e) {
			e.getMessage();
		}
		
		return result;
	}
	
	public User findById(Long id){
		User result = null;
		
		try {
			result = userRepository.getUserById(id);
		} catch (Exception e){
			e.getMessage();
		}
		
		return result;
	}
	
	public UserBasicInfo findByUsername(String username){
		UserBasicInfo result = null;
		
		try {
			result = userRepository.getUserByName(username);
		} catch (Exception e){
			e.getMessage();
		}
		
		return result;
	}
}
