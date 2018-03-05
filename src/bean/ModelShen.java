package bean;

/**
 * 模板的审批流程
 * @author DELL
 *
 */
public class ModelShen {
	private int id;
	private int modelId;
	private int order;
	private String name;
	private int part;
	private int group;
	private String person;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	@Override
	public String toString() {
		return "ModelShen [id=" + id + ", modelId=" + modelId + ", order=" + order + ", name=" + name + ", part=" + part
				+ ", group=" + group + ", person=" + person + "]";
	}
	
}
