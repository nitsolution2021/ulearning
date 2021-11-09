package org.ulearn.login.loginservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ulearn.login.loginservice.entity.LoginEntity;


public interface LoginRepository extends JpaRepository<LoginEntity, Integer> {
	Optional<LoginEntity> findByUserName(String userName);
}
