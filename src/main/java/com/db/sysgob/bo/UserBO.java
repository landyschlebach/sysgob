package com.db.sysgob.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.db.sysgob.entity.Role;
import com.db.sysgob.entity.User;
import com.db.sysgob.entity.UserBasicInfo;
import com.db.sysgob.repository.RoleRepository;
import com.db.sysgob.repository.UserRepository;

public class UserBO {
	private final String TAG = UserBO.class.getSimpleName();
	private static final Logger log = LoggerFactory.getLogger("sysgob_log");

	private final String ADVANCED_USER = "ADVANCED_USER";
	private final String PM_USER = "PM_USER";
	private final String ACCOUNTANT_USER = "ACCOUNTANT_USER";
	private final String MANAGER_USER = "MANAGER_USER"; 
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public String getView(UserBasicInfo user) {
		Role role = roleRepository.getById(user.getRoleId());
		
		return "";
	}
	
	public void blockUser(User user) {
		log.debug(TAG, "Blocking UserId[" + user.getUserId() + "]");
		
		try {
			user.blocked(true);
			userRepository.updateUser(user);
			log.debug(TAG, "Succesfully blocked UserId[" + user.getUserId() + "]");
		
		} catch(Exception e) {
			log.debug(TAG, "An error has ocurring while trying to block"
			+ " UserId[" + user.getUserId() + "] " + e.getMessage());
		}
	}
	
	public Long getDependencyOfUser(Long userId) {
		log.debug(TAG, "Finding dependencyId for User[" + userId + "]");
		User user = userRepository.getUserById(userId);
		Role role = roleRepository.getById(user.getRoleId());

		log.debug(TAG, "DependencyId=" + role.getDependencyId() + "for User[" + userId + "]");
		return role.getDependencyId();
	}
}
