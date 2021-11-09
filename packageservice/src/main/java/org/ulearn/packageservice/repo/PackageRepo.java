package org.ulearn.packageservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ulearn.packageservice.entity.PackageEntity;

@Repository
public interface PackageRepo extends JpaRepository<PackageEntity, Long>{

}
