package org.ulearn.instituteservice.servises;

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
import org.ulearn.instituteservice.controller.InstuteController;
import org.ulearn.instituteservice.entity.InstituteEntity;
import org.ulearn.instituteservice.exception.CustomException;
import org.ulearn.instituteservice.repository.InstituteRepo;

@Service
public class InstituteService {
	@Autowired
	private InstituteRepo instituteRepo;
	private static final Logger LOGGER = LoggerFactory.getLogger(InstuteController.class);
	
	
	public Map<String, Object> getInstuteList(Optional<Integer> page, Optional<String> sortBy, int isDeleted) {
		
		try {
			int Limit = 10;
			Pageable pagingSort = PageRequest.of(page.orElse(0), Limit, Sort.Direction.DESC, sortBy.orElse("instId"));
			Page<InstituteEntity> findAll = instituteRepo.findByAllInst(isDeleted, pagingSort);

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

			if (findAll.getSize() <= 1) {
				throw new CustomException("Institute Not Found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public Map<String, Object> getInstuteListWithPaginagtion(int page, int limit, String sort, String sortName,
			int isDeleted, Optional<String> keyword, Optional<String> sortBy) {

		try {

			Pageable pagingSort = null;

			if (sort == "ASC") {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}
			String keywordVal = keyword.get();
			Page<InstituteEntity> findAll = null;

			if (keyword.get().isEmpty()) {
				findAll = instituteRepo.findByAllInst(isDeleted, pagingSort);

			} else {
				findAll = instituteRepo.Search(keyword.get(), pagingSort);

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

			if (findAll.getSize() <= 1) {
				throw new CustomException("Institute Not Found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

}
