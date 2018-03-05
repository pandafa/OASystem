package service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.UserKind;
import dao.UserKindDao;

@Transactional(readOnly = true)
@Service
public class UserKindService {
	private UserKindDao userKindDao;
	
	public String getNameById(int id){
		return userKindDao.getUserKindName(id);
	}
	
	/**
	 * 返回所有用户类型
	 * @return
	 */
	public List<UserKind> getAllUserKind(){
		return userKindDao.getAllUserKind();
	}
	
	/**
	 * 
	 * @return
	 */
	public UserKindDao getUserKindDao() {
		return userKindDao;
	}
	
	/**
	 * 
	 * @param userKindDao
	 */
	public void setUserKindDao(UserKindDao userKindDao) {
		this.userKindDao = userKindDao;
	}
}
