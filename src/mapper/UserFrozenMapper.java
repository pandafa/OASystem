package mapper;

import org.apache.ibatis.annotations.Param;

public interface UserFrozenMapper {
	//添加到冻结列表
	public int changeUserStatusToFrozenByJobId(@Param("jobId")String jobId, @Param("times")long times);
	//解冻，移除冻结列表
	public int delUserFromFrozenByJobId(@Param("jobId")String jobId);
	//获取需要解冻的人数
	public int getNeedOutOfFrozenNumber(@Param("nowTime")long nowTime);
	//获取需要解冻的人的jobId
	public String getNeedOutOfFrozenJobId();
}
