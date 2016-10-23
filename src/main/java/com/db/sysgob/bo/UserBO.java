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
	private static final Logger log = LoggerFactory.getLogger(UserBO.class);

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
		log.debug("Blocking " + user);
		
		try {
			//user.blocked(1);
			userRepository.updateUser(user);
			log.debug("Succesfully blocked " + user);
		
		} catch(Exception e) {
			log.debug("An error has ocurring while trying to block"
			+ user + " " + e.getMessage());
		}
	}
	
	public Long getDependencyOfUser(Long userId) {
		log.debug("Finding dependencyId for User[" + userId + "]");
		User user = userRepository.getUserById(userId);
		Role role = roleRepository.getById(user.getRoleId());

		log.debug("DependencyId=" + role.getDependencyId() + "for User[" + userId + "]");
		return role.getDependencyId();
	}
}
