package bean;

import java.util.Date;

/**
 * 用户信息
 * @author DELL
 *
 */
public class UserInfo {
	
	public static final int KIND_MANAGER_WEB = 0;
	public static final int KIND_MANAGER_PART = 1;
	public static final int KIND_MANAGER_GROUP = 2;
	public static final int KIND_MEMBER = 3;
	
	public static final int STATUS_NO_ACTIVITY = 0;//未激活
	public static final int STATUS_NORMAL = 1;//正常使用
	public static final int STATUS_DISABLE = 2;//禁用
	public static final int STATUS_ABNORMAL = 3;//异常（禁止登陆）
	public static final int STATUS_FROZEN_15_MINUTE = 4;//冻结（15分钟）
	public static final int STATUS_FROZEN_30_MINUTE = 5;//冻结（30分钟）
	public static final int STATUS_FROZEN_24_HOUR = 6;//冻结（24小时）
	
	public static final int SEX_MALE = 0;
	public static final int SEX_FAMALE = 1;
	
	private String jobId;
	private String post;//职务
	private String cardId;
	private String name;
	private int sex;
	private int part;
	private int group;
	private int status;
	private int errorTimes;
	private String tel;
	private String addr;
	private String email;
	private int kind;
	private String password;
	private Date joinTime;
	
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public int getErrorTimes() {
		return errorTimes;
	}
	public void setErrorTimes(int errorTimes) {
		this.errorTimes = errorTimes;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getPart() {
		return part;
	}
	public void setPart(int part) {
		this.part = part;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserInfo [jobId=" + jobId + ", cardId=" + cardId + ", name=" + name + ", sex=" + sex + ", part=" + part
				+ ", group=" + group + ", status=" + status + ", tel=" + tel + ", addr=" + addr + ", email=" + email
				+ ", kind=" + kind + ", password=" + password + ", joinTime=" + joinTime + "]";
	}
	
	
}
