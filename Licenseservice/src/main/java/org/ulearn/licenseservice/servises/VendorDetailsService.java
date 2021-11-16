package org.ulearn.licenseservice.servises;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ulearn.licenseservice.entity.LoginEntity;
import org.ulearn.licenseservice.entity.LoginUserDetails;
import org.ulearn.licenseservice.exception.CustomException;
import org.ulearn.licenseservice.repository.LoginRepository;


@Service
public class VendorDetailsService implements UserDetailsService {
	
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
