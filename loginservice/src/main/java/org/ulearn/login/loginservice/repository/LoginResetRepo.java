package org.ulearn.login.loginservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ulearn.login.loginservice.entity.LoginResetEntity;

@Repository
public interface LoginResetRepo  extends JpaRepository<LoginResetEntity,Long>{
	

}
