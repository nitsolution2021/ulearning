package org.ulearn.instituteservice.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ulearn.instituteservice.entity.GlobalResponse;
import org.ulearn.instituteservice.entity.InstituteEntrity;
import org.ulearn.instituteservice.exception.CustomException;
import org.ulearn.instituteservice.repository.InstituteRepo;
import org.ulearn.instituteservice.validation.FieldValidation;

@RestController
@RequestMapping("/institute")
public class InstuteController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InstuteController.class);

	@Autowired
	private InstituteRepo instituteRepo;

	@Autowired
	private FieldValidation fieldValidation;

	@GetMapping("/list")
	public List<InstituteEntrity> getInstute() {
		LOGGER.info("Inside - InstituteController.getInstute()");

		try {
			List<InstituteEntrity> findAll = instituteRepo.findAll();
			if (findAll.size() < 1) {
				throw new CustomException("Institute Not Found!");
			} else {
				return findAll;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/add")
	public GlobalResponse postInstituteDetails(@RequestBody InstituteEntrity instituteEntrity) {
		LOGGER.info("Inside - InstituteController.postInstituteDetails()");

		try {
			Optional<InstituteEntrity> findByInstituteName = instituteRepo
					.findByInstName(instituteEntrity.getInstName());

			Optional<InstituteEntrity> findByInstEmail = instituteRepo.findByInstEmail(instituteEntrity.getInstEmail());

			if ((fieldValidation.isEmpty(instituteEntrity.getInstCnum()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstName()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstEmail()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstEndDate()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstGstNum()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstLogo()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstMnum()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstName()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstStatus()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstWebsite()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstPanNum()))) {
				if (!findByInstituteName.isPresent()) {
					if (!findByInstEmail.isPresent()) {

						InstituteEntrity filterInsDetails = new InstituteEntrity();

						filterInsDetails.setInstName(instituteEntrity.getInstName());
						filterInsDetails.setInstEndDate(instituteEntrity.getInstEndDate());
						filterInsDetails.setInstWebsite(instituteEntrity.getInstWebsite());
						filterInsDetails.setInstEmail(instituteEntrity.getInstEmail());
						filterInsDetails.setInstCnum(instituteEntrity.getInstCnum());
						filterInsDetails.setInstMnum(instituteEntrity.getInstMnum());
						filterInsDetails.setIsntRegDate(new Date());
						filterInsDetails.setInstLogo(instituteEntrity.getInstLogo());
						filterInsDetails.setInstPanNum(instituteEntrity.getInstPanNum());
						filterInsDetails.setInstGstNum(instituteEntrity.getInstGstNum());
						filterInsDetails.setInstStatus(instituteEntrity.getInstStatus());
						filterInsDetails.setIsActive(0);
						filterInsDetails.setIsDeleted(0);
						filterInsDetails.setCreatedOn(new Date());
						filterInsDetails.setUpdatedOn(new Date());

						InstituteEntrity save = instituteRepo.save(filterInsDetails);
						return new GlobalResponse("success", "Institute Added Successfully");
					} else {
						throw new CustomException("Institute Email Already Exist!");
					}
				} else {
					throw new CustomException("Institute Name Already Exist!");
				}
			} else {
				throw new CustomException("Validation Error!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/view/{instId}")
	public Optional<InstituteEntrity> viewInstituteDetails(@PathVariable() long instId) {
		LOGGER.info("Inside - InstituteController.viewInstituteDetails()");
		try {
			Optional<InstituteEntrity> findById = this.instituteRepo.findById(instId);
			if (!(findById.isPresent())) {
				throw new CustomException("Institute Not Found!");
			} else {
				return findById;
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/update/{instId}")
	public GlobalResponse putInstituteDetails(@RequestBody InstituteEntrity instituteEntrity,
			@PathVariable() long instId) {
		LOGGER.info("Inside - InstituteController.putInstituteDetails()");
		try {
			if ((fieldValidation.isEmpty(instituteEntrity.getInstCnum()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstEmail()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstGstNum()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstLogo()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstMnum()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstName()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstStatus()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstWebsite()))
					& (fieldValidation.isEmpty(instituteEntrity.getInstPanNum()))) {

				Optional<InstituteEntrity> findById = instituteRepo.findById(instId);
				Optional<InstituteEntrity> findByInstName = instituteRepo.findByInstUnqName(instId,
						instituteEntrity.getInstName());
				Optional<InstituteEntrity> findByInstEmail = instituteRepo.findByInstUnqEmail(instId,
						instituteEntrity.getInstEmail());

				if (findById.isPresent()) {

					if (!findByInstName.isPresent()) {
						if (!findByInstEmail.isPresent()) {

							InstituteEntrity InstEntrity = new InstituteEntrity();

							InstEntrity.setInstCnum(instituteEntrity.getInstCnum());
							InstEntrity.setInstEmail(instituteEntrity.getInstEmail());
							InstEntrity.setInstEndDate(instituteEntrity.getInstEndDate());
							InstEntrity.setInstGstNum(instituteEntrity.getInstGstNum());
							InstEntrity.setInstId(instituteEntrity.getInstId());
							InstEntrity.setInstLogo(instituteEntrity.getInstLogo());
							InstEntrity.setInstMnum(instituteEntrity.getInstMnum());
							InstEntrity.setInstName(instituteEntrity.getInstName());
							InstEntrity.setInstPanNum(instituteEntrity.getInstPanNum());
							InstEntrity.setInstStatus(instituteEntrity.getInstStatus());
							InstEntrity.setInstWebsite(instituteEntrity.getInstWebsite());
							InstEntrity.setIsActive(instituteEntrity.getIsActive());
							InstEntrity.setIsntRegDate(findById.get().getIsntRegDate());
							InstEntrity.setUpdatedOn(new Date());
							InstituteEntrity save = instituteRepo.save(InstEntrity);
							if (save.equals(null)) {
								throw new CustomException("Institute Email Already Exist!");
							}
							return new GlobalResponse("success", "Institute Updated Successfully");

						} else {
							throw new CustomException("Institute Email Already Exist!");
						}
					} else {
						throw new CustomException("Institute Name Already Exist!");
					}
				} else {
					throw new CustomException("Institute Not Exist!");
				}
			} else {
				throw new CustomException("Validation Error!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
