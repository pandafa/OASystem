package bean;

import java.util.Date;

/**
 * 提交的流程的审批过程
 * @author DELL
 *
 */
public class ProcedureShen {
	public static final int WORK_NO = 0;
	public static final int WORK_OK = 1;
	public static final int WORK_NEED = 2;
	public static final int WORK_PASS = 3;
	
	private int id;//流程审批ID
	private int procedureId;//流程编号
	private int userGroup;//审批者所在小组
	private int userPart;//审批者所在部门
	private String userJobId;//审批者ID
	private String userName;//审批者姓名
	private int order;//审批顺序
	private int work;//是否已经审批了，需要审批
	private String name;//审批流程的名字
	
	private String content;//审批内容
	private Date time;//审批时间
	private boolean pass;//是否通过
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProcedureId() {
		return procedureId;
	}
	public void setProcedureId(int procedureId) {
		this.procedureId = procedureId;
	}
	public int getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(int userGroup) {
		this.userGroup = userGroup;
	}
	public int getUserPart() {
		return userPart;
	}
	public void setUserPart(int userPart) {
		this.userPart = userPart;
	}
	public String getUserJobId() {
		return userJobId;
	}
	public void setUserJobId(String userJobId) {
		this.userJobId = userJobId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getWork() {
		return work;
	}
	public void setWork(int work) {
		this.work = work;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public boolean isPass() {
		return pass;
	}
	public void setPass(boolean pass) {
		this.pass = pass;
	}
	public void setPass(int pass) {
		if(pass==1){
			this.pass = true;
		}else{
			this.pass = false;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "ProcedureShen [id=" + id + ", procedureId=" + procedureId + ", userGroup=" + userGroup + ", userPart="
				+ userPart + ", userJobId=" + userJobId + ", userName=" + userName + ", order=" + order + ", work="
				+ work + ", name=" + name + ", content=" + content + ", time=" + time + ", pass=" + pass + "]";
	}
	
	
	
	
}
