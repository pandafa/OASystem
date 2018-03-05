package dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import bean.UserInfo;
import mapper.UserFrozenMapper;
import mapper.UserInfoMapper;

public class UserInfoDao {
	
	@Resource
	private UserFrozenMapper ufMapper;
	@Resource
	private UserInfoMapper uiMapper;

	
	/**
	 * 获取某个部门的全部用户的名字和jobId
	 * @param partId
	 * @param groupId
	 * @return
	 */
	public List<Map<String, Object>> getAllUserNameAndJobIdOfGroup(int partId, int groupId) {
		List<Map<String, Object>> result = null;
		result = uiMapper.getAllUserNameAndJobIdOfGroup(partId, groupId);
		if(result!=null && result.size()==0){
			result = null;
		}
		return result;
	}
	
	
	/**
	 * 更改某一个用户的全部信息。若更改所属，则传入true。
	 * @param info
	 * @param changeTime 
	 * @return
	 * @throws Exception 
	 */
	public boolean changeUserInfoAllByJobId(UserInfo info,boolean changeTime) throws Exception{
		boolean result = false;
			//状态是否更改
		if(getUserStatusByJobId(info.getJobId())!=info.getStatus()){
			//更改状态
			if(!changeUserStatusByJobId(info.getJobId(), info.getStatus())){
				return false;
			}
		}
		int returnNum = 0;
		if(changeTime){
			info.setJoinTime(new Date());
			returnNum = uiMapper.changeUserInfoAllByJobIdWithWhere(info);
		}else{
			returnNum = uiMapper.changeUserInfoAllByJobIdNoWhere(info);
		}
		if(returnNum==1){
			result = true;
		}else{
			throw new Exception();
		}
		return result;
	}
	
	/**
	 * 获取用户全部信息，根据用户jobId
	 * @param jobId
	 * @return
	 */
	public UserInfo getUserInfoByJobId(String jobId){
		UserInfo result = null;
		result = uiMapper.findByJobId(jobId);
		return result;
	}
	
	/**
	 * 获取全部用户，从第几到第几
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<Map<String,Object>> getUsersInfoOfAllByPageLimit(int begin, int end) {
		List<Map<String,Object>> result = null;
		result = uiMapper.getUsersInfoOfAllByPageLimit(begin, end);
		return result;
	}
	
	/**
	 * 按部门获取全部用户，从第几到第几
	 * @param partId
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<Map<String,Object>> getUsersInfoOfPartByPageLimit(int partId, int begin, int end) {
		List<Map<String,Object>> result = null;
		result = uiMapper.getUsersInfoOfPartByPageLimit(partId, begin, end);
		if(result!=null && result.size()==0){
			result = null;
		}
		return result;
	}
	
	/**
	 * 获取全部用户个数
	 * @return
	 */
	public int getMemberNumbersOfAll(){
		int result = -1;
		result = uiMapper.getMemberNumbersOfAll();
		return result;
	}
	
	/**
	 * 获取某个部门用户个数
	 * @param partId
	 * @return
	 */
	public int getMemberNumbersOfPart(int partId){
		int result = -1;
		result = uiMapper.getMemberNumbersOfPart(partId);
		return result;
	}
	
