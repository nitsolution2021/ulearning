package org.ulearn.instituteservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.ulearn.instituteservice.entity.InstituteEntity;
import org.ulearn.instituteservice.entity.InstituteGlobalEntity;

public interface InstituteRepo extends JpaRepository<InstituteEntity, Long> {
	Optional<InstituteEntity> findByInstName(String instName);
	
	Optional<InstituteEntity> findByInstEmail(String instName);
	
	
	@Query(value = "select obj from InstituteEntity obj where obj.instId = ?1 and obj.instName = ?2")
	Optional<InstituteEntity> findByInstUnqName(long instId,String instName); 
	
	@Query(value = "select obj from InstituteEntity obj where obj.instId = ?1 and obj.instEmail = ?2")
	Optional<InstituteEntity> findByInstUnqEmail(long instId,String instEmail);
	 
	@Query(value = "select InstituteEntityOBJ,InstituteAddressEntityOBJ from InstituteEntity InstituteEntityOBJ INNER JOIN InstituteAddressEntity InstituteAddressEntityOBJ on InstituteEntityOBJ.instId = InstituteAddressEntityOBJ.instId")
	List<InstituteGlobalEntity> findByAllInst();
	
	@Query(value = "select * from tbl_institutes",nativeQuery = true)
	List<InstituteGlobalEntity> findByInstUnq();
	
	
	
}
