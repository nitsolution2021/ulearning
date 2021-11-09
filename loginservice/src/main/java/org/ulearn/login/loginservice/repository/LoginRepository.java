package org.ulearn.login.loginservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ulearn.login.loginservice.entity.Login;

public interface LoginRepository extends JpaRepository<Login, Integer> {
	public Optional<Login> findByEmail(String email);
}
