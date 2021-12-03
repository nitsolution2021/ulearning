package org.ulearn.packageservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ulearn.packageservice.entity.LicenseEntity;

public interface LicenseRepo extends JpaRepository<LicenseEntity, Long>{

}
