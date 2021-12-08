package org.ulearn.packageservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ulearn.packageservice.entity.PackageLogEntity;

public interface PackageLogRepo extends JpaRepository<PackageLogEntity, Long>{

	
}