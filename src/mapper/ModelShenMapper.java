package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bean.ModelShen;

public interface ModelShenMapper {
	//添加一个新的流程
	public int addNewShen(ModelShen modelShen);
	//获取一个模板的全部流程
	public List<ModelShen> getShensByProcedureId(@Param("modelId")int modelId);
	//获取一个模板的流程数
	public int getNumberOfOneModel(@Param("modelId")int modelId);
	//删除一个模板的全部流程
	public int delAllByModelId(@Param("modelId")int modelId);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
