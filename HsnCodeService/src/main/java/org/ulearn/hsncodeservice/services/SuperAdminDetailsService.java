package org.ulearn.hsncodeservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ulearn.hsncodeservice.entity.LoginEntity;
import org.ulearn.hsncodeservice.entity.LoginUserDetails;
import org.ulearn.hsncodeservice.exception.CustomException;
import org.ulearn.hsncodeservice.repository.LoginRepo;


@Service
public class SuperAdminDetailsService implements UserDetailsService {
	
	@Autowired
	LoginRepo loginRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<LoginEntity> findByUserName = loginRepo.findByUserName(username);
		if (findByUserName.isPresent()) {
			findByUserName.orElseThrow(() -> new CustomException("Not found "+username));
			return findByUserName.map(LoginUserDetails :: new).get();
		} else {
			
				throw new CustomException("Username not found");
		}
	}
}
