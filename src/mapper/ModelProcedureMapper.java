package mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import bean.ModelProcedure;

public interface ModelProcedureMapper {

	//创建新的流程
	public int addNewProcedure(ModelProcedure modelProcedure);
	//获取所有模板的总数
	public int getAllModelNumber();
	//获取模板简略信息，从第几到第几的模板，按时间排序。
	public List<Map<String,Object>> getAllModelFromNumToNum(@Param("begin")int begin,@Param("end")int end);
	
	//获取一个模板的全部
	public ModelProcedure findById(@Param("id")int id);
	
	//更新一个模板
	public int updateModel(ModelProcedure modelProcedure);
	//删除一个模板
	public int delById(@Param("id")int id);
	//获取流程的创建者
	public String getUserOfProcedureWhoCreateById(@Param("id")int id);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
