package bean;

import java.util.Date;

/**
 * ÏûÏ¢
 * @author DELL
 *
 */
public class Message {
	
	public static final int KIND_MESSAGE_SYSTEM = 1;
	public static final int KIND_MESSAGE_COMPANY= 2;
	public static final int KIND_MESSAGE_PART   = 3;
	public static final int KIND_MESSAGE_GROUP  = 6;
	public static final int KIND_MESSAGE_PERSON = 8;
	public static final int KIND_NOTICE_COMPANY = 2;
	public static final int KIND_NOTICE_PART    = 4;
	public static final int KIND_NOTICE_GROUP   = 7;
	
	private int id;
	private String title;
	private int kind;
	private String sendPerson;
	private Date sendDate;
	private String content;
	
	private String acceptPerson;
	private int acceptPart;
	private int acceptGroup;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public String getSendPerson() {
		return sendPerson;
	}
	public void setSendPerson(String sendPerson) {
		this.sendPerson = sendPerson;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAcceptPerson() {
		return acceptPerson;
	}
	public void setAcceptPerson(String acceptPerson) {
		this.acceptPerson = acceptPerson;
	}
	public int getAcceptPart() {
		return acceptPart;
	}
	public void setAcceptPart(int acceptPart) {
		this.acceptPart = acceptPart;
	}
	public int getAcceptGroup() {
		return acceptGroup;
	}
	public void setAcceptGroup(int acceptGroup) {
		this.acceptGroup = acceptGroup;
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", title=" + title + ", kind=" + kind + ", sendPerson=" + sendPerson
				+ ", sendDate=" + sendDate + ", content=" + content + ", acceptPerson=" + acceptPerson + ", acceptPart="
				+ acceptPart + ", acceptGroup=" + acceptGroup + "]";
	}
	
}
