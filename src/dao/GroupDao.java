package dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import bean.Group;
import mapper.GroupMapper;

public class GroupDao {
	@Resource
	private GroupMapper mapper;
	
	/**
	 * 获取某部门的所有小组名和ID
	 * @param partId
	 * @return
	 */
	public List<Map<String, Object>> getAllGroupsOfPartNameAndId(int partId){
		List<Map<String, Object>> result = null;
		result = mapper.getAllGroupsOfPartNameAndId(partId);
		return result;
		
	}
	
	/**
	 * 根据部门ID和小组名，获取小组ID
	 * @param partId
	 * @param name
	 * @return
	 */
	public int getGroupByName(int partId,String name){
		int result = -1;
		try{
			result = mapper.getGroupByName(partId,name);
			return result;
		}catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * 添加小组
	 * @param partId
	 * @param name
	 * @param person
	 * @return
	 * @throws Exception 
	 */
	public boolean addGroup(int partId,String name,String person) throws Exception{
		boolean result = false;
		int num = mapper.addGroup(partId,name,person,new Date());
		if(num==1){
			result = true;
		}else{
			throw new Exception();
		}
		return result;
		
	}
	
	/**
	 * 删除小组
	 * @param partId
	 * @param groupId
	 * @return
	 * @throws Exception 
	 */
	public boolean delGroup(int partId,int groupId) throws Exception{
		boolean result = false;
		String groupName = mapper.getNameById(groupId, partId);
		int all = mapper.getMemberNumbersOfGroup(groupId, partId);
		if(all!=0){
			return false;
		}
		if(!groupName.equals("暂无小组")){
			if(mapper.delGroup(groupId, partId)!=0){
				result = true;
			}
		}else{
			return false;
		}
		if(!result){
			throw new Exception();
		}else{
			return true;
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
		result = mapper.getMemberNumbersOfGroup(groupId, partId);
		return result;
		
	}
	
	/**
	 * 返回从几到几的信息
	 * @param partId
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<Group> getGroupsOfPartAllInfoByNumber(int partId,int begin,int end){
		List<Group> result = null;
		if(begin>end){
			int temp = begin;
			begin = end;
			end = temp;
		}
		result = mapper.getGroupsOfPartAllInfoByNumber(partId, begin, end);
		return result;
	}
	
	/**
	 * 获取小组的个数
	 * @param partId
	 * @return
	 */
	public int allGroupsCount(int partId){
		int result = 0;
		result = mapper.allGroupsCount(partId);
		return result;
		
	}
	
	/**
	 * 根据id获取名字
	 * @param id
	 * @param partId
	 * @return
	 */
	public String getNameById(int id,int partId){
		String result = null;
		result = mapper.getNameById(id, partId);
		return result;
		
	}
}
