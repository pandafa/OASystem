package dao;

import java.util.List;
import javax.annotation.Resource;
import bean.UserKind;
import mapper.UserKindMapper;

public class UserKindDao {
	
	@Resource
	private UserKindMapper mapper;
	
	/**
	 * 返回所有用户类型
	 * @return
	 */
	public List<UserKind> getAllUserKind(){
		List<UserKind> result = null;
			result = mapper.getAllUserKind();
			return result;
	}
	
	/**
	 * 根据ID获取名字
	 * @param id
	 * @return
	 */
	public String getUserKindName(int id){
		String result = null;
		result = mapper.getNameById(id);
		return result;
	}
}
