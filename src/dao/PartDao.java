package dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import bean.Part;
import mapper.GroupMapper;
import mapper.PartMapper;
import mapper.UserInfoMapper;

public class PartDao {
	
	@Resource
	private PartMapper pMapper;
	@Resource
	private GroupMapper gMapper;
	@Resource
	private UserInfoMapper uMapper;

	
	/**
	 * 通过名字获取部门ID
	 * @param name
	 * @return
	 */
	public int getPartByName(String name){
		int result = -1;
		try{
			result = pMapper.getPartIdByName(name);
		}catch (Exception e) {
			return -1;
		}
		return result;
	}
	
	/**
	 * 添加部门
	 * @param name
	 * @param person
	 * @return
	 * @throws Exception 
	 */
	public boolean addPart(String name,String person) throws Exception{
		boolean result = false;
		//添加部门
		Part newPart = new Part();
		newPart.setName(name);
		newPart.setCreateDate(new Date());
		newPart.setCreatePerson(person);
		int newId = pMapper.addNewPart(newPart);
		if(newId!=0){
			//添加默认小组
			newId = newPart.getId();
			if(gMapper.addGroup(newId, "暂无小组", "系统", new Date())!=0){
				result = true;
			}
		}
		if(!result){
			throw new Exception();
		}
		return result;
	}
	
	/**
	 * 删除部门
	 * @param partId
	 * @return
	 * @throws Exception 
	 */
	public boolean delPart(int partId) throws Exception{
		boolean result = false;
		//获取部门数
		int all = gMapper.allGroupsCount(partId);
		if(gMapper.delAllGroupsByPartId(partId)==all){
			if(pMapper.delPartById(partId)!=0){
				result = true;
			}
		}
		if(!result){
			throw new Exception();
		}
		return result;
	}
	
	/**
	 * 获取某个部门的人数
	 * @param partId
	 * @return
	 */
	public int getMemberOfPartNumbers(int partId){
		int result = 0;
		result = uMapper.getMemberNumbersOfPart(partId);
		return result;
	}
	
	/**
	 * 获取某个部门的小组数
	 * @param partId
	 * @return
	 */
	public int getGroupOfPartNumbers(int partId){
		int result = 0;
		result = gMapper.allGroupsCount(partId);
		return result;
	}
	
	/**
	 * 返回从几到几的信息
	 * @param begin
	 * @param end
	 * @return 返回null则没有找到
	 */
	public List<Part> getPartsAllInfoByNumber(int begin,int end){
		List<Part> partList = null;
		if(begin>end){
			int temp = begin;
			begin = end;
			end = temp;
		}
		partList =  pMapper.getPartsAllInfoByNumber(begin, end);
		if(partList==null || partList.size()==0){
			partList = null;
		}
		return partList;
	}
	
	/**
	 * 获取部门的个数
	 * @return 
	 */
	
	public int allPartsCount(){
		int result = 0;
		result = pMapper.allPartsCount();
		return result;
	}
	
	/**
	 * 获取所有的部门id和名字
	 * @return
	 */
	public List<Map<String, Object>>  getAllPartsAndNames(){
		List<Map<String, Object>>  result = null;
		result = pMapper.getAllPartsAndNames();
		return result;
	}
	
	/**
	 * 根据id获取名字
	 * @param id
	 * @return 空，则失败。
	 */
	public String getNameById(int id){
		String result = null;
		result = pMapper.getNameById(id);
		return result;
	}
	
}
