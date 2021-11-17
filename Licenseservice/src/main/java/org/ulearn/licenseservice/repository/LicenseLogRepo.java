package org.ulearn.licenseservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.ulearn.licenseservice.entity.LicenseLogEntity;

public interface LicenseLogRepo extends JpaRepository<LicenseLogEntity, Long>{

	@Query(value="select * from tbl_inst_license_log where LC_ID = ?1 and LL_ACTION = ?2",nativeQuery = true)
	Optional<LicenseLogEntity> findByLcId(long lcId, String string);

}
