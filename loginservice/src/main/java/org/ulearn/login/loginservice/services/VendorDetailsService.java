package org.ulearn.login.loginservice.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ulearn.login.loginservice.entity.LoginEntity;
import org.ulearn.login.loginservice.entity.LoginUserDetails;
import org.ulearn.login.loginservice.exception.CustomeException;
import org.ulearn.login.loginservice.repository.LoginRepository;

@Service
public class VendorDetailsService implements UserDetailsService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<LoginEntity> findByUserName = loginRepository.findByUserName(username);
		if (findByUserName.isPresent()) {
			findByUserName.orElseThrow(() -> new CustomeException("Not found "+username));
			return findByUserName.map(LoginUserDetails :: new).get();
		} else {
			
				throw new CustomeException("Username not found");
		}
	}
	


}
