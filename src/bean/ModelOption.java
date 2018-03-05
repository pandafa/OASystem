package bean;

/**
 * 模板的选项
 * @author DELL
 *
 */
public class ModelOption {
	private int id;
	private int modelId;
	private String name;
	private int order;
	private int must;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getMust() {
		return must;
	}
	public void setMust(int must) {
		this.must = must;
	}
	@Override
	public String toString() {
		return "ModelOption [id=" + id + ", modelId=" + modelId + ", name=" + name + ", order=" + order + ", must="
				+ must + "]";
	}
	
}
