package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bean.ProcedureOption;

public interface ProcedureOptionMapper {
	//添加新的填选项
	public int addNewOption(ProcedureOption procedureOption);
	//通过提交号找全部提交的填选项
	public List<ProcedureOption> findBySubmitId(@Param("submitId")int submitId);
}
