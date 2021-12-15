package org.ulearn.packageservice.repo;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import org.ulearn.packageservice.entity.DataResponseEntity;
import org.ulearn.packageservice.entity.PackageEntity;

public interface PackageRepo extends JpaRepository<PackageEntity, Long>{

	@Query(value="select * from tbl_inst_package",nativeQuery = true)
	List<PackageEntity> findAlldata();
	@Query(value = "SELECT packObj FROM PackageEntity packObj WHERE packObj.isDeleted = 0")
	List<PackageEntity> findByListInst();
	@Query("SELECT packObj FROM PackageEntity packObj WHERE CONCAT(instituteEntity.instName,packObj.pkCdate,packObj.pkFname,packObj.pkNusers,packObj.pkStatus,packObj.pkValidityType) LIKE %?1% AND packObj.isDeleted = ?2")
	Page<PackageEntity> Search(String string, int isDeleted,Pageable pageSort);
	@Query(value = "SELECT packObj FROM PackageEntity packObj WHERE packObj.isDeleted = ?1")
	Page<PackageEntity> findByListpackage(Pageable pageSort,int isDeleted);
	@Query(value="select * from tbl_inst_package where IS_ACTIVE=2",nativeQuery = true)
	List<PackageEntity> recentData();
//	@Query("select packObj from PackageEntity packObj where licenseEntity.instId = ?1")
//	Optional<PackageEntity> licExist(Long instId);
	//@Query(value="SELECT * FROM tbl_inst_package WHERE INST_ID= ?1",nativeQuery = true)
	@Query("SELECT packObj FROM PackageEntity packObj WHERE packObj.instId = ?1")
	List<PackageEntity> instData(Long instId);
	@Query("SELECT packObj FROM PackageEntity packObj WHERE packObj.instId = ?1")
	Optional<PackageEntity> findPackageData(Long instId);
	@Query("SELECT packObj FROM PackageEntity packObj WHERE packObj.instId = ?1 AND packObj.isDeleted = ?2")
	Page<PackageEntity> getInstData(long instId, Pageable pagingSort,int isDelete);
	
	
}