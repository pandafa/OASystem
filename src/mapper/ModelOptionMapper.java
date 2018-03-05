package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bean.ModelOption;

public interface ModelOptionMapper {
	
	//添加一个新的填选项
	public int addNewOption(ModelOption opt);
	//获取一个模板的全部填选项
	public List<ModelOption> getOptionsByProcedureId(@Param("modelId")int modelId);
	//获取一个模板的填选项数
	public int getNumberOfOneModel(@Param("modelId")int modelId);
	//删除一个模板的全部填选项
	public int delAllOptionsByModelId(@Param("modelId")int modelId);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
