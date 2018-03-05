package bean;

import java.util.Date;

/**
 * нд╪Ч╡ж©Б
 * @author DELL
 *
 */
public class FileDepot {
	public static final int SOURCE_COMPANY = 1;
	public static final int SOURCE_PART = 0;
	public static final int SOURCE_GROUP = 2;
	private int id;
	private String showFileName;
	private String realFileName;
	private Date updateDate;
	private long size;
	private int source;
	private int part;
	private int group;
	private String createPerson;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getShowFileName() {
		return showFileName;
	}
	public void setShowFileName(String showFileName) {
		this.showFileName = showFileName;
	}
	public String getRealFileName() {
		return realFileName;
	}
	public void setRealFileName(String realFileName) {
		this.realFileName = realFileName;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
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
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	@Override
	public String toString() {
		return "FileDepot [id=" + id + ", showFileName=" + showFileName + ", realFileName=" + realFileName
				+ ", updateDate=" + updateDate + ", size=" + size + ", source=" + source + ", part=" + part + ", group="
				+ group + ", createPerson=" + createPerson + "]";
	}

}
