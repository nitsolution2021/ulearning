package org.ulearn.instituteservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.ulearn.instituteservice.entity.InstituteEntity;

public interface InstituteRepo extends JpaRepository<InstituteEntity, Long> {
	Optional<InstituteEntity> findByInstName(String instName);
	
	Optional<InstituteEntity> findByInstEmail(String instName);
	
	
	@Query(value = "select INST_NAME from tbl_institutes where INST_ID <> ? and INST_NAME=?",nativeQuery = true)
	Optional<InstituteEntity> findByInstUnqName(long INST_ID,String instName);
	
	@Query(value = "select INST_EMAIL from tbl_institutes where INST_ID <> ? and INST_EMAIL=?",nativeQuery = true)
	Optional<InstituteEntity> findByInstUnqEmail(long INST_ID,String instEmail);
	
	
	
}
