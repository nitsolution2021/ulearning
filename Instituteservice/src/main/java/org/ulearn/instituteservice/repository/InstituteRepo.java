package org.ulearn.instituteservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ulearn.instituteservice.entity.InstituteEntrity;

public interface InstituteRepo extends JpaRepository<InstituteEntrity, Long> {
	List<InstituteEntrity> findByInstName(String instName);
}
