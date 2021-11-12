package org.ulearn.licenseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ulearn.licenseservice.entity.LicenseLogEntity;

public interface LicenseLogRepo extends JpaRepository<LicenseLogEntity, Long>{

}
