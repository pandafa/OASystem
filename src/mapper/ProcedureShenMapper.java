package mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import bean.ProcedureShen;

public interface ProcedureShenMapper {
	//添加审批流程
	public int addNewShen(ProcedureShen procedureShen);
	//通过提交号找全部提交的审批
	public List<ProcedureShen> findBySubmitId(@Param("submitId")int submitId);
	//获取某个人需要处理的总数
	public int getAllNeedToDealNumber(@Param("userJobId")String jobId);
	
	//找到需要处理的审批流程id
	public List<Map<String, Object>> getNeedDealIdsByJobIdFromNumberToNumber
	(@Param("userJobId")String jobId,@Param("begin")int begin,@Param("end")int end);
	
	//获取更新的时间
	public Timestamp getUpdateTime(@Param("procedureId")int submitId,@Param("order")int order);
	
	//寻找处理人员
	public String getDealPersonByIds(@Param("id")int shenId,@Param("procedureId")int procedureId);
	//获得该流程的审批顺序
	public int getOrderByIds(@Param("id")int shenId,@Param("procedureId")int procedureId);
	//更新审批流程
	public int updateAllByIds(ProcedureShen procedureShen);
	//查找某个流程第某步骤的审批人
	public String getJobIdByOrderAndProcedureId(@Param("procedureId")int procedureId,@Param("order")int order);
	//查询某个提交的流程是否有此步骤
	public int getHasThisOrderOfSubmit(@Param("procedureId")int procedureId,@Param("order")int order);
	//更改某个提交的流程的某步骤的状态
	public int updateWork(@Param("work")int work,@Param("procedureId")int procedureId,@Param("order")int order);
	
}