	/**
	 * 更改用户基本信息。包括部门、小组、用户类别，根据jobId更改。
	 * @param jobId
	 * @param part
	 * @param group
	 * @param kind
	 * @return
	 * @throws Exception 
	 */
	public boolean changeUserBaseInfo(String jobId,int part,int group,int kind,String post,boolean changeTime) throws Exception{
		boolean result = false;
		if(changeTime){
			//改变了所属
			if(uiMapper.changeUserBaseInfoWithTime(jobId, part, group, kind, new Date(),post)==1){
				result = true;
			}
		}else{
			//没变所属
			if(uiMapper.changeUserBaseInfoNoTime(jobId, kind,post)==1){
				result = true;
			}
		}
		if(result){
			return true;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * 获取用户小组。失败则返回-1
	 * @param jobId
	 * @return
	 */
	public int getUserGroupByJobId(String jobId) {
		int result = -1;
			result = uiMapper.getUserGroupByJobId(jobId);
			return result;
	}
	
	/**
	 * 获取用户部门。失败则返回-1
	 * @param jobId
	 * @return
	 */
	public int getUserPartByJobId(String jobId){
		int result = -1;
			result = uiMapper.getUserPartByJobId(jobId);
			return result;
	}
	
	/**
	 * 获取用户名
	 * @param jobId
	 * @return
	 */
	public String getUserNameByJobId(String jobId){
		String result = null;
			result = uiMapper.getUserNameByJobId(jobId);
			return result;
	}
	
	/**
	 * 批量添加用户
	 * @param usersList
	 * @return
	 * @throws Exception
	 */
	public boolean createNewUsers(List<UserInfo> usersList) throws Exception{
		boolean res = false;
		for(int i=0;i<usersList.size();i++){
			usersList.get(i).setErrorTimes(0);
			usersList.get(i).setJoinTime(new Date());
//			System.out.println("Dao:("+i+")"+usersList.get(i).getKind());
			int insertRes = uiMapper.addNewUser(usersList.get(i));
			if(insertRes==0){
				res = false;
				break;
			}else if(i+1==usersList.size()){
				res = true;
			}
		}
		if(res){
			return true;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * 是否有这个用户
	 * @param jobId
	 * @return
	 */
	public boolean hasUserByJobId(String jobId){
		boolean res = false;
		int num = uiMapper.hasUserByJobId(jobId);
		if(num!=0){
			res = true;
		}
		return res;
	}
	
	/**
	 * 通过jobId获取用户类型（int）
	 * @param id  jobId
	 * @return 失败为-1
	 */
	public int getUserKindByJobId(String id){
		int res = -1;
			res = uiMapper.getUserKindByJobId(id);
			return res;
	}
	
	/**
	 * 根据jobId找到附加项
	 * @param jobId
	 * @return
	 */
	public Map<String,Object> getOtherInfoByJobId(String jobId){
		Map<String,Object> resMap = null;
			resMap = uiMapper.getUserInfoByJobIdToMap(jobId);
			return resMap;
	}
	
	/**
	 * 验证密码
	 * @param jobId
	 * @param password
	 * @return
	 */
	public boolean validatePasswordByJobId(String jobId,String password){
		boolean result = false;
			String correctPwd = uiMapper.getUserPasswordByJobId(jobId);
			if(correctPwd.equals(password)){
				result = true;
			}
			return result;
	}
	
	/**
	 * 修改密码
	 * @param jobId
	 * @param password 新密码
	 * @return 是否成功
	 * @throws Exception 
	 */
	public boolean changePassword(String jobId,String password) throws Exception{
		int num = uiMapper.changePassword(jobId, password);
		if(num>0){
			return true;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * 通过jobId更改用户自己的邮箱、电话、地址
	 * @param jobId
	 * @param tel
	 * @param email
	 * @param addr
	 * @return
	 * @throws Exception 
	 */
	public boolean changeMyPersonInfoByJobId(String jobId,String tel,String email,String addr) throws Exception{
		int n = uiMapper.changeMyPersonInfoByJobId(jobId, tel, email, addr);
		if(n>=1){
			return true;
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * 获取某小组的人数
	 * @param partId
	 * @param groupId
	 * @return -1为查找失败
	 */
	public int getMemberNumbersOfGroup(int partId,int groupId){
		int result = -1;
			result = uiMapper.getMemberNumbersOfGroup(partId, groupId);
			return result;
	}
	
	/**
	 * 找某一个部门中除了密码以外的信息
	 * @param groupId
	 * @return
	 */
	public List<UserInfo> findUsersGroupOfGroupId(int partId,int groupId,int begin,int end){
		List<UserInfo> result = null;
			result = uiMapper.findUsersGroupOfGroupId(partId, groupId, begin, end);
			//清除密码
			if(result!=null && result.size()!=0){
				for(int i=0;i<result.size();i++){
					result.get(i).setPassword(null);
				}
			}
			return result;
	}
	
	/**
	 * 通过工号找信息
	 * @param jobId
	 * @return
	 */
	public Map<String, Object> findUserByJobId(String jobId){
		Map<String, Object> result = null;
		result = uiMapper.getUserInfoByJobIdToMap(jobId);
		if(result!=null){
			result.remove("password");
		}
		return result;
	}
	
	/**
	 * 通过工号登录
	 * @param jobId
	 * @param password
	 * @return
	 */
	public UserInfo checkLoginByJobId(String jobId,String password){
		UserInfo info = null;
			info = uiMapper.findByJobIdAndPassword(jobId, password);
			return info;
	}

	
	/**
	 * 查询用户状态
	 * @param jobId
	 * @return
	 */
	@SuppressWarnings("finally")
	public int getUserStatusByJobId(String jobId){
		int result = -1;
		try{
			result = uiMapper.getUserStatusByJobId(jobId);
		}catch (Exception e) {
		}finally{
			return result;
		}
		
	}
	
	/**
	 * 更改用户状态
	 * @param jobId
	 * @param status
	 * @return
	 * @throws Exception 
	 */
	public boolean changeUserStatusByJobId(String jobId, int status) throws Exception {
		boolean result = false;
		boolean canGo = true;
//		System.out.println("进入该状态"+",status:"+status+",jobId："+jobId);
		//如果冻结，添加到表中
		if(status==UserInfo.STATUS_FROZEN_15_MINUTE || status==UserInfo.STATUS_FROZEN_30_MINUTE || status==UserInfo.STATUS_FROZEN_24_HOUR){
			if(!changeUserStatusToFrozenByJobId(jobId,status)){
				canGo = false;
			}
		}
		if(canGo){
			int num = uiMapper.changeUserStatusByJobId(jobId, status);
			if(num==1){
				result = true;
			}else{
				throw new Exception();
			}
		}
		return result;
	}
	
	/**
	 * 添加到冻结列表
	 * @param jobId
	 * @param status
	 * @return
	 */
	public boolean changeUserStatusToFrozenByJobId(String jobId, int status) throws Exception {
		boolean result = false;
		Long times = new Date().getTime();
		//如果冻结，添加到表中
		if(status==UserInfo.STATUS_FROZEN_15_MINUTE){
			times += 1000*60*15;
		}else if(status==UserInfo.STATUS_FROZEN_30_MINUTE){
			times += 1000*60*30;
		}else if(status==UserInfo.STATUS_FROZEN_24_HOUR){
			times += 1000*60*60*24;
		}else{
			return false;
		}
		int num = ufMapper.changeUserStatusToFrozenByJobId(jobId, times);
		if(num>1){
			result = true;
		}
		return result;
	}
	
	/**
	 * 解冻
	 * @param jobId
	 * @param status
	 * @return
	 * @throws Exception 
	 */
	public boolean changeUserStatusOutOfFrozenByJobId(String jobId) throws Exception {
		boolean result = false;
		//从列表中删除
		int num = ufMapper.delUserFromFrozenByJobId(jobId);
		if(num==1){
			//更改状态
			if(uiMapper.changeUserStatusByJobId(jobId, UserInfo.STATUS_NORMAL)==1){
				result = true;
			}else{
				throw new Exception();
			}
		}
		return result;
	}
	
	/**
	 * 有需要解冻的用户么
	 * @return
	 */
	public String hasNeedToOutOfFrozen(){
		long nowTime = new Date().getTime();
		String result = null;
		if(ufMapper.getNeedOutOfFrozenNumber(nowTime) != 0){
			result = ufMapper.getNeedOutOfFrozenJobId();
		}
		return result;
	}
	
	/**
	 * 获得错误次数
	 * @param jobId
	 * @return
	 */
	public int getUserPasawordErrorTimes(String jobId) {
		int result = -1;
		result = uiMapper.getUserPasawordErrorTimes(jobId);
		return result;
	}
	
	/**
	 * 改变错误次数
	 * @param jobId
	 * @param times
	 * @return
	 * @throws Exception 
	 */
	public boolean changeUserPasswordErrorTimes(String jobId, int times) throws Exception {
		int num = uiMapper.changeUserPasswordErrorTimes(jobId, times);
		if(num==1){
			return true;
		}else{
			throw new Exception("返回的数字为："+num+",times:"+times+",jobId:"+jobId);
		}
	}
	
	
	
}
