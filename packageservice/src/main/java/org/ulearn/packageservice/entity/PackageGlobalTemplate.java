package org.ulearn.packageservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class PackageGlobalTemplate {

	@Column(name = "INST_ID")
	private Long instId;
	@Column(name = "INST_NAME")
	private String instName;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	@Column(name = "INST_EDATE")
	private Date instEndDate;
	@Column(name = "INST_WEBSITE")
	private String instWebsite;
	@Column(name = "INST_EMAIL")
	private String instEmail;
	@Column(name = "INST_CNUM")
	private String instCnum;
	@Column(name = "INST_MNUM")
	private String instMnum;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	@Column(name = "ISNT_RDATE")
	private Date isntRegDate;
	@Column(name = "INST_LOGO")
	private String instLogo;
	@Column(name = "INST_PAN_NUM")
	private String instPanNum;
	@Column(name = "INST_GST_NUM")
	private String instGstNum;
	@Column(name = "INST_STATUS")
	private String instStatus;
	@Column(name = "IS_ACTIVE")
	private int instIsActive;
	@Column(name = "IS_DELETED")
	private int instIsDeleted;
	@Column(name = "CREATED_ON")
	private Date instCreatedOn;
	@Column(name = "UPDATED_ON")
	private Date instUpdatedOn;
	@Column(name = "PK_ID") private Long pkId;
	@Column(name = "PK_TYPE") String pkType;
	@Column(name = "PK_NAME") @NotEmpty(message = "Package name is required") String pkName;
	@Column(name = "PK_FNAME") @NotEmpty(message = "Package Fname is required") String pkFname;
	@Column(name = "PK_NUSERS")@Positive(message = "Package nusers is required") private Long pkNusers;
	@Column(name = "PK_VALIDITY_TYPE")@NotEmpty(message = "Package validity type is required") private String pkValidityType;
	@Column(name = "PK_VALIDITY_NUM") @Positive(message = "Package validity no required") Long pkValidityNum;
	@Column(name = "PK_CDATE")@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd") Date pkCdate;
	@Column(name = "PK_COMMENT") @NotEmpty(message = "Write some comment on that package") String pkComment;
	@Column(name = "PARENT_ID")private Long parentId;
	@Column(name = "IS_ACTIVE") private Long packIsActive;
	@Column(name = "IS_DELETED") private Long packIsDeleted;
	@Column(name = "PK_STATUS")private String pkStatus;
	@Column(name = "CREATED_ON") private Date packCreatedOn;
	@Column(name = "UPDATED_ON") private Date packUpdatedOn;
	@Column(name="ADM_ID")
	private Long admId;
	@Column(name = "ADM_FNAME")
	private String amdFname;
	@Column(name = "ADM_LNAME")
	private String amdLname;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	@Column(name = "ADM_DOB")
	private Date amdDob;
	@Column(name = "ADM_MNUM")
	private String amdMnum;
	@Column(name = "ADM_EMAIL")
	private String amdEmail;
	@Column(name = "ADM_USERNAME")
	private String amdUsername;
	@Column(name = "ADM_PASSWORD")
	private String amdPassword;
	@Column(name = "ADM_PPIC")
	private String amdPpic;
	@Column(name = "CREATED_ON")
	private Date amdCreatedOn;	
	@Column(name = "UPDATED_ON")
	private Date amdUpdatedOn;
	
	private Long etId;
	private String etName;
	private String etSubject;
	private String etBody;
	private String etType;
	private String etAction;
	public PackageGlobalTemplate(Long instId, String instName, Date instEndDate, String instWebsite, String instEmail,
			String instCnum, String instMnum, Date isntRegDate, String instLogo, String instPanNum, String instGstNum,
			String instStatus, int instIsActive, int instIsDeleted, Date instCreatedOn, Date instUpdatedOn, Long pkId,
			String pkType, @NotEmpty(message = "Package name is required") String pkName,
			@NotEmpty(message = "Package Fname is required") String pkFname,
			@Positive(message = "Package nusers is required") Long pkNusers,
			@NotEmpty(message = "Package validity type is required") String pkValidityType,
			@Positive(message = "Package validity no required") Long pkValidityNum, Date pkCdate,
			@NotEmpty(message = "Write some comment on that package") String pkComment, Long parentId,
			Long packIsActive, Long packIsDeleted, String pkStatus, Date packCreatedOn, Date packUpdatedOn, Long admId,
			String amdFname, String amdLname, Date amdDob, String amdMnum, String amdEmail, String amdUsername,
			String amdPassword, String amdPpic, Date amdCreatedOn, Date amdUpdatedOn, Long etId, String etName,
			String etSubject, String etBody, String etType, String etAction) {
		super();
		this.instId = instId;
		this.instName = instName;
		this.instEndDate = instEndDate;
		this.instWebsite = instWebsite;
		this.instEmail = instEmail;
		this.instCnum = instCnum;
		this.instMnum = instMnum;
		this.isntRegDate = isntRegDate;
		this.instLogo = instLogo;
		this.instPanNum = instPanNum;
		this.instGstNum = instGstNum;
		this.instStatus = instStatus;
		this.instIsActive = instIsActive;
		this.instIsDeleted = instIsDeleted;
		this.instCreatedOn = instCreatedOn;
		this.instUpdatedOn = instUpdatedOn;
		this.pkId = pkId;
		this.pkType = pkType;
		this.pkName = pkName;
		this.pkFname = pkFname;
		this.pkNusers = pkNusers;
		this.pkValidityType = pkValidityType;
		this.pkValidityNum = pkValidityNum;
		this.pkCdate = pkCdate;
		this.pkComment = pkComment;
		this.parentId = parentId;
		this.packIsActive = packIsActive;
		this.packIsDeleted = packIsDeleted;
		this.pkStatus = pkStatus;
		this.packCreatedOn = packCreatedOn;
		this.packUpdatedOn = packUpdatedOn;
		this.admId = admId;
		this.amdFname = amdFname;
		this.amdLname = amdLname;
		this.amdDob = amdDob;
		this.amdMnum = amdMnum;
		this.amdEmail = amdEmail;
		this.amdUsername = amdUsername;
		this.amdPassword = amdPassword;
		this.amdPpic = amdPpic;
		this.amdCreatedOn = amdCreatedOn;
		this.amdUpdatedOn = amdUpdatedOn;
		this.etId = etId;
		this.etName = etName;
		this.etSubject = etSubject;
		this.etBody = etBody;
		this.etType = etType;
		this.etAction = etAction;
	}
	public PackageGlobalTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getInstId() {
		return instId;
	}
	public void setInstId(Long instId) {
		this.instId = instId;
	}
	public String getInstName() {
		return instName;
	}
	public void setInstName(String instName) {
		this.instName = instName;
	}
	public Date getInstEndDate() {
		return instEndDate;
	}
	public void setInstEndDate(Date instEndDate) {
		this.instEndDate = instEndDate;
	}
	public String getInstWebsite() {
		return instWebsite;
	}
	public void setInstWebsite(String instWebsite) {
		this.instWebsite = instWebsite;
	}
	public String getInstEmail() {
		return instEmail;
	}
	public void setInstEmail(String instEmail) {
		this.instEmail = instEmail;
	}
	public String getInstCnum() {
		return instCnum;
	}
	public void setInstCnum(String instCnum) {
		this.instCnum = instCnum;
	}
	public String getInstMnum() {
		return instMnum;
	}
	public void setInstMnum(String instMnum) {
		this.instMnum = instMnum;
	}
	public Date getIsntRegDate() {
		return isntRegDate;
	}
	public void setIsntRegDate(Date isntRegDate) {
		this.isntRegDate = isntRegDate;
	}
	public String getInstLogo() {
		return instLogo;
	}
	public void setInstLogo(String instLogo) {
		this.instLogo = instLogo;
	}
	public String getInstPanNum() {
		return instPanNum;
	}
	public void setInstPanNum(String instPanNum) {
		this.instPanNum = instPanNum;
	}
	public String getInstGstNum() {
		return instGstNum;
	}
	public void setInstGstNum(String instGstNum) {
		this.instGstNum = instGstNum;
	}
	public String getInstStatus() {
		return instStatus;
	}
	public void setInstStatus(String instStatus) {
		this.instStatus = instStatus;
	}
	public int getInstIsActive() {
		return instIsActive;
	}
	public void setInstIsActive(int instIsActive) {
		this.instIsActive = instIsActive;
	}
	public int getInstIsDeleted() {
		return instIsDeleted;
	}
	public void setInstIsDeleted(int instIsDeleted) {
		this.instIsDeleted = instIsDeleted;
	}
	public Date getInstCreatedOn() {
		return instCreatedOn;
	}
	public void setInstCreatedOn(Date instCreatedOn) {
		this.instCreatedOn = instCreatedOn;
	}
	public Date getInstUpdatedOn() {
		return instUpdatedOn;
	}
	public void setInstUpdatedOn(Date instUpdatedOn) {
		this.instUpdatedOn = instUpdatedOn;
	}
	public Long getPkId() {
		return pkId;
	}
	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}
	public String getPkType() {
		return pkType;
	}
	public void setPkType(String pkType) {
		this.pkType = pkType;
	}
	public String getPkName() {
		return pkName;
	}
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
	public String getPkFname() {
		return pkFname;
	}
	public void setPkFname(String pkFname) {
		this.pkFname = pkFname;
	}
	public Long getPkNusers() {
		return pkNusers;
	}
	public void setPkNusers(Long pkNusers) {
		this.pkNusers = pkNusers;
	}
	public String getPkValidityType() {
		return pkValidityType;
	}
	public void setPkValidityType(String pkValidityType) {
		this.pkValidityType = pkValidityType;
	}
	public Long getPkValidityNum() {
		return pkValidityNum;
	}
	public void setPkValidityNum(Long pkValidityNum) {
		this.pkValidityNum = pkValidityNum;
	}
	public Date getPkCdate() {
		return pkCdate;
	}
	public void setPkCdate(Date pkCdate) {
		this.pkCdate = pkCdate;
	}
	public String getPkComment() {
		return pkComment;
	}
	public void setPkComment(String pkComment) {
		this.pkComment = pkComment;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getPackIsActive() {
		return packIsActive;
	}
	public void setPackIsActive(Long packIsActive) {
		this.packIsActive = packIsActive;
	}
	public Long getPackIsDeleted() {
		return packIsDeleted;
	}
	public void setPackIsDeleted(Long packIsDeleted) {
		this.packIsDeleted = packIsDeleted;
	}
	public String getPkStatus() {
		return pkStatus;
	}
	public void setPkStatus(String pkStatus) {
		this.pkStatus = pkStatus;
	}
	public Date getPackCreatedOn() {
		return packCreatedOn;
	}
	public void setPackCreatedOn(Date packCreatedOn) {
		this.packCreatedOn = packCreatedOn;
	}
	public Date getPackUpdatedOn() {
		return packUpdatedOn;
	}
	public void setPackUpdatedOn(Date packUpdatedOn) {
		this.packUpdatedOn = packUpdatedOn;
	}
	public Long getAdmId() {
		return admId;
	}
	public void setAdmId(Long admId) {
		this.admId = admId;
	}
	public String getAmdFname() {
		return amdFname;
	}
	public void setAmdFname(String amdFname) {
		this.amdFname = amdFname;
	}
	public String getAmdLname() {
		return amdLname;
	}
	public void setAmdLname(String amdLname) {
		this.amdLname = amdLname;
	}
	public Date getAmdDob() {
		return amdDob;
	}
	public void setAmdDob(Date amdDob) {
		this.amdDob = amdDob;
	}
	public String getAmdMnum() {
		return amdMnum;
	}
	public void setAmdMnum(String amdMnum) {
		this.amdMnum = amdMnum;
	}
	public String getAmdEmail() {
		return amdEmail;
	}
	public void setAmdEmail(String amdEmail) {
		this.amdEmail = amdEmail;
	}
	public String getAmdUsername() {
		return amdUsername;
	}
	public void setAmdUsername(String amdUsername) {
		this.amdUsername = amdUsername;
	}
	public String getAmdPassword() {
		return amdPassword;
	}
	public void setAmdPassword(String amdPassword) {
		this.amdPassword = amdPassword;
	}
	public String getAmdPpic() {
		return amdPpic;
	}
	public void setAmdPpic(String amdPpic) {
		this.amdPpic = amdPpic;
	}
	public Date getAmdCreatedOn() {
		return amdCreatedOn;
	}
	public void setAmdCreatedOn(Date amdCreatedOn) {
		this.amdCreatedOn = amdCreatedOn;
	}
	public Date getAmdUpdatedOn() {
		return amdUpdatedOn;
	}
	public void setAmdUpdatedOn(Date amdUpdatedOn) {
		this.amdUpdatedOn = amdUpdatedOn;
	}
	public Long getEtId() {
		return etId;
	}
	public void setEtId(Long etId) {
		this.etId = etId;
	}
	public String getEtName() {
		return etName;
	}
	public void setEtName(String etName) {
		this.etName = etName;
	}
	public String getEtSubject() {
		return etSubject;
	}
	public void setEtSubject(String etSubject) {
		this.etSubject = etSubject;
	}
	public String getEtBody() {
		return etBody;
	}
	public void setEtBody(String etBody) {
		this.etBody = etBody;
	}
	public String getEtType() {
		return etType;
	}
	public void setEtType(String etType) {
		this.etType = etType;
	}
	public String getEtAction() {
		return etAction;
	}
	public void setEtAction(String etAction) {
		this.etAction = etAction;
	}
	@Override
	public String toString() {
		return "PackageGlobalTemplate [instId=" + instId + ", instName=" + instName + ", instEndDate=" + instEndDate
				+ ", instWebsite=" + instWebsite + ", instEmail=" + instEmail + ", instCnum=" + instCnum + ", instMnum="
				+ instMnum + ", isntRegDate=" + isntRegDate + ", instLogo=" + instLogo + ", instPanNum=" + instPanNum
				+ ", instGstNum=" + instGstNum + ", instStatus=" + instStatus + ", instIsActive=" + instIsActive
				+ ", instIsDeleted=" + instIsDeleted + ", instCreatedOn=" + instCreatedOn + ", instUpdatedOn="
				+ instUpdatedOn + ", pkId=" + pkId + ", pkType=" + pkType + ", pkName=" + pkName + ", pkFname="
				+ pkFname + ", pkNusers=" + pkNusers + ", pkValidityType=" + pkValidityType + ", pkValidityNum="
				+ pkValidityNum + ", pkCdate=" + pkCdate + ", pkComment=" + pkComment + ", parentId=" + parentId
				+ ", packIsActive=" + packIsActive + ", packIsDeleted=" + packIsDeleted + ", pkStatus=" + pkStatus
				+ ", packCreatedOn=" + packCreatedOn + ", packUpdatedOn=" + packUpdatedOn + ", admId=" + admId
				+ ", amdFname=" + amdFname + ", amdLname=" + amdLname + ", amdDob=" + amdDob + ", amdMnum=" + amdMnum
				+ ", amdEmail=" + amdEmail + ", amdUsername=" + amdUsername + ", amdPassword=" + amdPassword
				+ ", amdPpic=" + amdPpic + ", amdCreatedOn=" + amdCreatedOn + ", amdUpdatedOn=" + amdUpdatedOn
				+ ", etId=" + etId + ", etName=" + etName + ", etSubject=" + etSubject + ", etBody=" + etBody
				+ ", etType=" + etType + ", etAction=" + etAction + "]";
	}
	
	
}