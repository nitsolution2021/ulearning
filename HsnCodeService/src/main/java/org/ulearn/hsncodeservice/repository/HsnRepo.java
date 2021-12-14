package org.ulearn.hsncodeservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.ulearn.hsncodeservice.entity.HsnCodeEntity;

public interface HsnRepo extends JpaRepository<HsnCodeEntity, Long> {

	@Query("SELECT Obj FROM HsnCodeEntity Obj WHERE CONCAT(Obj.hsId, Obj.hsCode, Obj.hsDesc, Obj.hsSgst, Obj.hsCgst, Obj.hsIgst, Obj.hsOrder, Obj.servId, Obj.isDeleted, Obj.isActive, Obj.createdOn) LIKE %?1% AND Obj.isDeleted = ?2")
	public Page<HsnCodeEntity> search(String string, int isDelete, Pageable pagingSort);

	@Query(value = "select obj from HsnCodeEntity obj where obj.isDeleted = ?1")
	public Page<HsnCodeEntity> findAllAndDelete(int isDelete, Pageable pagingSort);
	
	public Optional<HsnCodeEntity> findByHsCode(String hsCode);

}
