package org.ulearn.packageservice.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer"})

public class DataResponseEntity implements Serializable{

	private PackageEntity packageData;
	private InstituteEntity instituteData;
	private PackageLogEntity packageLogEntity;
	public DataResponseEntity(PackageEntity packageData, InstituteEntity instituteData,
			PackageLogEntity packageLogEntity) {
		super();
		this.packageData = packageData;
		this.instituteData = instituteData;
		this.packageLogEntity = packageLogEntity;
	}
	public DataResponseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PackageEntity getPackageData() {
		return packageData;
	}
	public void setPackageData(PackageEntity packageData) {
		this.packageData = packageData;
	}
	public InstituteEntity getInstituteData() {
		return instituteData;
	}
	public void setInstituteData(InstituteEntity instituteData) {
		this.instituteData = instituteData;
	}
	public PackageLogEntity getPackageLogEntity() {
		return packageLogEntity;
	}
	public void setPackageLogEntity(PackageLogEntity packageLogEntity) {
		this.packageLogEntity = packageLogEntity;
	}
	@Override
	public String toString() {
		return "DataResponseEntity [packageData=" + packageData + ", instituteData=" + instituteData
				+ ", packageLogEntity=" + packageLogEntity + "]";
	}
	
}