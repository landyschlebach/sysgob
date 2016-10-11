package com.db.sysgob.bo;

import org.springframework.beans.factory.annotation.Autowired;

import com.db.sysgob.entity.Role;
import com.db.sysgob.entity.User;
import com.db.sysgob.entity.UserBasicInfo;
import com.db.sysgob.repository.RoleRepository;
import com.db.sysgob.repository.UserRepository;

public class UserBO {

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
		try {
		user.blocked(true);
		userRepository.updateUser(user);
		} catch(Exception e) {
			e.getMessage();
		}
	}
	
	public Long getDependencyOfUser(Long userId) {
		User user = userRepository.getUserById(userId);
		Role role = roleRepository.getById(user.getRoleId());
		
		return role.getDependencyId();
	}
}
