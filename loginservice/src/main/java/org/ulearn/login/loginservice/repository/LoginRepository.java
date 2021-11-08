package org.ulearn.login.loginservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ulearn.login.loginservice.entity.Login;

public interface LoginRepository extends JpaRepository<Login, Integer> {

}
