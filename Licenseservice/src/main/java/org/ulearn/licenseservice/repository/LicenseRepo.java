package org.ulearn.licenseservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.ulearn.licenseservice.entity.LicenseEntity;

public interface LicenseRepo extends JpaRepository<LicenseEntity, Integer> {

	@Query(value="select * from tbl_inst_license where IS_DELETED = 0", nativeQuery = true)
	List<LicenseEntity> findAllIsNotDeleted();

	Optional<LicenseEntity> findByInstId(Long instId);
	
	@Query("SELECT licObj FROM LicenseEntity licObj WHERE licObj.isDeleted = 0 AND CONCAT(licObj.lcName, licObj.lcType, licObj.lcStype, licObj.lcValidityType, licObj.lcComment, licObj.lcStatus) LIKE %?1%")
	Page<LicenseEntity> Search(String keyword, Pageable pagingSort);

	
//	@Query(value = "SELECT licenseObj,instituteObj.INST_NAME FROM tbl_inst_license licenseObj, tbl_institutes instituteObj WHERE licenseObj.isDeleted = 0 and instituteObj.INST_ID =licenseObj.INST_ID")
	@Query("SELECT licenseObj FROM LicenseEntity licenseObj WHERE licenseObj.isDeleted = 0")
	Page<LicenseEntity> findByAllLicense(Pageable pagingSort);
	
	//UPDATE `ulearn_dev_sadmin`.`tbl_inst_license` SET `LC_STATUS` = 'Suspend' WHERE (`LC_ID` = '5');
//	@Modifying
//	@Query("UPDATE LicenseEntity licenseObj SET licenseObj.lcStype = :lStatus WHERE licenseObj.lcId = :lcId")
//	void updateLicenseStatusById(@Param("lStatus") String lStatus,@Param("lcId") int lcId);
//	@Query("UPDATE LicenseEntity licenseObj SET licenseObj.lcStype = ?1 WHERE licenseObj.lcId = ?2")
//	void updateLicenseStatusById(String lStatus,int lcId);
	@Query(value="UPDATE tbl_inst_license SET LC_STATUS = ?1 WHERE LC_ID = ?2",nativeQuery = true)
	void updateLicenseStatusById(String lStatus,int lcId);
}
