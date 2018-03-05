package service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.Part;
import dao.PartDao;

@Transactional(readOnly = true)
@Service
public class PartService {
	public static int PAGE_NUMBER = 5;
	private PartDao partDao;
	public PartDao getPartDao() {
		return partDao;
	}
	public void setPartDao(PartDao partDao) {
		this.partDao = partDao;
	}
	
	/**
	 * 通过名字获取部门ID
	 * @param name
	 * @return
	 */
	public int getPartByName(String name){
		return partDao.getPartByName(name);
	}
	
	/**
	 * 获取某个部门的人数
	 * @param partId
	 * @return
	 */
	public int getMemberOfPartNumbers(int partId){
		return partDao.getMemberOfPartNumbers(partId);
	}
	
	/**
	 * 获取某个部门的小组数
	 * @param partId
	 * @return
	 */
	public int getGroupOfPartNumbers(int partId){
		return partDao.getGroupOfPartNumbers(partId);
	}
	
	/**
	 * 删除部门
	 * @param partId
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public boolean delPart(int partId) throws Exception{
		return partDao.delPart(partId);
	}
	
	/**
	 * 添加部门
	 * @param name
	 * @param person
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public boolean addPart(String name,String person) throws Exception{
		return partDao.addPart(name, person);
	}
	
	/**
	 * 获取第几页的内容
	 * @param page
	 * @return
	 */
	public List<Part> getPartByPage(int page){
		if(page<1){
			page = 1;
		}
		int begin = (page-1)*PAGE_NUMBER;
		int end = PAGE_NUMBER;
		return partDao.getPartsAllInfoByNumber(begin, end);
	}
	
	/**
	 * 获取总页数
	 * @return
	 */
	public int getAllPage(){
		int number = partDao.allPartsCount();
		return (int)Math.ceil(1.0*number/PAGE_NUMBER);
	}
	
	/**
	 * 获取所有的部门id和名字
	 * @return
	 */
	public List<Map<String, Object>> getAllPartsAndNames(){
		return partDao.getAllPartsAndNames();
	}
	
	/**
	 * 根据ID获取部门名称
	 * @param id
	 * @return
	 */
	public String getNameById(int id){
		return partDao.getNameById(id);
	}
	
}
