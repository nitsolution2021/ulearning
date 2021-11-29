package org.ulearn.hsncodeservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.ulearn.hsncodeservice.entity.LoginEntity;

public interface LoginRepo extends JpaRepository<LoginEntity, Long> {
	
	Optional<LoginEntity> findByUserName(String userName);

	public Optional<LoginEntity> findByEmail(String email);
	
	@Query(value = "select login from LoginEntity login where userName = ?1 and accessToken = ?2")
	Optional<LoginEntity> findByUserNameAndToken(String userName,String accessToken);
}
