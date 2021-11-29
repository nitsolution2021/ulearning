package org.ulearn.packageservice.repo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ulearn.packageservice.entity.DataResponseEntity;
import org.ulearn.packageservice.entity.PackageEntity;

public interface PackageRepo extends JpaRepository<PackageEntity, Long>{

	@Query(value="select * from tbl_inst_package",nativeQuery = true)
	List<DataResponseEntity> findAlldata();
}
