package org.ulearn.instituteservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.ulearn.instituteservice.entity.InstituteEntity;
import org.ulearn.instituteservice.entity.InstituteGlobalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InstituteRepo extends JpaRepository<InstituteEntity, Long> {
	Optional<InstituteEntity> findByInstName(String instName);
	
	Optional<InstituteEntity> findByInstEmail(String instEmail);	
	
	@Query(value = "SELECT instObj FROM InstituteEntity instObj INNER JOIN InstituteAdminEntity instAmdObj on instAmdObj.instId = instObj.instId WHERE instObj.isDeleted = ?1")
	Page<InstituteEntity> findByAllInst(int isDeleted,Pageable pagingSort);
	
	@Query(value = "SELECT instObj FROM InstituteEntity instObj")
	List<InstituteEntity> findByListInst();

	@Query(value = "select * from tbl_institutes where INST_ID != ? and INST_NAME = ? and IS_DELETED = 0",nativeQuery = true)
	List<InstituteEntity> findByInstUnqName(long instId,String instName); 
	
	@Query(value = "select instD from InstituteEntity instD where instD.instId != ?1 and instD.instEmail = ?2")
	List<InstituteEntity> findByInstUnqEmail(long instId,String instEmail);
	 
	@Query(value = "select instAdrObj from InstituteEntity instD INNER JOIN InstituteAddressEntity instAdrObj on instAdrObj.instId = instD.instId INNER JOIN InstituteAdminEntity instAmdObj on instAmdObj.instId = instD.instId")
	List<InstituteGlobalEntity> findByAllInstQuery();	

	@Query(value = "select * from tbl_institutes where IS_DELETED = 0",nativeQuery = true)
	List<InstituteGlobalEntity> findByInstUnq();
	
	@Query(value = "select tbl_institutes.*,tbl_inst_addr.* from tbl_institutes INNER JOIN tbl_inst_addr on tbl_institutes.INST_ID = tbl_inst_addr.INST_ID",nativeQuery = true)
	List<InstituteEntity> func();	
//	instituteAdmin.amdUsername,
	@Query("SELECT instObj FROM InstituteEntity instObj INNER JOIN InstituteAdminEntity instAmdObj on instAmdObj.instId = instObj.instId WHERE instObj.isDeleted = ?1 AND CONCAT(instAmdObj.amdUsername,instObj.instName,instObj.instEmail,instObj.instMnum,instObj.instCnum,instObj.instWebsite,instObj.isntRegDate) LIKE %?2%")
    Page<InstituteEntity> Search(int isDeleted,String sortKey, Pageable pageable);
	
	@Query("SELECT instObj FROM InstituteEntity instObj WHERE instObj.instId =?1 AND instObj.instEmail = ?2")
    List<InstituteEntity> findByIdAndEmail(long instId, String instEmail);
	
	

		
}
