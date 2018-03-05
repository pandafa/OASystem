package service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import bean.Group;
import dao.GroupDao;

@Transactional(readOnly = true)
@Service
public class GroupService {
	public static int PAGE_NUMBER = 5;
	private GroupDao groupDao;
	public GroupDao getGroupDao() {
		return groupDao;
	}
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	/**
	 * 获取某部门的所有小组名和ID
	 * @param partId
	 * @return
	 */
	public List<Map<String, Object>> getAllGroupsOfPartNameAndId(int partId){
		return groupDao.getAllGroupsOfPartNameAndId(partId);
	}
	
	/**
	 * 根据部门ID和小组名，获取小组ID
	 * @param partId
	 * @param name
	 * @return
	 */
	public int getGroupByName(int partId,String name){
		return groupDao.getGroupByName(partId, name);
	}
	
	/**
	 * 删除小组
	 * @param partId
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public boolean delGroup(int partId,int groupId) throws Exception{
		return groupDao.delGroup(partId,groupId);
	}
	
	/**
	 * 添加小组
	 * @param partId
	 * @param name
	 * @param person
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public boolean addGroup(int partId,String name,String person) throws Exception{
		return groupDao.addGroup(partId,name, person);
	}
	
	/**
	 * 获取某小组的人数
	 * @param partId
	 * @param groupId
	 * @return
	 */
	public int getMemberNumbersOfGroup(int partId,int groupId){
		return groupDao.getMemberNumbersOfGroup(partId, groupId);
	}
	
	/**
	 * 获取第几页的内容
	 * @param page
	 * @return
	 */
	public List<Group> getGroupsOfPartByPage(int partId,int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_NUMBER;
		int end = PAGE_NUMBER;
		return groupDao.getGroupsOfPartAllInfoByNumber(partId,begin, end);
	}
	
	/**
	 * 获取总页数
	 * @return
	 */
	public int getAllPage(int partId){
		int number = groupDao.allGroupsCount(partId);
		return (int)Math.ceil(1.0*number/PAGE_NUMBER);
	}
	
	/**
	 * 根据ID获取小组名字
	 * @param partId
	 * @param groupId
	 * @return
	 */
	public String getNameById(int partId,int groupId){
		return groupDao.getNameById(groupId, partId);
	}
	
	//-----------
}
