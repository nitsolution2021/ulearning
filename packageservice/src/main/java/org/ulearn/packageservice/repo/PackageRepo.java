package org.ulearn.packageservice.repo;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ulearn.packageservice.entity.DataResponseEntity;
import org.ulearn.packageservice.entity.PackageEntity;

public interface PackageRepo extends JpaRepository<PackageEntity, Long>{

	@Query(value="select * from tbl_inst_package",nativeQuery = true)
	List<DataResponseEntity> findAlldata();
//	@Query(value="SELECT packObj FROM PackageEntity packObj WHERE packObj.isDeleted = 0 AND CONCAT(packObj.pkId,packObj.instId,packObj.pkName,packObj.pkFname,packObj.pkValidityNum) LIKE %?1%" )
//	Page<PackageEntity> Search(String string, Pageable pagingSort);
//	
//	Page<PackageEntity> findByAllInst(Pageable pagingSort);
	@Query(value = "SELECT packObj FROM PackageEntity packObj WHERE packObj.isDeleted = 0")
	List<PackageEntity> recentlyAddedData();
//	@Query(value="SELECT packObj FROM packageEntity packObj WHERE packObj.isDeleted =0")
//	Page<PackageEntity> findByAllInst(Pageable pagingSort);
}
