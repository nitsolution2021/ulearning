package org.ulearn.packageservice.repo;

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

}
