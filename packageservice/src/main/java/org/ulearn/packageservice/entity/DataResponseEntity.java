package org.ulearn.packageservice.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer"})

public class DataResponseEntity implements Serializable{

	private PackageEntity packageData;
	private InstituteEntity instituteData;
	public DataResponseEntity(PackageEntity packageData, InstituteEntity instituteData) {
		super();
		this.packageData = packageData;
		this.instituteData = instituteData;
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
	@Override
	public String toString() {
		return "DataResponseEntity [packageData=" + packageData + ", instituteData=" + instituteData + "]";
	}
}