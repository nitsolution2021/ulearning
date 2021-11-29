package org.ulearn.licenseservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.ulearn.licenseservice.entity.LicenseEntity;

public interface LicenseRepo extends JpaRepository<LicenseEntity, Long> {

	@Query(value="select * from tbl_inst_license where IS_DELETED = 0", nativeQuery = true)
	List<LicenseEntity> findAllIsNotDeleted();

	Optional<LicenseEntity> findByInstId(Long instId);
	
	@Query("SELECT licObj FROM LicenseEntity licObj WHERE licObj.isDeleted = 0 AND CONCAT(licObj.lcName, licObj.lcType, licObj.lcStype, licObj.lcValidityType, licObj.lcComment, licObj.lcStatus) LIKE %?1%")
	Page<LicenseEntity> Search(String keyword, Pageable pagingSort);

	@Query(value = "SELECT licenseObj FROM LicenseEntity licenseObj WHERE licenseObj.isDeleted = 0")
	Page<LicenseEntity> findByAllLicense(Pageable pagingSort);

}
