package org.ulearn.login.loginservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ulearn.login.loginservice.entity.GlobalEntity;
import org.ulearn.login.loginservice.entity.GlobalResponse;
import org.ulearn.login.loginservice.entity.LoginEntity;
import org.ulearn.login.loginservice.repository.LoginRepository;

@Service
public class LoginService {

	@Autowired
	private LoginRepository loginRepository;

	public GlobalResponse changePass(GlobalEntity globalEntity, String token) {
		if (!token.equals(null)) {
			Optional<LoginEntity> findByEmail = loginRepository.findByEmail(globalEntity.getEmail());
			if (findByEmail.isPresent()) {
				if (findByEmail.get().getPassword().equals(globalEntity.getOldPass())) {
					LoginEntity login = new LoginEntity();
					login.setFirstName(findByEmail.get().getFirstName());
					login.setLastName(findByEmail.get().getLastName());
					login.setAccessToken(findByEmail.get().getAccessToken());
					login.setEmail(findByEmail.get().getEmail());
					login.setPpic(findByEmail.get().getPpic());
					login.setPassword(globalEntity.getNewPass());
					login.setUid(findByEmail.get().getUid());
					login.setUserName(findByEmail.get().getUserName());
					loginRepository.save(login);
					return new GlobalResponse("Success", "Password changed");
				} else {
					return new GlobalResponse("Faild", "Password not match");
				}
			} else {
				return new GlobalResponse("Faild", "User not found");
			}
		} else {
			return new GlobalResponse("Faild", "Token not valid");
		}
	}
}
