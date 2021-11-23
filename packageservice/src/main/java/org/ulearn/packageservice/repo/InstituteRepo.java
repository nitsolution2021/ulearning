package org.ulearn.packageservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ulearn.packageservice.entity.InstituteEntity;
@Repository
public interface InstituteRepo extends JpaRepository<InstituteEntity, Long>{

}
