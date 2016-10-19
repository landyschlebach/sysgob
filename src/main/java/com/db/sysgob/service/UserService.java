package com.db.sysgob.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.sysgob.entity.User;
import com.db.sysgob.entity.UserBasicInfo;
import com.db.sysgob.repository.UserRepository;

@Service
public class UserService {
	private final String TAG = UserService.class.getSimpleName();
	private static final Logger log = LoggerFactory.getLogger("sysgob_log");

	@Autowired
	private UserRepository userRepository;
	
	public boolean create(User user){
		log.debug(TAG, "WebService [CREATE]");
		boolean result = false;
		
		try {
			userRepository.newUser(user);
			result = true;

			log.debug(TAG, "Successfully created User [" + user +"]");
		} catch (Exception e) {
			log.debug(TAG, e.getMessage());
		}
		
		return result;
	}
	
	public boolean modify(User user){
		log.debug(TAG, "WebService [UPDATE]");
		boolean result = false;
		
		try {
			userRepository.updateUser(user);
			result = true;

			log.debug(TAG, "Successfully updated User [" + user +"]");
		} catch (Exception e) {
			log.debug(TAG, e.getMessage());
		}
		
		return result;
	}
	
	public User findById(Long id){
		User result = null;
		
		try {
			result = userRepository.getUserById(id);
		} catch (Exception e){
			log.debug(TAG, e.getMessage());
		}
		
		return result;
	}
	
	public UserBasicInfo findByUsername(String username){
		UserBasicInfo result = null;
		
		try {
			result = userRepository.getUserByName(username);
		} catch (Exception e){
			log.debug(TAG, e.getMessage());
		}
		
		return result;
	}
}
