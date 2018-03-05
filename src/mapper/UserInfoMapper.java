package mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import bean.UserInfo;

public interface UserInfoMapper {
	//获取某个部门的全部用户的名字和jobId
	public List<Map<String, Object>> getAllUserNameAndJobIdOfGroup(@Param("part")int partId, @Param("group")int groupId);
	//更改某一个用户的全部信息。包括所属
	public int changeUserInfoAllByJobIdWithWhere(UserInfo info);
	//更改某一个用户的全部信息。不包括所属
	public int changeUserInfoAllByJobIdNoWhere(UserInfo info);
	//获取用户全部信息，根据用户jobId
	public UserInfo findByJobId(@Param("jobId")String jobId);
	//获取全部用户，从第几到第几
	public List<Map<String,Object>> getUsersInfoOfAllByPageLimit(@Param("begin")int begin, @Param("end")int end);
	//按部门获取全部用户，从第几到第几
	public List<Map<String,Object>> getUsersInfoOfPartByPageLimit(@Param("partId")int partId, @Param("begin")int begin, @Param("end")int end);
	//获取全部用户个数
	public int getMemberNumbersOfAll();
	//获取某个部门用户个数
	public int getMemberNumbersOfPart(@Param("partId")int partId);
	
	
	//更改用户基本信息。包括部门、小组、用户类别，根据jobId更改。
	public int changeUserBaseInfoWithTime(@Param("jobId")String jobId,@Param("part")int part,
			@Param("group")int group,@Param("kind")int kind,@Param("joinTime")Date changeTime,@Param("post")String post);
	//更改用户基本信息。包括用户类别，根据jobId更改。
	public int changeUserBaseInfoNoTime(@Param("jobId")String jobId,@Param("kind")int kind,@Param("post")String post);
	
	//获取用户小组
	public int getUserGroupByJobId(@Param("jobId")String jobId);
	//获取用户部门
	public int getUserPartByJobId(@Param("jobId")String jobId);
	//获取用户名
	public String getUserNameByJobId(@Param("jobId")String jobId);
	//添加新用户
	public int addNewUser(UserInfo userInfo);
	//是否有这个用户
	public int hasUserByJobId(@Param("jobId")String jobId);
	//获取用户类型
	public int getUserKindByJobId(@Param("jobId")String jobId);
	//根据jobId找到用户
	public Map<String,Object> getUserInfoByJobIdToMap(@Param("jobId")String jobId);
	//获取用户密码
	public String getUserPasswordByJobId(@Param("jobId")String jobId);
	//修改密码
	public int changePassword(@Param("jobId")String jobId,@Param("password")String password);
	//通过jobId更改用户自己的邮箱、电话、地址
	public int changeMyPersonInfoByJobId(@Param("jobId")String jobId,@Param("tel")String tel,
			@Param("email")String email,@Param("addr")String addr);
	//获取某小组的人数
	public int getMemberNumbersOfGroup(@Param("partId")int partId,@Param("groupId")int groupId);
	//找某一个部门中除了密码以外的信息
	public List<UserInfo> findUsersGroupOfGroupId(@Param("partId")int partId,@Param("groupId")int groupId,
			@Param("begin")int begin,@Param("end")int end);
	//通过工号和密码对应查找
	public UserInfo findByJobIdAndPassword(@Param("jobId")String jobId,@Param("password")String password);
	//获取用户类型
	public int getUserStatusByJobId(@Param("jobId")String jobId);
	//更改用户状态
	public int changeUserStatusByJobId(@Param("jobId")String jobId, @Param("status")int status);
	//获取用户错误次数
	public int getUserPasawordErrorTimes(@Param("jobId")String jobId);
	//改变错误次数
	public int changeUserPasswordErrorTimes(@Param("jobId")String jobId, @Param("times")int times);
	
	
	//获取所有的jobId
	public List<String> getAllUserJobId();
	//获取所有的jobId，部门的
	public List<String> getAllUserJobIdOfPart(@Param("partId")int partId);
	//获取所有的jobId，小组的
	public List<String> getAllUserJobIdOfGroup(@Param("partId")int partId,@Param("groupId")int groupId);
	
}
