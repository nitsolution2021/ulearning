package org.ulearn.emailtemplateservice.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ulearn.emailtemplateservice.entity.LoginEntity;
import org.ulearn.emailtemplateservice.entity.LoginUserDetails;
import org.ulearn.emailtemplateservice.exception.CustomException;
import org.ulearn.emailtemplateservice.repository.LoginRepository;



@Service
public class SuperAdminDetailsService implements UserDetailsService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<LoginEntity> findByUserName = loginRepository.findByUserName(username);
		if (findByUserName.isPresent()) {
			findByUserName.orElseThrow(() -> new CustomException("Not found "+username));
			return findByUserName.map(LoginUserDetails :: new).get();
		} else {
			
				throw new CustomException("Username not found");
		}
	}
	


}
