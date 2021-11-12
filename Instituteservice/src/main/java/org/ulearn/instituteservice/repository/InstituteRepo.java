package org.ulearn.instituteservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.ulearn.instituteservice.entity.InstituteEntity;
import org.ulearn.instituteservice.entity.InstituteAddressEntity;
import org.ulearn.instituteservice.entity.InstituteGlobalEntity;

public interface InstituteRepo extends JpaRepository<InstituteEntity, Long> {
	Optional<InstituteEntity> findByInstName(String instName);
	
	Optional<InstituteEntity> findByInstEmail(String instName);
	
	
	@Query(value = "select INST_NAME from tbl_institutes where INST_ID <> ? and INST_NAME=?",nativeQuery = true)
	Optional<InstituteEntity> findByInstUnqName(long INST_ID,String instName); 
	
	@Query(value = "select INST_EMAIL from tbl_institutes where INST_ID <> ? and INST_EMAIL=?",nativeQuery = true)
	Optional<InstituteEntity> findByInstUnqEmail(long INST_ID,String instEmail);
	 
//	@Query(value = "select tbl_institutes.INST_NAME from tbl_institutes INNER JOIN tbl_inst_addr on tbl_institutes.INST_ID = tbl_inst_addr.INST_ID",nativeQuery = true)
	List<InstituteEntity> findAllByInstId(Long instId);
	
	//select customer from CustomerRegister customer , CustomerDepartments dep where customer.userId = dep.primaryContact and dep.departmentId = ?1
	
		//select * from tbl_institutes INNER JOIN tbl_inst_addr on tbl_institutes.INST_ID = tbl_inst_addr.INST_ID;
	
}
