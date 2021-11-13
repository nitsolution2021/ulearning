package org.ulearn.instituteservice.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ulearn.instituteservice.entity.GlobalResponse;
import org.ulearn.instituteservice.entity.InstituteAddressEntity;
import org.ulearn.instituteservice.entity.InstituteAdminEntity;
import org.ulearn.instituteservice.entity.InstituteEntity;
import org.ulearn.instituteservice.entity.InstituteGlobalEntity;
import org.ulearn.instituteservice.exception.CustomException;
import org.ulearn.instituteservice.repository.InstituteAddressRepo;
import org.ulearn.instituteservice.repository.InstituteAdminRepo;
import org.ulearn.instituteservice.repository.InstituteRepo;
import org.ulearn.instituteservice.validation.FieldValidation;

@RestController
@RequestMapping("/institute")
public class InstuteController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InstuteController.class);

	@Autowired
	private InstituteRepo instituteRepo;
	
	@Autowired
	private InstituteAddressRepo instituteAddressRepo; 
	
	@Autowired
	private InstituteAdminRepo instituteAdminRepo; 

	@Autowired
	private FieldValidation fieldValidation;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/list")
	public List<InstituteGlobalEntity> getInstute() {
		LOGGER.info("Inside - InstituteController.getInstute()");

		try {

			List<InstituteGlobalEntity> findAll = instituteRepo.findByAllInstQuery();

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
	public GlobalResponse postInstituteDetails(@RequestBody InstituteGlobalEntity instituteGlobalEntrity) {
		LOGGER.info("Inside - InstituteController.postInstituteDetails()");

		try {
			Optional<InstituteEntity> findByInstituteName = instituteRepo.findByInstName(instituteGlobalEntrity.getInstName());
			Optional<InstituteEntity> findByInstEmail = instituteRepo.findByInstEmail(instituteGlobalEntrity.getInstEmail());

			if ((fieldValidation.isEmpty(instituteGlobalEntrity.getInstCnum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstName()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstEmail()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstEndDate()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstGstNum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstLogo()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstMnum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstName()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstStatus()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstWebsite()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstPanNum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrCountry()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrDistrict()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrLine1()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrPincode()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrState()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrTaluka()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrType()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrOrder()))
//					& (fieldValidation.isEmpty(instituteGlobalEntrity.getIsPrimary()))					
					) {
				if (!findByInstituteName.isPresent()) {
					if (!findByInstEmail.isPresent()) {

						InstituteEntity filterInsDetails = new InstituteEntity();

						filterInsDetails.setInstName(instituteGlobalEntrity.getInstName());
						filterInsDetails.setInstEndDate(instituteGlobalEntrity.getInstEndDate());
						filterInsDetails.setInstWebsite(instituteGlobalEntrity.getInstWebsite());
						filterInsDetails.setInstEmail(instituteGlobalEntrity.getInstEmail());
						filterInsDetails.setInstCnum(instituteGlobalEntrity.getInstCnum());
						filterInsDetails.setInstMnum(instituteGlobalEntrity.getInstMnum());
						filterInsDetails.setIsntRegDate(new Date());
						filterInsDetails.setInstLogo(instituteGlobalEntrity.getInstLogo());
						filterInsDetails.setInstPanNum(instituteGlobalEntrity.getInstPanNum());
						filterInsDetails.setInstGstNum(instituteGlobalEntrity.getInstGstNum());
						filterInsDetails.setInstStatus(instituteGlobalEntrity.getInstStatus());
						filterInsDetails.setIsActive(0);
						filterInsDetails.setIsDeleted(0);
						filterInsDetails.setCreatedOn(new Date());
						filterInsDetails.setUpdatedOn(new Date());

						InstituteEntity save = instituteRepo.save(filterInsDetails);
												
						
						InstituteAddressEntity filterInsAdrDetails = new InstituteAddressEntity();

						filterInsAdrDetails.setAdrCity(instituteGlobalEntrity.getAdrCity());
						filterInsAdrDetails.setAdrCountry(instituteGlobalEntrity.getAdrCountry());
						filterInsAdrDetails.setAdrDistrict(instituteGlobalEntrity.getAdrDistrict());
						filterInsAdrDetails.setAdrLine1(instituteGlobalEntrity.getAdrLine1());
						filterInsAdrDetails.setAdrLine2(instituteGlobalEntrity.getAdrLine2());
						filterInsAdrDetails.setAdrOrder(instituteGlobalEntrity.getAdrOrder());
						filterInsAdrDetails.setAdrPincode(instituteGlobalEntrity.getAdrPincode());
						filterInsAdrDetails.setAdrState(instituteGlobalEntrity.getAdrState());
						filterInsAdrDetails.setAdrTaluka(instituteGlobalEntrity.getAdrTaluka());
						filterInsAdrDetails.setAdrType(instituteGlobalEntrity.getAdrType());
						filterInsAdrDetails.setInstId(save.getInstId());
						filterInsAdrDetails.setIsPrimary(instituteGlobalEntrity.getIsPrimary());						
						filterInsAdrDetails.setAdrStatus(instituteGlobalEntrity.getAdrStatus());
						filterInsAdrDetails.setIsActive(0);
						filterInsAdrDetails.setIsDeleted(0);
						filterInsAdrDetails.setCreatedOn(new Date());
						filterInsAdrDetails.setUpdatedOn(new Date());

						InstituteAddressEntity InsAdrDetails = instituteAddressRepo.save(filterInsAdrDetails);
						
						
						InstituteAdminEntity filterInsAmdDetails = new InstituteAdminEntity();

						
						filterInsAmdDetails.setAmdFname(instituteGlobalEntrity.getAmdFname());
						filterInsAmdDetails.setAmdLname(instituteGlobalEntrity.getAmdLname());
						filterInsAmdDetails.setAmdDob(instituteGlobalEntrity.getAmdDob());
						filterInsAmdDetails.setAmdMnum(instituteGlobalEntrity.getAmdMnum());
						filterInsAmdDetails.setAmdEmail(instituteGlobalEntrity.getAmdEmail());
						filterInsAmdDetails.setAmdUsername(instituteGlobalEntrity.getAmdUsername());
//						filterInsAmdDetails.setAmdPassword(instituteGlobalEntrity.getAmdPassword());
						filterInsAmdDetails.setAmdPassword(passwordEncoder.encode(instituteGlobalEntrity.getAmdPassword()));
						filterInsAmdDetails.setAmdPpic(instituteGlobalEntrity.getAmdPpic());
						filterInsAmdDetails.setInstId(save.getInstId());
						filterInsAmdDetails.setCreatedOn(new Date());
						filterInsAmdDetails.setUpdatedOn(new Date());

						InstituteAdminEntity InsAmdDetails = instituteAdminRepo.save(filterInsAmdDetails);
						
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
	public Optional<InstituteEntity> viewInstituteDetails(@PathVariable() long instId) {
		LOGGER.info("Inside - InstituteController.viewInstituteDetails()");
		try {
			Optional<InstituteEntity> findById = this.instituteRepo.findById(instId);
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
	public GlobalResponse putInstituteDetails(@RequestBody InstituteGlobalEntity instituteGlobalEntrity,
			@PathVariable("instId") long instId) {
		LOGGER.info("Inside - InstituteController.putInstituteDetails()"+instituteGlobalEntrity);
		try {
			if ((fieldValidation.isEmpty(instituteGlobalEntrity.getInstCnum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstName()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstEmail()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstEndDate()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstGstNum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstLogo()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstMnum()))					
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstStatus()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstWebsite()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getInstPanNum()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrCountry()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrDistrict()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrLine1()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrPincode()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrState()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrTaluka()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrType()))
					& (fieldValidation.isEmpty(instituteGlobalEntrity.getAdrOrder()))
//					& (fieldValidation.isEmpty(instituteGlobalEntrity.getIsPrimary()))					
					) {
				Optional<InstituteEntity> findById = instituteRepo.findById(instId);
				Optional<InstituteEntity> findByInstName = instituteRepo.findByInstUnqName(instId,
						instituteGlobalEntrity.getInstName());
//				LOGGER.info("Inside - InstituteController.putInstituteDetails()///"+findByInstName);
				Optional<InstituteEntity> findByInstEmail = instituteRepo.findByInstUnqEmail(instId,
						instituteGlobalEntrity.getInstEmail());
				
				if (findById.isPresent()) {

					if (!findByInstName.isPresent()) {
						if (!findByInstEmail.isPresent()) {

							InstituteEntity InstEntrity = new InstituteEntity();
							
							InstEntrity.setInstCnum(instituteGlobalEntrity.getInstCnum());
							InstEntrity.setInstEmail(instituteGlobalEntrity.getInstEmail());
							InstEntrity.setInstEndDate(instituteGlobalEntrity.getInstEndDate());
							InstEntrity.setInstGstNum(instituteGlobalEntrity.getInstGstNum());
							InstEntrity.setInstId(instituteGlobalEntrity.getInstId());
							InstEntrity.setInstLogo(instituteGlobalEntrity.getInstLogo());
							InstEntrity.setInstMnum(instituteGlobalEntrity.getInstMnum());
							InstEntrity.setInstName(instituteGlobalEntrity.getInstName());
							InstEntrity.setInstPanNum(instituteGlobalEntrity.getInstPanNum());
							InstEntrity.setInstStatus(instituteGlobalEntrity.getInstStatus());
							InstEntrity.setInstWebsite(instituteGlobalEntrity.getInstWebsite());
							InstEntrity.setIsActive(instituteGlobalEntrity.getIsActive());
							InstEntrity.setIsntRegDate(findById.get().getIsntRegDate());
							InstEntrity.setUpdatedOn(new Date());
							InstituteEntity save = instituteRepo.save(InstEntrity);
							
							InstituteAddressEntity filterInsAdrDetails = new InstituteAddressEntity();

							filterInsAdrDetails.setInstId(instituteGlobalEntrity.getInstId());
							filterInsAdrDetails.setAdrCity(instituteGlobalEntrity.getAdrCity());
							filterInsAdrDetails.setAdrCountry(instituteGlobalEntrity.getAdrCountry());
							filterInsAdrDetails.setAdrDistrict(instituteGlobalEntrity.getAdrDistrict());
							filterInsAdrDetails.setAdrLine1(instituteGlobalEntrity.getAdrLine1());
							filterInsAdrDetails.setAdrLine2(instituteGlobalEntrity.getAdrLine2());
							filterInsAdrDetails.setAdrOrder(instituteGlobalEntrity.getAdrOrder());
							filterInsAdrDetails.setAdrPincode(instituteGlobalEntrity.getAdrPincode());
							filterInsAdrDetails.setAdrState(instituteGlobalEntrity.getAdrState());
							filterInsAdrDetails.setAdrTaluka(instituteGlobalEntrity.getAdrTaluka());
							filterInsAdrDetails.setAdrType(instituteGlobalEntrity.getAdrType());
							filterInsAdrDetails.setInstId(save.getInstId());
							filterInsAdrDetails.setIsPrimary(instituteGlobalEntrity.getIsPrimary());						
							filterInsAdrDetails.setAdrStatus(instituteGlobalEntrity.getAdrStatus());
							filterInsAdrDetails.setIsActive(0);
							filterInsAdrDetails.setIsDeleted(0);
							filterInsAdrDetails.setCreatedOn(new Date());
							filterInsAdrDetails.setUpdatedOn(new Date());

							InstituteAddressEntity InsAdrDetails = instituteAddressRepo.save(filterInsAdrDetails);
							
							InstituteAdminEntity filterInsAmdDetails = new InstituteAdminEntity();

							filterInsAmdDetails.setAmdId(instituteGlobalEntrity.getAmdId());
							filterInsAmdDetails.setAmdFname(instituteGlobalEntrity.getAmdFname());
							filterInsAmdDetails.setAmdLname(instituteGlobalEntrity.getAmdLname());
							filterInsAmdDetails.setAmdDob(instituteGlobalEntrity.getAmdDob());
							filterInsAmdDetails.setAmdMnum(instituteGlobalEntrity.getAmdMnum());
							filterInsAmdDetails.setAmdEmail(instituteGlobalEntrity.getAmdEmail());
							filterInsAmdDetails.setAmdUsername(instituteGlobalEntrity.getAmdUsername());
							filterInsAmdDetails.setAmdPassword(instituteGlobalEntrity.getAmdPassword());
//							filterInsAmdDetails.setAmdPassword(passwordEncoder.encode(instituteGlobalEntrity.getAmdPassword()));
							filterInsAmdDetails.setAmdPpic(instituteGlobalEntrity.getAmdPpic());
							filterInsAmdDetails.setInstId(save.getInstId());
//							filterInsAmdDetails.setCreatedOn(new Date());
							filterInsAmdDetails.setUpdatedOn(new Date());

							InstituteAdminEntity InsAmdDetails = instituteAdminRepo.save(filterInsAmdDetails);

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
