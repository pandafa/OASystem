package bean;

import java.util.Date;

/**
 * 创建的模板
 * @author DELL
 *
 */
public class ModelProcedure {
	private int id;
	private String name;
	private String introduction;
	private String illustrate;
	private String remark;
	private String title;
	private String projectName;
	private int enclosure;
	private Date createDate;
	private String createPerson;
	
	private ModelShen[] shen;
	private ModelOption[] option;
	
	
	public ModelShen[] getShen() {
		return shen;
	}
	public void setShen(ModelShen[] shen) {
		this.shen = shen;
	}
	public ModelOption[] getOption() {
		return option;
	}
	public void setOption(ModelOption[] option) {
		this.option = option;
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
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getIllustrate() {
		return illustrate;
	}
	public void setIllustrate(String illustrate) {
		this.illustrate = illustrate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getEnclosure() {
		return enclosure;
	}
	public void setEnclosure(int enclosure) {
		this.enclosure = enclosure;
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
	@Override
	public String toString() {
		return "ModelProcedure [id=" + id + ", name=" + name + ", introduction=" + introduction + ", illustrate="
				+ illustrate + ", remark=" + remark + ", title=" + title + ", projectName=" + projectName
				+ ", enclosure=" + enclosure + ", createDate=" + createDate + ", createPerson=" + createPerson + "]";
	}
	
}
