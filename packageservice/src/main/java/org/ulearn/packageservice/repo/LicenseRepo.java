package org.ulearn.packageservice.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.ulearn.packageservice.entity.LicenseEntity;

public interface LicenseRepo extends JpaRepository<LicenseEntity, Long>{

	@Query(value="SELECT * FROM tbl_inst_license WHERE INST_ID= ?1",nativeQuery = true)
	Optional<LicenseEntity> findInstOptional(Long instId);
	@Query(value="SELECT * FROM tbl_inst_license WHERE INST_ID= ?1",nativeQuery = true)
	List<LicenseEntity> findInst(Long instId);
}
