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
	
	Optional<InstituteEntity> findByInstEmail(String instEmail);
	
	
//	@Query(value = "select instD from InstituteEntity instD where instD.instId <> ?1 and instD.instName = ?2")
	@Query(value = "select * from tbl_institutes where INST_ID != ? and INST_NAME = ?",nativeQuery = true)
	List<InstituteEntity> findByInstUnqName(long instId,String instName); 
	
	@Query(value = "select instD from InstituteEntity instD where instD.instId != ?1 and instD.instEmail = ?2")
	List<InstituteEntity> findByInstUnqEmail(long instId,String instEmail);
	 
//	@Query(value = "select instAdr.* from tbl_institutes ins INNER JOIN tbl_inst_addr instAdr on inst.INST_ID = instAdr.INST_ID",nativeQuery = true)
	@Query(value = "select instAdrObj from InstituteEntity instD INNER JOIN InstituteAddressEntity instAdrObj on instAdrObj.instId = instD.instId")
	List<InstituteGlobalEntity> findByAllInstQuery();	


	@Query(value = "select * from tbl_institutes",nativeQuery = true)
	List<InstituteGlobalEntity> findByInstUnq();
	
	@Query(value = "select tbl_institutes.*,tbl_inst_addr.* from tbl_institutes INNER JOIN tbl_inst_addr on tbl_institutes.INST_ID = tbl_inst_addr.INST_ID",nativeQuery = true)
	List<InstituteEntity> func();

	//select obj from  InstituteEntity obj INNER JOIN InstituteAddressEntity obj1 on obj.instId = obj1.instId;
		//select * from tbl_institutes INNER JOIN tbl_inst_addr on tbl_institutes.INST_ID = tbl_inst_addr.INST_ID;
//	@Query(value = "select * from tbl_institutes",nativeQuery = true)
//	List<InstituteGlobalEntity> findByInstUnq();

	
}
