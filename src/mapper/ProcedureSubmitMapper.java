package mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import bean.ProcedureSubmit;

public interface ProcedureSubmitMapper {
	//添加新的提交
	public int addNewSubmit(ProcedureSubmit procedureSubmit);
	//获取某人提交的流程的总数
	public int getNumberOfSubmitByCreatePerson(@Param("createPerson")String jobId);
	//获取从第几到第几的我提交的流程，简略版
	public List<Map<String, Object>> getAllMyProcedureSimpleFromNumToNum(@Param("createPerson")String jobId, @Param("begin")int begin, @Param("end")int end);
	
	//获取某个提交的全部
	public ProcedureSubmit findById(@Param("id")int id);
	//获取提交的主要信息
	public Map<String, Object> getMainInfoById(@Param("id")int id);
	//获取流程的提交人
	public String getCreatePerson(@Param("id")int id);
	//更新提交的流程状态
	public int updateStatus(@Param("id")int id,@Param("status")int status);
	//获取某个人需要提醒的流程ID数
	public int getNumberByUserJobIdAndWork(@Param("userJobId")String jobId,@Param("work")int work);
	
	//删除一个提交的流程
	public int delOneSubmit(@Param("id")int id);
	
}
