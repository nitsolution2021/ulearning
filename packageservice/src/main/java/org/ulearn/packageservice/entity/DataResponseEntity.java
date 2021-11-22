package org.ulearn.packageservice.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity

public class DataResponseEntity implements Serializable{

	@Id
	private PackageEntity packageEntity;
	private InstituteAdminEntity adminEntity;
	private InstituteAddressEntity instituteAddressEntity;
	private LicenseEntity licenseEntity;
	private InstituteEntity instituteEntity;
	public DataResponseEntity(long instId, PackageEntity packageEntity, InstituteAdminEntity adminEntity,
			InstituteAddressEntity instituteAddressEntity, LicenseEntity licenseEntity,
			InstituteEntity instituteEntity) {
		super();
		this.packageEntity = packageEntity;
		this.adminEntity = adminEntity;
		this.instituteAddressEntity = instituteAddressEntity;
		this.licenseEntity = licenseEntity;
		this.instituteEntity = instituteEntity;
	}
	public DataResponseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PackageEntity getPackageEntity() {
		return packageEntity;
	}
	public void setPackageEntity(PackageEntity packageEntity) {
		this.packageEntity = packageEntity;
	}
	public InstituteAdminEntity getAdminEntity() {
		return adminEntity;
	}
	public void setAdminEntity(InstituteAdminEntity adminEntity) {
		this.adminEntity = adminEntity;
	}
	public InstituteAddressEntity getInstituteAddressEntity() {
		return instituteAddressEntity;
	}
	public void setInstituteAddressEntity(InstituteAddressEntity instituteAddressEntity) {
		this.instituteAddressEntity = instituteAddressEntity;
	}
	public LicenseEntity getLicenseEntity() {
		return licenseEntity;
	}
	public void setLicenseEntity(LicenseEntity licenseEntity) {
		this.licenseEntity = licenseEntity;
	}
	public InstituteEntity getInstituteEntity() {
		return instituteEntity;
	}
	public void setInstituteEntity(InstituteEntity instituteEntity) {
		this.instituteEntity = instituteEntity;
	}
	@Override
	public String toString() {
		return "DataResponseEntity [ packageEntity=" + packageEntity + ", adminEntity="
				+ adminEntity + ", instituteAddressEntity=" + instituteAddressEntity + ", licenseEntity="
				+ licenseEntity + ", instituteEntity=" + instituteEntity + "]";
	}
	
}
