package org.ulearn.login.loginservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ulearn.login.loginservice.entity.LoginResetEntity;


public interface LoginResetRepo  extends JpaRepository<LoginResetEntity,Long>{
	
	public Optional<LoginResetEntity> findByPrToken(String prToken);

}
