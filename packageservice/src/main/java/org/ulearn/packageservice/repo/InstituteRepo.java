package org.ulearn.packageservice.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ulearn.packageservice.entity.InstituteEntity;
@Repository
public interface InstituteRepo extends JpaRepository<InstituteEntity, Long>{

	@Query(value="SELECT instObj FROM InstituteEntity instObj")
	Page<InstituteEntity> findByAllInst(Pageable pagingSort);
//	@Query("SELECT instObj FROM InstituteEntity instObj WHERE CONCAT(packageEntity.pkId,packageEntity.instId,packageEntity.pkName,packageEntity.pkFname,packageEntity.pkCdate,packageEntity.pkValidityNum) LIKE %?1%")
//	Page<InstituteEntity> Search(String string, Pageable pagingSort);

}
