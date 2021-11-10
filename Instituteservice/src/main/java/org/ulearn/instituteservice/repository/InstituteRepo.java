package org.ulearn.instituteservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.ulearn.instituteservice.entity.InstituteEntrity;

public interface InstituteRepo extends JpaRepository<InstituteEntrity, Long> {
	Optional<InstituteEntrity> findByInstName(String instName);
	
	@Query(value = "select INST_NAME from tbl_institutes where INST_ID <> ? and INST_NAME=?",nativeQuery = true)
	Optional<InstituteEntrity> findByInstUnqName(long INST_ID,String instName);
	
	@Query(value = "select INST_EMAIL from tbl_institutes where INST_ID <> ? and INST_EMAIL=?",nativeQuery = true)
	Optional<InstituteEntrity> findByInstUnqEmail(long INST_ID,String instEmail);
	
	
	
}
