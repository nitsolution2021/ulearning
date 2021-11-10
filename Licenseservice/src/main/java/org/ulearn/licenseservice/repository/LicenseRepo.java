package org.ulearn.licenseservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.ulearn.licenseservice.entity.LicenseEntity;

public interface LicenseRepo extends JpaRepository<LicenseEntity, Long> {

	@Query(value="select * from tbl_inst_license where IS_DELETED = 0", nativeQuery = true)
	List<LicenseEntity> findAllIsNotDeleted();

}
