package service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import bean.UserInfo;
import dao.UserInfoDao;
import util.MD5;

@Transactional(readOnly = true)
@Service
public class UserService {
	public static int PAGE_NUMBER = 10;
	public static int PAGE_USER_MANAGE_NUMBER = 10;
	private UserInfoDao userInfoDao;
	
	/**
	 * 获取某个部门的全部用户的名字和jobId
	 * @param partId
	 * @param groupId
	 * @return
	 */
	public List<Map<String, Object>> getAllUserNameAndJobIdOfGroup(int partId, int groupId) {
		return userInfoDao.getAllUserNameAndJobIdOfGroup(partId,groupId);
	}
	
	/**
	 * 更改某一个用户的全部信息（除了密码）
	 * @param info
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public boolean changeUserInfoAllByJobId(UserInfo info) throws Exception{
		int part = getUserPartByJobId(info.getJobId());
		int group = getUserGroupByJobId(info.getJobId());
		if(part!=info.getPart() || group!=info.getGroup()){
			return userInfoDao.changeUserInfoAllByJobId(info, true);
		}else{
			return userInfoDao.changeUserInfoAllByJobId(info, false);
		}
	}
	
	/**
	 * 获取用户全部信息，根据用户jobId
	 * @param jobId
	 * @return
	 */
	public UserInfo getUserInfoByJobId(String jobId){
		return userInfoDao.getUserInfoByJobId(jobId);
	}
	
	
	/**
	 * 获取全部用户信息，按某个部门或全部部门。partId为0，则获取全部部门。
	 * @param partId
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getUsersInfoOfPartByPage(int partId,int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_USER_MANAGE_NUMBER;
		int end = PAGE_USER_MANAGE_NUMBER;
		if(partId==0){
			return userInfoDao.getUsersInfoOfAllByPageLimit(begin,end);
		}else{
			return userInfoDao.getUsersInfoOfPartByPageLimit(partId,begin,end);
		}
	}
	
	/**
	 * 获取总页数，按部门或全部。partId为0，则获取全部
	 * @param partId
	 * @return
	 */
	public int getAllPageByPart(int partId){
		int number = 0;
		if(partId==0){
			number = userInfoDao.getMemberNumbersOfAll();
		}else{
			number = userInfoDao.getMemberNumbersOfPart(partId);
		}
		return (int)Math.ceil(1.0*number/PAGE_USER_MANAGE_NUMBER);
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
	@Transactional(readOnly = false)
	public boolean changeUserBaseInfoWithKind(String jobId,int part,int group,int kind,String post) throws Exception{
		int preGroup = getUserGroupByJobId(jobId);
		int prePart = getUserPartByJobId(jobId);
		if(preGroup!=group || prePart!=part){
			//改变了所属
			return userInfoDao.changeUserBaseInfo(jobId, part, group, kind, post, true);
		}else{
			//没改变所属
			return userInfoDao.changeUserBaseInfo(jobId, part, group, kind, post, false);
		}
	}
	
	/**
	 * 更改用户基本信息。包括部门和小组，根据jobId更改。
	 * @param jobId
	 * @param part
	 * @param group
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public boolean changeUserBaseInfoWithoutKind(String jobId,int part,int group,String post) throws Exception{
		int kind = getUserKindByJobId(jobId);
		int preGroup = getUserGroupByJobId(jobId);
		int prePart = getUserPartByJobId(jobId);
		if(preGroup!=group || prePart!=part){
			//改变了所属
			return userInfoDao.changeUserBaseInfo(jobId, part, group, kind, post, true);
		}else{
			//没改变所属
			return userInfoDao.changeUserBaseInfo(jobId, part, group, kind, post, false);
		}
	}
	
	/**
	 * 获取用户小组。失败则返回-1
	 * @param jobId
	 * @return
	 */
	public int getUserGroupByJobId(String jobId) {
		return userInfoDao.getUserGroupByJobId(jobId);
	}
	
	/**
	 * 获取用户部门。失败则返回-1
	 * @param jobId
	 * @return
	 */
	public int getUserPartByJobId(String jobId){
		return userInfoDao.getUserPartByJobId(jobId);
	}
	
	/**
	 * 获取用户名
	 * @param jobId
	 * @return
	 */
	public String getUserNameById(String jobId){
		return userInfoDao.getUserNameByJobId(jobId);
	}
	
	/**
	 * 批量创建新用户
	 * @param usersList
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false,isolation = Isolation.READ_UNCOMMITTED)
	public boolean createNewUsers(List<UserInfo> usersList) throws Exception{
		boolean res = false;
		//有内容
		if(usersList!=null && usersList.size()!=0){
			for(int i=0;i<usersList.size();i++){
				UserInfo user = usersList.get(i);
//				System.out.println("Service:("+i+")"+user.getKind());
				if(user.getJobId()!=null && user.getCardId()!=null && user.getName()!=null
						&& user.getSex()!=-1 && user.getPart()!=-1 && user.getGroup()!=-1){
					if(user.getKind()==0){
						usersList.get(i).setKind(UserInfo.KIND_MEMBER);//如果没有，设为默认值
					}
					if(user.getPassword()==null){
						usersList.get(i).setPassword(md5PasswordFirst("123456"));//如果没有，设为默认值
					}
				}else{
					//缺少东西，不进行添加
//					System.out.println("缺少东西，不进行添加");
					return res;
				}
			}
			//进行添加
			return userInfoDao.createNewUsers(usersList);
		}
		return res;
	}
	
	/**
	 * 是否有这个用户
	 * @param jobId
	 * @return
	 */
	public boolean hasUserByJobId(String jobId){
		return userInfoDao.hasUserByJobId(jobId);
	}
	
	/**
	 * 通过jobId获取用户类型
	 * @param jobId
	 * @return 失败为-1
	 */
	public int getUserKindByJobId(String jobId){
		return userInfoDao.getUserKindByJobId(jobId);
	}
	
	/**
	 * 获取总页数,按小组
	 * @param part
	 * @param group
	 * @return
	 */
	public int getAllPageByGroup(int partId,int groupId){
		int number = userInfoDao.getMemberNumbersOfGroup(partId, groupId);
		return (int)Math.ceil(1.0*number/PAGE_NUMBER);
	}
	
	
	/**
	 * 找某一个部门中除了密码以外的信息
	 * @param groupId
	 * @return
	 */
	public List<UserInfo> findUsersGroupOfGroupId(int partId,int groupId,int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_NUMBER;
		int end = PAGE_NUMBER;
		return userInfoDao.findUsersGroupOfGroupId(partId,groupId,begin,end);
	}
	
	/**
	 * 通过jobId获取处了密码以外的信息
	 * @param jobId
	 * @return
	 */
	public Map<String, Object> getPersonInfoAllByJobId(String jobId){
		return userInfoDao.findUserByJobId(jobId);
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
	@Transactional(readOnly = false)
	public boolean changeMyPersonInfoByJobId(String jobId,String tel,String email,String addr) throws Exception{
		if(tel==null || tel.length()==0){
			tel = null;
		}
		if(email==null || email.length()==0){
			email = null;
		}
		if(addr==null || addr.length()==0){
			addr = null;
		}
		return userInfoDao.changeMyPersonInfoByJobId(jobId, tel, email, addr);
	}
	
	/**
	 * 根据jobId找到附加项
	 * @param jobId
	 * @return
	 */
	public Map<String,Object> getOtherInfoByJobId(String jobId){
		return userInfoDao.getOtherInfoByJobId(jobId);
	}
	
	/**
	 * 验证登录
	 * @param jobId
	 * @param password
	 * @return
	 */
	public UserInfo checkLogin(String jobId,String password){
		UserInfo res = null;
		password = md5Password(password);
		res  = userInfoDao.checkLoginByJobId(jobId, password);
//		if(res==null){
//			res  = userInfoDao.checkLoginByCardId(jobId, password);
//		}
		return res;
	}
	
	/**
	 * 修改自己的密码
	 * @param jobId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public boolean changeMyPassword(String jobId,String oldPassword,String newPassword) throws Exception{
		boolean res = false;
		oldPassword = md5Password(oldPassword);
		newPassword = md5Password(newPassword);
		//如果密码正确
		if(userInfoDao.validatePasswordByJobId(jobId, oldPassword)){
			//修改密码
			if(userInfoDao.changePassword(jobId, newPassword)){
				res = true;
			}
		}
		return res;
	}
	
	/**
	 * 忘记密码
	 * @param jobId
	 * @param cardId
	 * @param name
	 * @param newPassword
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public Boolean forgetPassword(String jobId,String cardId,String name,String newPassword) throws Exception{
		Boolean res = false;
		//加密处理
		newPassword = md5Password(newPassword);
		//验证用户
		Map<String, Object> verif = userInfoDao.findUserByJobId(jobId);
		if(verif==null){
			return res;
		}
		if(verif.get("jobId").equals(jobId) && verif.get("cardId").equals(cardId) && verif.get("name").equals(name)){
			//验证通过，是本人,修改密码
			if(userInfoDao.getUserStatusByJobId(jobId)==UserInfo.STATUS_NO_ACTIVITY){
				//没有激活，进行激活
				userInfoDao.changeUserStatusByJobId(jobId,UserInfo.STATUS_NORMAL);
			}
			return userInfoDao.changePassword(jobId, newPassword);
		}else{
			return res;
		}
	}
	
	/**
	 * 更改用户状态
	 * @param jobId
	 * @param status
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public boolean changeUserStatusByJobId(String jobId, int status) throws Exception{
		return userInfoDao.changeUserStatusByJobId(jobId, status);
	}
	
	/**
	 * 解冻
	 * @param jobId
	 * @return
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public boolean changeUserStatusOutOfFrozenByJobId(String jobId) throws Exception{
		return userInfoDao.changeUserStatusOutOfFrozenByJobId(jobId);
	}
	
	/**
	 * 有需要解冻的用户么
	 * @return
	 */
	public String hasNeedToOutOfFrozen(){
		return userInfoDao.hasNeedToOutOfFrozen();
	}
	
	/**
	 * 对密码二级加密
	 * @param str
	 * @return
	 */
	private String md5Password(String str){
		String res = null;
		String doStr = "ruan"+str+"jian"+str;
		res = MD5.md5(doStr);
//		System.out.println("MD5-->"+res);
		return res;
	}
	
	/**
	 * 对密码一级加密
	 * @param str
	 * @return
	 */
	private String md5PasswordFirst(String str){
		String res = null;
		String doStr = str+"15180600101"+str;
		res = MD5.md5(doStr);
		return res;
	}
	
	/**
	 * 查询用户状态
	 * @param jobId
	 * @return
	 */
	public int getUserStatusByJobId(String jobId){
		return userInfoDao.getUserStatusByJobId(jobId);
	}
	
	

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	/**
	 * 改变错误次数
	 * @param username
	 * @param times
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public boolean changeUserPasswordErrorTimes(String jobId, int times) throws Exception {
		return userInfoDao.changeUserPasswordErrorTimes(jobId, times);
	}
	
	/**
	 * 获得错误次数
	 * @param username
	 * @return
	 */
	public int getUserPasawordErrorTimes(String jobId) {
		return userInfoDao.getUserPasawordErrorTimes(jobId);
	}

	

	
	

	
}
