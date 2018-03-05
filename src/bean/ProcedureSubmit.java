package bean;

import java.util.Arrays;
import java.util.Date;

/**
 * 用户提交的流程
 * @author DELL
 *
 */
public class ProcedureSubmit {
	/**
	 * 通过
	 */
	public static final int STATUS_PASS = 1;
	/**
	 * 未通过
	 */
	public static final int STATUS_NO_PASS = 2;
	/**
	 * 进行中
	 */
	public static final int STATUS_WORKING = 3;
	/**
	 * 错误
	 */
	public static final int STATUS_ERROR = 4;
	
	private int id;
	private String name;
	private String projectName;
	private String projectNameTitle;
	private Date createDate;
	private String createPerson;
	private int status;
	private String partName;
	private String groupName;
	private String showFileName;
	private String fileName;
	private String remark;
	
	private ProcedureShen[] shens;
	private ProcedureOption[] opts;
	
	
	public ProcedureShen[] getShens() {
		return shens;
	}
	public void setShens(ProcedureShen[] shens) {
		this.shens = shens;
	}
	public ProcedureOption[] getOpts() {
		return opts;
	}
	public void setOpts(ProcedureOption[] opts) {
		this.opts = opts;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectNameTitle() {
		return projectNameTitle;
	}
	public void setProjectNameTitle(String projectNameTitle) {
		this.projectNameTitle = projectNameTitle;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getShowFileName() {
		return showFileName;
	}
	public void setShowFileName(String showFileName) {
		this.showFileName = showFileName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "ProcedureSubmit [id=" + id + ", name=" + name + ", projectName=" + projectName + ", projectNameTitle="
				+ projectNameTitle + ", createDate=" + createDate + ", createPerson=" + createPerson + ", status="
				+ status + ", partName=" + partName + ", groupName=" + groupName + ", showFileName=" + showFileName
				+ ", fileName=" + fileName + ", remark=" + remark + ", shens=" + Arrays.toString(shens) + ", opts="
				+ Arrays.toString(opts) + "]";
	}
	
}
