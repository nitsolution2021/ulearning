package org.ulearn.hsncodeservice.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.ulearn.hsncodeservice.entity.GlobalResponseEntity;
import org.ulearn.hsncodeservice.entity.HsnCodeEntity;
import org.ulearn.hsncodeservice.exception.CustomException;
import org.ulearn.hsncodeservice.repository.HsnRepo;

@Service
public class HsnServices {
	private static final Logger LOGGER = LoggerFactory.getLogger(HsnServices.class);

	@Autowired
	private HsnRepo hsnRepo;

	public GlobalResponseEntity addHsnCode(HsnCodeEntity hsnCodeEntity) {
		LOGGER.info("Inside - HsnServices.addHsnCode()");
		try {
			Optional<HsnCodeEntity> findByHsCode = hsnRepo.findByHsCode(hsnCodeEntity.getHsCode());
			if (findByHsCode.isPresent()) {
				throw new CustomException("This HSN Number is Already Present");
			} 
			HsnCodeEntity entity = new HsnCodeEntity();
			entity.setCreatedOn(new Date());
			entity.setHsCgst(hsnCodeEntity.getHsCgst());
			entity.setHsIgst(hsnCodeEntity.getHsIgst());
			entity.setHsSgst(hsnCodeEntity.getHsSgst());
			entity.setHsCode(hsnCodeEntity.getHsCode());
			entity.setHsDesc(hsnCodeEntity.getHsDesc());
			entity.setIsActive(1);
			entity.setIsDeleted(0);
			entity.setServId(hsnCodeEntity.getServId());
			HsnCodeEntity save = hsnRepo.save(entity);
			if (save.equals(null)) {
				throw new CustomException("Data Not Store");
			} else {
				return new GlobalResponseEntity("SUCCESS","HSN Code Generated Successfully",200);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getHsnCode(int page, int limit, String sort, String sortName, Optional<String> keyword,
			Optional<String> sortBy, int isDelete) {
		try {
			Pageable pagingSort = null;
			int CountData = (int) hsnRepo.count();
			if (limit == 0) {
				limit = CountData;
			}
			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<HsnCodeEntity> findAll = null;
			if (keyword.isPresent()) {
				findAll = hsnRepo.search(keyword.get(), isDelete, pagingSort);
			} else {
				findAll = hsnRepo.findAllAndDelete(isDelete, pagingSort);
			}
			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}

			Map<String, Object> response = new HashMap<>();
			response.put("data", findAll.getContent());
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());

			if (findAll.getSize() < 1) {
				throw new CustomException("HSN Code Not Found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

}
